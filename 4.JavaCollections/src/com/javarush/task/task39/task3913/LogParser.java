package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery, QLQuery {
    private Path logDir;
    private List<Record> records = new ArrayList<>();
    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public LogParser(Path logDir) {
        this.logDir = logDir;
        init();
//        System.out.println("Filled records, size = " + records.size());
//        for (Record record : records) {
//            System.out.println(record);
//        }
    }

    private void init() {
        for (File file : logDir.toFile().listFiles()) {
            if (file.toString().endsWith(".log")) {
                try {
                    List<String> lines = Files.readAllLines(file.toPath(), Charset.forName("UTF-8"));
                    for (String line : lines) {
                        String[] record = line.split("\t");

                        //getting date
                        Date recordDate = null;
                        try {
                            recordDate = dateFormat.parse(record[2]);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        //splitting record[3] into event and task
                        String[] eventAndTaskNum = record[3].split(" ");
                        Event event = Event.valueOf(eventAndTaskNum[0]);
                        int taskNumber = eventAndTaskNum.length > 1 ? Integer.valueOf(eventAndTaskNum[1]) : -1;

                        records.add(new Record(record[0], record[1], recordDate, event, taskNumber, Status.valueOf(record[4])));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {
        return getUniqueIPs(after, before).size();
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {
        return getIPs(Command.UNIQUE, null, after, before);
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {
        return getIPs(Command.FOR_USER, user, after, before);
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {
        return getIPs(Command.FOR_EVENT, event, after, before);
    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {
        return getIPs(Command.FOR_STATUS, status, after, before);
    }

    private Set<String> getIPs(Command command, Object param, Date after, Date before) { //command might be enum
        Set<String> ips = new HashSet<>();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        for (File file : logDir.toFile().listFiles()) {
            if (file.toString().endsWith(".log")) {
                try {
                    List<String> lines = Files.readAllLines(file.toPath(), Charset.forName("UTF-8"));
                    for (String line : lines) {
                        String[] record = line.split("\t");

                        try {
                            // fromDate toDate check
                            Date recordDate = dateFormat.parse(record[2]);
                            if (    (after == null && before == null) ||
                                    (after != null && after.getTime() <= recordDate.getTime() && before == null) ||
                                    (after == null && before != null && recordDate.getTime() <= before.getTime()) ||
                                    (after != null && before != null && after.getTime() <= recordDate.getTime() && recordDate.getTime() <= before.getTime()) ) {

                                switch (command) {
                                    case UNIQUE:
                                        ips.add(record[0]);
                                        break;
                                    case FOR_USER:
                                        if (record[1].equals(param)) {
                                            ips.add(record[0]);
                                        }
                                        break;
                                    case FOR_EVENT:
                                        if (Event.valueOf(record[3].split(" ")[0])    // номер задачи вырезается
                                                .equals(param)) {
                                            ips.add(record[0]);
                                        }
                                        break;
                                    case FOR_STATUS:
                                        if (Status.valueOf(record[4])
                                                .equals(param)) {
                                            ips.add(record[0]);
                                        }
                                        break;
                                }
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ips;
    }

    private enum Command {
        UNIQUE,
        FOR_USER,
        FOR_EVENT,
        FOR_STATUS
    }


    @Override
    public Set<String> getAllUsers() {
        return  records.stream()
                    .map(s -> s.userName)
                    .collect(Collectors.toSet());
    }

    @Override
    public int getNumberOfUsers(Date after, Date before) {
        return getUserSet(getDateFilteredStream(after, before)).size();

    }

    public Stream<Record> getDateFilteredStream(Date after, Date before) {
        return records.stream()
                .filter(s -> {
                    if (after != null)
                        return s.date.getTime() >= after.getTime();
                    else return true;
                })
                .filter(s -> {
                    if (before != null)
                        return s.date.getTime() <= before.getTime();
                    else return true;
                });
    }

    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {
        return (int) getDateFilteredStream(after, before)
                    .filter(s -> user.equals(s.userName))
                    .map(s -> s.event)
                    .distinct()
                    .count();
    }

    @Override
    public Set<String> getUsersForIP(String ip, Date after, Date before) {
        return getDateFilteredStream(after, before)
                .filter(s -> ip.equals(s.ip))
                .map(s -> s.userName)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getLoggedUsers(Date after, Date before) {
        return getDateFilteredStream(after, before)
                .filter(s -> s.event.equals(Event.LOGIN))
//                .filter(s -> s.status.equals(Status.OK))
                .map(s -> s.userName)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {
        return getDateFilteredStream(after, before)
                .filter(s -> s.event == Event.DOWNLOAD_PLUGIN)
                .filter(s -> s.status == Status.OK)
                .map(s -> s.userName)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getWroteMessageUsers(Date after, Date before) {
        return getDateFilteredStream(after, before)
                .filter(s -> s.event == Event.WRITE_MESSAGE)
                .filter(s -> s.status == Status.OK)
                .map(s -> s.userName)
                .collect(Collectors.toSet());
    }

    private Set<String> getUserSet(Stream<Record> stream){
        return stream
                .map(s -> s.userName)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before) {
        Stream<Record> stream = getDateFilteredStream(after, before)
                .filter(s -> s.event == Event.SOLVE_TASK);
        return getUserSet(stream);
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
        return getUserSet(
                getDateFilteredStream(after, before)
                    .filter(s -> (s.event == Event.SOLVE_TASK) && s.taskNumber == task)
        );
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before) {
        return getUserSet(
                getDateFilteredStream(after, before)
                        .filter(s -> (s.event == Event.DONE_TASK))
        );
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
        return getUserSet(
                getDateFilteredStream(after, before)
                        .filter(s -> (s.event == Event.DONE_TASK) && s.taskNumber == task)
        );
    }

    @Override
    public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {
        return getDateFilteredStream(after, before)
                .filter(s -> user.equals(s.userName) && event == s.event)
                .map(s -> s.date)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Date> getDatesWhenSomethingFailed(Date after, Date before) {
        return getDateFilteredStream(after, before)
                .filter(s -> s.status == Status.FAILED)
                .map(s -> s.date)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Date> getDatesWhenErrorHappened(Date after, Date before) {
        return getDateFilteredStream(after, before)
                .filter(s -> s.status == Status.ERROR)
                .map(s -> s.date)
                .collect(Collectors.toSet());
    }

    @Override
    public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before) {
        return getDateFilteredStream(after, before)
                .filter(s -> user.equals(s.userName) && s.event == Event.LOGIN)
                .map(s -> s.date)
                .sorted()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {
        return getDateFilteredStream(after, before)
                .filter(s -> user.equals(s.userName) && s.event == Event.SOLVE_TASK && s.taskNumber == task)
                .map(s -> s.date)
                .sorted()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before) {
        return getDateFilteredStream(after, before)
                .filter(s -> s.userName.equals(user) && s.event == Event.DONE_TASK && s.taskNumber == task)
                .map(s -> s.date)
                .sorted()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {
        return getDateFilteredStream(after, before)
                .filter(s -> s.userName.equals(user) && s.event == Event.WRITE_MESSAGE && s.status == Status.OK)
                .map(s -> s.date)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {
        return getDateFilteredStream(after, before)
                .filter(s -> user.equals(s.userName) && s.event == Event.DOWNLOAD_PLUGIN && s.status == Status.OK)
                .map(s -> s.date)
                .collect(Collectors.toSet());
    }

    @Override
    public int getNumberOfAllEvents(Date after, Date before) {
        return (int)getDateFilteredStream(after, before)
                .map(s -> s.event)
                .distinct()
                .count();
    }

    @Override
    public Set<Event> getAllEvents(Date after, Date before) {
        return getDateFilteredStream(after, before)
                .map(s -> s.event)
                .distinct()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getEventsForIP(String ip, Date after, Date before) {
        return getDateFilteredStream(after, before)
                .filter(s -> ip.equals(s.ip))
                .map(s -> s.event)
                .distinct()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getEventsForUser(String user, Date after, Date before) {
        return getDateFilteredStream(after, before)
                .filter(s -> user.equals(s.userName))
                .map(s -> s.event)
                .distinct()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getFailedEvents(Date after, Date before) {
        return getDateFilteredStream(after, before)
                .filter(s -> s.status == Status.FAILED)
                .map(s -> s.event)
                .distinct()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getErrorEvents(Date after, Date before) {
        return getDateFilteredStream(after, before)
                .filter(s -> s.status == Status.ERROR)
                .map (s -> s.event)
                .distinct()
                .collect(Collectors.toSet());
    }

    @Override
    public int getNumberOfAttemptToSolveTask(int task, Date after, Date before) {
        return (int)getDateFilteredStream(after, before)
                .filter(s -> s.event == Event.SOLVE_TASK && s.taskNumber == task)
                .count();
    }

    @Override
    public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before) {
        return (int)getDateFilteredStream(after, before)
                .filter(s -> s.event == Event.DONE_TASK && s.taskNumber == task)
                .count();
    }

    @Override
    public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before) {
        Map<Integer, Integer> result;
        result = getDateFilteredStream(after, before)
                .filter(s -> s.event == Event.SOLVE_TASK)
                .collect(Collectors.toMap(
                        Record::getTaskNumber,
                        s -> 1,
                        Integer::sum
                        ));
        return result;
    }

    @Override
    public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before) {
        return getDateFilteredStream(after, before)
                .filter(s -> s.event == Event.DONE_TASK)
                .collect(Collector.of(
                        HashMap::new,
                        (b, s) -> b.merge(s.taskNumber, 1, Integer::sum),
                        (b1, b2) -> {b1.putAll(b2);
                            return b1;}

                ));
    }

    @Override
    public Set<Object> execute(String query) {
        if (query == null) return null;

        String[] splitQuery = query.trim().split(" ");
        String what = splitQuery[1];
        String field = null, value = null;
        if (splitQuery.length > 3) {
            field = splitQuery[3];
            int startIndex, endIndex;
            if ((startIndex = query.indexOf("\"")) != -1 && (endIndex = query.indexOf("\"", startIndex + 1)) != -1) {
                value = query.substring(startIndex + 1, endIndex);
            }
        }

        //getting start and end date (through indexes of \")
        int s1 = ordinalIndexOf(query, "\"", 3) + 1;
        int s2 = ordinalIndexOf(query, "\"", 4);
        int e1 = ordinalIndexOf(query, "\"", 5) + 1;
        int e2 = ordinalIndexOf(query, "\"", 6);
        String startDateString = (s1 != -1 && s2 != -1) ? query.substring(s1, s2) : null;
        String endDateStirng = (e1 != -1 && e2 != -1) ? query.substring(e1, e2) : null;



        String fieldIntoLambda = field;
        String valueIntoLambda = value;

        return records.stream() // check get, for etc
                .filter(s -> {
                    if (startDateString != null && endDateStirng != null) {
                        try {
                            Date startDate = dateFormat.parse(startDateString);
                            Date endDate = dateFormat.parse(endDateStirng);
                            return s.date.after(startDate) && s.date.before(endDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                            return false;
                        }
                    }
                    return true;
                })
                .filter(s -> {
                    if (fieldIntoLambda == null || valueIntoLambda == null) return true;
                    //  ip, user, date, event или status
                    if (fieldIntoLambda.equals("ip")) return s.ip.equals(valueIntoLambda);
                    else if (fieldIntoLambda.equals("user")) return s.userName.equals(valueIntoLambda);
                    //parsing
                    else if (fieldIntoLambda.equals("date"))
                        try {
                            Date thisDate = dateFormat.parse(valueIntoLambda);
                            Date sdate = s.date;
                            return s.date.equals(thisDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                            return false;
                        }
                    else if (fieldIntoLambda.equals("event")) return s.event.equals(Event.valueOf(valueIntoLambda));
                    else if (fieldIntoLambda.equals("status")) return s.status.equals(Status.valueOf(valueIntoLambda));
                    else return false;
                })
                .map(s -> {
                    if (what.equals("ip")) return s.ip;
                    else if (what.equals("user")) return s.userName;
                    else if (what.equals("date")) return s.date;
                    else if (what.equals("event")) return s.event;
                    else if (what.equals("status")) return s.status;
                    return null; //return set containing null
                })
                .distinct()
                .collect(Collectors.toSet());


//        if ("get ip".equals(query)) {
//            return records.stream()
//                    .map(s -> s.ip)
//                    .distinct()
//                    .collect(Collectors.toSet());
//        } else if ("get user".equals((query))) {
//            return records.stream()
//                    .map(s -> s.userName)
//                    .distinct()
//                    .collect(Collectors.toSet());
//        } else if ("get date".equals(query)) {
//            return records.stream()
//                    .map(s -> s.date)
//                    .distinct()
//                    .collect(Collectors.toSet());
//        } else if ("get event".equals(query)) {
//            return records.stream()
//                    .map(s -> s.event)
//                    .distinct()
//                    .collect(Collectors.toSet());
//        } else if ("get status".equals(query)) {
//            return records.stream()
//                    .map(s -> s.status)
//                    .distinct()
//                    .collect(Collectors.toSet());
//        }
        
    }

    public static int ordinalIndexOf(String str, String substr, int n) {
        int pos = str.indexOf(substr);
        while (--n > 0 && pos != -1)
            pos = str.indexOf(substr, pos + 1);
        return pos;
    }
}