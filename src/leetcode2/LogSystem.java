package leetcode2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

class LogSystem {

    private Set<Log> logs = new TreeSet<>(new LogComparator(null));

    public LogSystem() {

    }

    public static void main(String[] args) {
        LogSystem logSystem = new LogSystem();
        logSystem.put(1, "2017:01:01:23:59:59");
        logSystem.put(2, "2017:01:01:22:59:59");
        logSystem.put(3, "2016:01:01:00:00:00");
        logSystem.put(4, "2016:01:01:01:00:00");
        System.out.println(logSystem.retrieve("2016:01:01:01:01:01", "2017:01:01:23:00:00", "Year"));

    }

    public void put(int id, String timestamp) {
        Log newLog = getLog(id, timestamp);
        logs.add(newLog);
    }

    private Log getLog(int id, String timestamp) {
        String[] dateArr = timestamp.split(":");
        SimpleDateFormat format = new SimpleDateFormat("yyyy:mm:dd:hh:mm:ss");
        long time = System.currentTimeMillis();
        try {
            time = format.parse(timestamp).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log newLog = new Log(id, time, dateArr[0], dateArr[1], dateArr[2], dateArr[3], dateArr[4], dateArr[5]);
        return newLog;
    }

    public List<Integer> retrieve(String start, String end, String precision) {
        List<Integer> results = new ArrayList<>();
        List<Log> logs2 = logs.stream().collect(Collectors.toList());
        Log l1 = getLog(-1, start);
        Log l2 = getLog(-1, end);
        results = logs2.stream().filter(e -> {
            if ("Year".equalsIgnoreCase(precision)) {
                return compare(e.year, l1.year, l2.year);
            } else if ("Month".equalsIgnoreCase(precision)) {
                return compare(e.month, l1.month, l2.month);
            } else if ("Day".equalsIgnoreCase(precision)) {
                return compare(e.day, l1.day, l2.day);
            } else if ("Hour".equalsIgnoreCase(precision)) {
                return compare(e.hour, l1.hour, l2.hour);
            } else if ("Minute".equalsIgnoreCase(precision)) {
                return compare(e.min, l1.min, l2.min);
            } else if ("Second".equalsIgnoreCase(precision)) {
                return compare(e.sec, l1.sec, l2.sec);
            } else {
                return false;
            }
        }).map(e -> e.id).collect(Collectors.toList());
        return results;
    }

    private boolean compare(int c, int one, int two) {
        return c >= one && c <= two;
    }

    public class Log {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Log log = (Log) o;
            return time == log.time && year == log.year && month == log.month && day == log.day && hour == log.hour && min == log.min && sec == log.sec;
        }

        @Override
        public int hashCode() {
            return Objects.hash(time, year, month, day, hour, min, sec);
        }

        protected int id;
        protected long time;
        protected int year;
        protected int month;
        protected int day;
        protected int hour;
        protected int min;
        protected int sec;

        public Log(String part, String precision) {
            if ("Year".equalsIgnoreCase(precision)) {
                System.out.println("part=" + part);
                year = Integer.valueOf(part);
            } else if ("Month".equalsIgnoreCase(precision)) {
                month = Integer.valueOf(part);
            } else if ("Day".equalsIgnoreCase(precision)) {
                day = Integer.valueOf(part);
            } else if ("Hour".equalsIgnoreCase(precision)) {
                hour = Integer.valueOf(part);
            } else if ("Minute".equalsIgnoreCase(precision)) {
                min = Integer.valueOf(part);
            } else if ("Second".equalsIgnoreCase(precision)) {
                sec = Integer.valueOf(part);
            }
        }


        public Log(int id, long now, String y, String mo, String d, String h, String m, String s) {
            this.id = id;
            time = now;
            year = Integer.valueOf(y);
            month = Integer.valueOf(mo);
            day = Integer.valueOf(d);
            hour = Integer.valueOf(h);
            min = Integer.valueOf(m);
            sec = Integer.valueOf(s);
        }

    }


    private class LogComparator implements Comparator<Log> {

        private String precision;

        public LogComparator(String g) {
            precision = g;
        }

        public int compare(Log l1, Log l2) {
            if ("Year".equalsIgnoreCase(precision)) {
                return Integer.compare(l1.year, l2.year);
            } else if ("Day".equalsIgnoreCase(precision)) {
                return Integer.compare(l1.day, l2.day);
            } else if ("Hour".equalsIgnoreCase(precision)) {
                return Integer.compare(l1.hour, l2.hour);
            } else if ("Minute".equalsIgnoreCase(precision)) {
                return Integer.compare(l1.min, l2.min);
            } else if ("Second".equalsIgnoreCase(precision)) {
                return Integer.compare(l1.sec, l2.sec);
            } else {
                return Long.compare(l1.time, l2.time);
            }

        }

    }
}
