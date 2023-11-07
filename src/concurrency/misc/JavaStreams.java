package concurrency.misc;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class JavaStreams {

    private Stream<String> stream;

    public static void main(String[] args) {
        // reduce starts with initial value, accumulated value and what to do with next
        long res = LongStream.of(2, 53, 17, 21, 11, 42, 30)
                .reduce(1, (acc, next) -> acc * next);
        long res2 = LongStream.of(1,2,3)
                .reduce(0, (acc, next) -> acc + next);
        long start = System.currentTimeMillis();
        String input = "Framework - Lead by example - Consensus: Broaden the discussion quorum & Consensus - Relationships: Connect and influence leadership. Build trust. Value my opinion. Rigour - Data driven decision making: system and business metrics Examples Successful Influence:- Production Stability, Reporting architecture 2.0, Team reorg around platform - Data, Reporting, etc, Audience Targeting, Various Initiatives, Areas of influence: Senior Engineering leaders: investment in new platforms, team/org structures, Product: Influence product to help prioritise new capabilities, phase out deliverables, Juniors: Quality of deliverables, health of platform, tech quotient of team, etc UnSuccessful Influence:- Shopping Entities Reporting architecture 2.0: 1. Identify whats wrong with current system. Product. NFR. Extensibility. etc 2. Work with product to build a case for re architecture (weak link) 3. Buy in from immediate boss 4. Share with team 5. Share with org level stake holders 6. Buy in from site architects Strength - Breadth and depth of technology - Excellent articulation skills - Framework to achieve business impact Weakness - Projects get shelved more often than not; so take it in stride and don't be married to a problem or a solution - Estimates";
        Stream<String> stream = Arrays.stream((input.split(" ")));
        //Stream<String> stream = Arrays.stream((input.split(" "))).parallel();
        Map<String, Long> wordCount = stream.map(String::trim)
                .map(w -> w.replaceAll("\\.", ""))
                .map(w -> w.replaceAll("\\(", ""))
                .map(w -> w.replaceAll(",", ""))
                .map(w -> w.replaceAll("-", ""))
                .map(w -> w.replaceAll(":", ""))
                .map(w -> w.replaceAll(";", ""))
                .filter(w -> w != null && !w.isEmpty())
                .map(String::toLowerCase).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        List<Map.Entry<String, Long>> orderedWordCount = wordCount.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).collect(Collectors.toList());
        System.out.println(orderedWordCount);
        long end = System.currentTimeMillis();
        System.out.println("Time taken=" + (end - start));
        System.out.println(res2);

    }
}
