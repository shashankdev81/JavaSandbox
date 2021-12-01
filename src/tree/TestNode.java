package tree;

import java.util.Objects;

public class TestNode {

    private int num;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestNode testNode = (TestNode) o;
        return num == testNode.num && name.equals(testNode.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, name);
    }
}
