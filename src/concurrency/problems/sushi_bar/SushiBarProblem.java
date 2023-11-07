package concurrency.problems.sushi_bar;


public class SushiBarProblem {

    public static void main(String[] args) throws Exception {
        SushiBar sushiBar = new SushiBar(5);
        new Thread(new Patron("Shashank", sushiBar)).start();
        new Thread(new Patron("Shweta", sushiBar)).start();
        new Thread(new Patron("Divya", sushiBar)).start();
        new Thread(new Patron("Gaurav", sushiBar)).start();
        new Thread(new Patron("Mom", sushiBar)).start();
        new Thread(new Patron("Nishant", sushiBar)).start();
        new Thread(new Patron("JD", sushiBar)).start();
        new Thread(new Patron("Ambuj", sushiBar)).start();
        new Thread(new Patron("Priya", sushiBar)).start();
        new Thread(new Patron("Saransh", sushiBar)).start();
        Thread.sleep(10);
        new Thread(new Patron("Shubham", sushiBar)).start();

    }


}
