package leetcode2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class MovieRentingSystem {


    public static void main(String[] args) {
        MovieRentingSystem movieRentingSystem = new MovieRentingSystem(10,
            new int[][]{{0, 418, 3}, {9, 5144, 46}, {2, 8986, 29}, {6, 1446, 28}, {3, 8215, 97},
                {7, 9105, 34}, {6, 9105, 30}, {5, 1722, 94}, {9, 528, 40}, {3, 850, 77},
                {3, 7069, 40}, {8, 1997, 42}, {0, 8215, 28}, {7, 4050, 80}, {4, 7100, 97},
                {4, 9686, 32}, {4, 2566, 93}, {2, 8320, 12}, {2, 5495, 56}});
        System.out.println(movieRentingSystem.search(7837));
        System.out.println(movieRentingSystem.search(5495));
        movieRentingSystem.rent(4, 7100);
    }

    final Map<Integer, Set<MovieToRent>> movieToShopMap = new HashMap<>();

    final Map<Integer, Set<MovieToRent>> shopToRentedMoviesMap = new HashMap<>();

    int shopsCount = 0;

    public MovieRentingSystem(int n, int[][] entries) {
        for (int i = 0; i < entries.length; i++) {
            Integer shop = entries[i][0];
            Integer movie = entries[i][1];
            Integer price = entries[i][2];
            movieToShopMap.putIfAbsent(movie, new TreeSet<>());
            movieToShopMap.get(movie).add(new MovieToRent(shop, movie, price));
        }
        this.shopsCount = n;
    }

    public List<Integer> search(int movie) {
        List<Integer> results = new ArrayList<>();
        if (movieToShopMap.containsKey(movie)) {
            results = movieToShopMap.get(movie).stream().map(m -> m.shopId).limit(5)
                .collect(Collectors.toList());
        }
        return results;
    }

    public void rent(int shop, int movie) {
        if (movieToShopMap.containsKey(movie)) {
            List<MovieToRent> movieToRentList = movieToShopMap.get(movie).stream()
                .filter(m -> m.equals(new MovieToRent(shop, movie, -1)))
                .collect(Collectors.toList());
            if (movieToRentList.isEmpty()) {
                MovieToRent mv = movieToShopMap.get(movie).stream().collect(Collectors.toList())
                    .get(0);
                System.out.println("rent null " + mv.movieId + "," + mv.shopId + "," + mv.price);
                System.out.println(
                    "rent null " + movieToShopMap.get(movie).size() + "," + shop + "," + movie);
                return;
            }
            movieToShopMap.get(movie).remove(movieToRentList.get(0));
            shopToRentedMoviesMap.putIfAbsent(shop, new TreeSet<>());
            shopToRentedMoviesMap.get(shop).add(movieToRentList.get(0));
        }

    }

    public void drop(int shop, int movie) {
        if (shopToRentedMoviesMap.containsKey(shop)) {
            List<MovieToRent> movieToRentList = shopToRentedMoviesMap.get(shop).stream()
                .filter(m -> m.equals(new MovieToRent(shop, movie, -1)))
                .collect(Collectors.toList());
            if (movieToRentList.isEmpty()) {
                return;
            }
            shopToRentedMoviesMap.get(shop).remove(movieToRentList.get(0));
            movieToShopMap.get(movie).add(movieToRentList.get(0));
        }

    }

    public List<List<Integer>> report() {
        System.out.println(shopToRentedMoviesMap.keySet());
        List<List<Integer>> results = new ArrayList<>();
        for (int i = 0; i < shopsCount; i++) {
            final int shopId = i;
            if (shopToRentedMoviesMap.containsKey(shopId)) {
                for (MovieToRent movie : shopToRentedMoviesMap.get(shopId)) {
                    List<Integer> shopReport = new ArrayList<>();
                    shopReport.add(movie.shopId);
                    shopReport.add(movie.movieId);
                    results.add(shopReport);
                }
            }
        }
        return results;
    }

    public class MovieToRent implements Comparable<MovieToRent> {

        int shopId;
        int price;
        int movieId;

        public MovieToRent(int sid, int mid, int p) {
            this.shopId = sid;
            this.movieId = mid;
            this.price = p;
        }

        public int compareTo(MovieToRent ext) {
            if (this.price == ext.price) {
                if (this.shopId == ext.shopId) {
                    return this.movieId - ext.movieId;
                } else {
                    return this.shopId - ext.shopId;
                }
            } else {
                return this.price - ext.price;
            }

        }

        public boolean equals(Object obj) {
            if (obj == null || !obj.getClass().equals(this.getClass())) {
                return false;
            }
            MovieToRent mr = (MovieToRent) obj;
            return this.shopId == mr.shopId && this.movieId == mr.movieId;

        }

        public int hashCode() {
            return Objects.hash(shopId, movieId);
        }
    }

}

/**
 * Your MovieRentingSystem object will be instantiated and called as such: MovieRentingSystem obj =
 * new MovieRentingSystem(n, entries); List<Integer> param_1 = obj.search(movie);
 * obj.rent(shop,movie); obj.drop(shop,movie); List<List<Integer>> param_4 = obj.report();
 */