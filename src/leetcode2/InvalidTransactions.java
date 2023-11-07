package leetcode2;


import java.util.*;
import java.util.stream.Collectors;

class InvalidTransactions {

    public static void main(String[] args) {
        InvalidTransactions invalidTransactions = new InvalidTransactions();
        System.out.println(invalidTransactions.invalidTransactions(new String[]
                {"lee,886,1785,beijing", "alex,763,1157,amsterdam", "lee,277,129,amsterdam", "bob,770,105,amsterdam", "lee,603,926,amsterdam", "chalicefy,476,50,budapest", "lee,924,859,barcelona", "alex,302,590,amsterdam", "alex,397,1464,barcelona", "bob,412,1404,amsterdam", "lee,505,849,budapest"}

        ));
//        System.out.println(invalidTransactions.invalidTransactions(new String[]{"alice,20,800,mtv", "alice,50,100,beijing"}));
//        System.out.println(invalidTransactions.invalidTransactions(new String[]{"alice,20,800,mtv", "alice,50,1200,mtv"}));
//        System.out.println(invalidTransactions.invalidTransactions(new String[]{"alice,20,800,mtv", "alice,50,100,mtv", "alice,51,100,frankfurt"}));
    }

    public List<String> invalidTransactions(String[] transactions) {
        Map<String, Queue<Transaction>> nameToTrxns = new HashMap<>();
        for (String trx : transactions) {
            Transaction trans = new Transaction(trx);
            nameToTrxns.putIfAbsent(trans.name, new PriorityQueue<>());
            nameToTrxns.get(trans.name).offer(trans);
        }
        List<Transaction> totalFrauds = new ArrayList<>();
        for (Map.Entry<String, Queue<Transaction>> entry : nameToTrxns.entrySet()) {
            Queue<Transaction> trxQueue = entry.getValue();
            if (entry.getValue().isEmpty()) {
                continue;
            }
            Queue<Transaction> window = new ArrayDeque<>();
            List<Transaction> frauds = new ArrayList<>();
            while (!trxQueue.isEmpty()) {
                window.add(trxQueue.poll());
                while (!trxQueue.isEmpty() && isWithinWindow(window, trxQueue)) {
                    window.add(trxQueue.poll());
                }
                List<Transaction> likelyFrauds = new ArrayList<>(window);
                String topCity = window.peek().city;
                boolean isLikelyFraud = false;
                for (Transaction transaction : window) {
                    if (!topCity.equals(window.poll().city)) {
                        isLikelyFraud = true;
                        break;
                    }
                }
                if (isLikelyFraud) {
                    frauds.addAll(likelyFrauds);
                } else {
                    for (Transaction transaction : likelyFrauds) {
                        if (transaction.amount > 1000) {
                            frauds.add(transaction);
                        }
                    }
                }
                window.poll();
            }
            totalFrauds.addAll(frauds);
        }
        return totalFrauds.stream().map(t -> t.trans).collect(Collectors.toList());

    }

    private boolean isWithinWindow(Queue<Transaction> window, Queue<Transaction> trxQueue) {
        return Math.abs(trxQueue.peek().time - window.peek().time) <= 60;
    }

    private boolean isFraudTrxn(Queue<Transaction> window, Queue<Transaction> trxQueue) {
        return trxQueue.peek().time - window.peek().time <= 60 &&
                trxQueue.peek().name.equals(window.peek().name) && !trxQueue.peek().city.equals(window.peek().city);
    }

    private boolean isExistsMoreTraxs(Queue<Transaction> window, Queue<Transaction> trxQueue) {
        return !trxQueue.isEmpty() && !window.isEmpty();
    }

    public class Transaction implements Comparable<Transaction> {
        String name;
        int time;
        int amount;
        String city;
        String trans;

        public Transaction(String trx) {
            trans = trx;
            String[] arr = trx.split(",");
            name = arr[0];
            time = Integer.valueOf(arr[1]);
            amount = Integer.valueOf(arr[2]);
            city = arr[3];
        }

        public boolean equals(Object o) {
            if (o == null || !getClass().equals(o.getClass())) {
                return false;
            }
            Transaction ext = (Transaction) o;
            return name.equals(ext.name) && time == ext.time && amount == ext.amount && city.equals(ext.city);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, time, amount, city, trans);
        }

        public int compareTo(Transaction ext) {
            TreeMap<Integer,Integer> eventsMap = new TreeMap<>();
            new TreeMap<>(eventsMap.subMap(1,5));
            List<Integer> ll = new ArrayList<>(eventsMap.keySet());
            return Integer.compare(time, ext.time);

        }
    }
}
