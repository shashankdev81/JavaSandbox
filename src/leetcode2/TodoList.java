package leetcode2;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

class TodoList {


    private AtomicInteger taskIdCounter = new AtomicInteger(0);

    private Map<Integer, Map<String, List<Task>>> userToTagsToTaskMap;

    private Map<Integer, Map<Integer, Task>> userToTaskMap;

    public TodoList() {
        userToTagsToTaskMap = new HashMap<>();
        userToTaskMap = new HashMap<>();
    }

    public static void main(String[] args) {
        TodoList todoList = new TodoList();
        todoList.addTask(1, "Task1", 50, new ArrayList<>());
        todoList.addTask(1, "Task2", 100, Arrays.asList(new String[]{"P1"}));
        System.out.println(todoList.getAllTasks(1));
        System.out.println(todoList.getAllTasks(5));
        todoList.addTask(1, "Task3", 30, Arrays.asList(new String[]{"P1"}));
        System.out.println(todoList.getTasksForTag(1, "P1"));
        todoList.completeTask(5, 1);
        todoList.completeTask(1, 2);
        System.out.println(todoList.getTasksForTag(1, "P1"));
        System.out.println(todoList.getAllTasks(1));
        Optional<Integer> h = Arrays.asList(3).stream().max(Integer::compare);
        System.out.println(h.get());
    }

    public int addTask(int userId, String taskDescription, int dueDate, List<String> tags) {
        userToTagsToTaskMap.putIfAbsent(userId, new TreeMap<>());
        userToTaskMap.putIfAbsent(userId, new HashMap<>());
        int taskId = taskIdCounter.incrementAndGet();
        Task task = new Task(taskId, taskDescription, dueDate);
        userToTaskMap.get(userId).put(taskId, task);
        for (String tag : tags) {
            userToTagsToTaskMap.get(userId).putIfAbsent(tag, new ArrayList<>());
            userToTagsToTaskMap.get(userId).get(tag).add(task);
        }
        return taskId;
    }

    public List<String> getAllTasks(int userId) {
        List<String> results = new ArrayList<String>();
        if (userToTaskMap.get(userId) != null) {
            results = userToTaskMap.get(userId).values().stream().sorted()
                .filter(t -> !t.isComplete()).map(t -> t.desc).collect(Collectors.toList());
        }
        return results;
    }

    public List<String> getTasksForTag(int userId, String tag) {
        List<String> results = new ArrayList<>();
        if (userToTagsToTaskMap.get(userId) != null) {
            results = userToTagsToTaskMap.get(userId).get(tag).stream().sorted()
                .filter(t -> !t.isComplete()).map(t -> t.desc).collect(Collectors.toList());
        }
        return results;
    }

    public void completeTask(int userId, int taskId) {
        if (userToTaskMap.get(userId) != null) {
            userToTaskMap.get(userId).get(taskId).complete();
        }
    }

    public class Task implements Comparable<Task> {

        private int id;
        private String desc;
        private int dueDate;
        private boolean isComplete = false;

        public Task(int idVal, String taskDescription, int dueWhen) {
            this.id = idVal;
            this.desc = taskDescription;
            this.dueDate = dueWhen;
        }

        public String toString() {
            return "Task:id, desc, dueData, isComplete=" + id + "," + desc + "," + dueDate + ","
                + isComplete;
        }

        public int compareTo(Task t) {
            return Integer.compare(this.dueDate, t.dueDate);
        }

        public void complete() {
            this.isComplete = true;
        }

        public boolean isComplete() {
            return this.isComplete;
        }
    }
}