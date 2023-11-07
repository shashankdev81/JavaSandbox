package concurrency.problems.sushi_bar;

public class Patron implements Runnable {

    private String name;

    private SushiBar sushiBar;


    public Patron(String inName, SushiBar sushiBar) {
        this.name = inName;
        this.sushiBar = sushiBar;
    }

    @Override
    public void run() {
        try {
            sushiBar.sitDown();
            System.out.println("Will sit down and eat:" + name);
            Thread.sleep((long) (1000 * Math.random()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Will get up and move:" + name);
            sushiBar.getUp();
        }

    }
}