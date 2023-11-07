package object_orinted_design.file_search;

public class FileName {

    private String id;
    private String name;
    private long createdAt;
    private long updatedAt;
    private String author;
    private int size;

    public FileName(String name, long createdAt, long updatedAt, String author) {
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.author = author;
    }


    public String getName() {
        return name;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public String getAuthor() {
        return author;
    }

    public int getSize() {
        return size;
    }
}
