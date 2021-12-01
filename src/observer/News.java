package observer;

public class News {
    private String info;

    public News(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public String toString() {
        return "News{" +
                "info='" + info + '\'' +
                '}';
    }
}
