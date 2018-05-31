package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.DateQuery;
import com.javarush.task.task39.task3913.query.IPQuery;
import com.javarush.task.task39.task3913.query.UserQuery;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogParser implements IPQuery, UserQuery, DateQuery {
    private Path logDir;
    private List<Record> records = new ArrayList<>();

    public LogParser(Path logDir) {
        this.logDir = logDir;
        init();
//        System.out.println("Filled records, size = " + records.size());
//        for (Record record : records) {
//            System.out.println(record);
//        }
    }

    private void init() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
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
                .filter(s -> user.equals(s.userName))
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
        return null;
    }

    @Override
    public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {
        return null;
    }

    @Override
    public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {
        return null;
    }
}