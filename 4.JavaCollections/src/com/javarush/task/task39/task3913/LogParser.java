package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.IPQuery;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LogParser implements IPQuery{
    private Path logDir;

    public LogParser(Path logDir) {
        this.logDir = logDir;
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
}