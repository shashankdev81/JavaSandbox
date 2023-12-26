package leetcode2;


import java.util.*;
import java.util.stream.Collectors;

public class ShoppingOffers {

    Map<String, Integer> memoryMap = new HashMap<>();

    public static void main(String[] args) {
        List<Integer> list = new LinkedList<>();
        ShoppingOffers shoppingOffers = new ShoppingOffers();
        List<Integer> prices = Arrays.stream(new int[]{2, 5}).boxed().collect(Collectors.toList());
        List<List<Integer>> special = Arrays.stream(new int[][]{{3, 0, 5}, {1, 2, 10}}).map(s -> Arrays.stream(s).boxed().collect(Collectors.toList())).collect(Collectors.toList());
        List<Integer> needs = Arrays.stream(new int[]{3, 2}).boxed().collect(Collectors.toList());
//        List<Integer> prices = Arrays.stream(new int[]{9, 9}).boxed().collect(Collectors.toList());
//        List<List<Integer>> special = Arrays.stream(new int[][]{{1, 1, 1}}).map(s -> Arrays.stream(s).boxed().collect(Collectors.toList())).collect(Collectors.toList());
//        List<Integer> needs = Arrays.stream(new int[]{2, 2}).boxed().collect(Collectors.toList());
        System.out.println(shoppingOffers.shoppingOffers(prices, special, needs));
    }

    public int shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
        int cost = 0;
        Map<Integer, Integer> unMetNeedsMap = new HashMap<>();
        for (int i = 0; i < needs.size(); i++) {
            unMetNeedsMap.put(i, needs.get(i));
        }
        cost = applyOffer(special, 0, price, 0, unMetNeedsMap, new LinkedList<>());
        return cost;
    }

    private int applyOffer(List<List<Integer>> offers, int offerIdx, List<Integer> price, int costSoFar, Map<Integer, Integer> unMetNeedsMap, List<Integer> offersApplied) {
        if (memoryMap.containsKey(offersApplied.toString())) {
            return memoryMap.get(offersApplied.toString());
        }
        if (!isUnmet(unMetNeedsMap)) {
            return costSoFar;
        }
        if (offerIdx >= offers.size()) {
            return purchaseUnmetGoods(price, unMetNeedsMap, costSoFar);
        }
        int costWithCurrOfferOnce = Integer.MAX_VALUE;
        int costWithoutCurrOffer = Integer.MAX_VALUE;
        int costWithCurrOfferNTimes = Integer.MAX_VALUE;
        int cost = 0;
        //boolean isApplicable = index < offers.size() && unMetNeedsMap.entrySet().stream().allMatch(e -> e.getValue() <= offers.get(index).get(e.getKey()));
        List<Integer> offer = offers.get(offerIdx);

        //apply
        costWithoutCurrOffer = applyOffer(offers, offerIdx + 1, price, costSoFar, unMetNeedsMap, offersApplied);

        boolean isOfferApplied = useOffer(unMetNeedsMap, offer, true);
        cost += isOfferApplied ? offer.get(offer.size() - 1) : 0;
        offersApplied.add(offerIdx);
        costWithCurrOfferOnce = applyOffer(offers, offerIdx + 1, price, costSoFar + cost, unMetNeedsMap, offersApplied);
        if (isOfferApplied) {
            costWithCurrOfferNTimes = applyOffer(offers, offerIdx, price, costSoFar + cost, unMetNeedsMap, offersApplied);
        }
        if (isOfferApplied) {
            useOffer(unMetNeedsMap, offer, false);
        }
        int minCost = Math.min(costWithCurrOfferNTimes, Math.min(costWithCurrOfferOnce, costWithoutCurrOffer));
        memoryMap.putIfAbsent(offersApplied.toString(), minCost);
        return minCost;

    }

    private int purchaseUnmetGoods(List<Integer> price, Map<Integer, Integer> unMetNeedsMap, int cost) {
        int total = cost;
        boolean isUnmet = isUnmet(unMetNeedsMap);
        if (isUnmet) {
            for (Map.Entry<Integer, Integer> entry : unMetNeedsMap.entrySet()) {
                total += price.get(entry.getKey()) * entry.getValue();
            }
        }
        return total;
    }

    private boolean isUnmet(Map<Integer, Integer> unMetNeedsMap) {
        boolean isUnmet = unMetNeedsMap.entrySet().stream().anyMatch(e -> e.getValue() > 0);
        return isUnmet;
    }

    private boolean useOffer(Map<Integer, Integer> unMetNeedsMap, List<Integer> offer, boolean isApply) {
        boolean isNotApplicable = unMetNeedsMap.entrySet().stream().anyMatch(e -> offer.get(e.getKey()) > e.getValue());
        if (isApply && isNotApplicable) {
            return false;
        }
        for (int i = 0; i < offer.size() - 1; i++) {
            if (isApply) {
                unMetNeedsMap.put(i, unMetNeedsMap.get(i) - offer.get(i));
            } else {
                unMetNeedsMap.put(i, unMetNeedsMap.get(i) + offer.get(i));
            }
        }
        return true;
    }


}
