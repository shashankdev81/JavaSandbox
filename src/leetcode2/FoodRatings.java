package leetcode2;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class FoodRatings {


    private Map<String, Food> foodMap = new HashMap<>();

    private Map<String, Food> ratingsMap = new TreeMap<>();

    public FoodRatings(String[] foods, String[] cuisines, int[] ratings) {
        for (int i = 0; i < foods.length; i++) {
            Food newFood = new Food(foods[i], cuisines[i], ratings[i]);
            foodMap.put(foods[i], newFood);
            if (!ratingsMap.containsKey(cuisines[i])) {
                ratingsMap.put(cuisines[i], newFood);
            } else {
                Food currHighestRatedFood = ratingsMap.get(cuisines[i]);
                if (currHighestRatedFood.rating < newFood.rating
                    || (currHighestRatedFood.rating == newFood.rating
                    && currHighestRatedFood.foodName.compareTo(newFood.foodName) > 1)) {
                    ratingsMap.put(cuisines[i], newFood);
                }

            }
        }
    }

    public void changeRating(String food, int newRating) {
        if (!foodMap.containsKey(food)) {
            return;
        }
        Food newFood = foodMap.get(food);
        Food currHighestRatedFood = ratingsMap.get(newFood.cuisineType);
        newFood.rating = newRating;
        if (currHighestRatedFood.rating < newFood.rating) {
            ratingsMap.put(currHighestRatedFood.cuisineType, newFood);
        }

    }

    public String highestRated(String cuisine) {
        return ratingsMap.get(cuisine).foodName;
    }

    class Food {

        String foodName;
        String cuisineType;
        int rating;

        public Food(String f, String c, int r) {
            foodName = f;
            cuisineType = c;
            rating = r;
        }
    }
}

/**
 * Your FoodRatings object will be instantiated and called as such: FoodRatings obj = new
 * FoodRatings(foods, cuisines, ratings); obj.changeRating(food,newRating); String param_2 =
 * obj.highestRated(cuisine);
 */