package object_orinted_design;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RouterServiceUsingRegexMatching implements IRouter {


    public static void main(String[] args) {
        RouterServiceUsingRegexMatching routerService = new RouterServiceUsingRegexMatching();
        routerService.withRoute("/foo/qw/ert", "/bar/foo");
        routerService.withRoute("/bar/*/ert", "/foo/bar");
        routerService.withRoute("/bar/ext/*", "/qu/rty");
        System.out.println(routerService.route("/foo/qw/ert"));
        System.out.println(routerService.route("/bar/qw/ert"));
        System.out.println(routerService.route("/bar/ext/timepass"));

    }

    private Set<Character> SPECIAL_TRAILING_CHARS = "?/&".chars().mapToObj(s -> (char) s).collect(
        Collectors.toSet());
    private Map<String, RegexAndRoute> routeMap;

    private class RegexAndRoute {

        private String route;

        private Pattern pattern;


        public RegexAndRoute(String route, Pattern pattern) {
            this.route = route;
            this.pattern = pattern;
        }
    }

    public RouterServiceUsingRegexMatching() {
        this.routeMap = new HashMap<>();
    }

    public void withRoute(String path, String result) {
        path = normalise(path);
        String regex = path.contains(".*") ? path : path.replace("*", ".*");
        routeMap.put(path, new RegexAndRoute(result, Pattern.compile(regex)));
    }

    public String route(String path) {
        path = normalise(path);
        if (routeMap.containsKey(path)) {
            return routeMap.get(path).route;
        }

        for (Map.Entry<String, RegexAndRoute> existingPath : routeMap.entrySet()) {
            Matcher matcher = existingPath.getValue().pattern.matcher(path);
            if (matcher.matches()) {
                return existingPath.getValue().route;
            }
        }
        return null;
    }

    private String normalise(String path) {
        if (path.charAt(0) != '/') {
            path = "/" + path;
        }
        char endChar = path.charAt(path.length() - 1);
        if (SPECIAL_TRAILING_CHARS.contains(endChar)) {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }

}
