package tree;

public interface BST {

    public void insert(int value);

    public int getSize();

    public int getHeight();

    public int getMin();

    public int getMax();

    public boolean isBalanced();

    public void remove(int value) throws Exception;

}
