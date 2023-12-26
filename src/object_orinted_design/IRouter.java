package object_orinted_design;

public interface IRouter {

    void withRoute(String path, String result);

    String route(String path);
}
