package dp;

import java.util.HashMap;
import java.util.Map;

public class EmployeesPerMonth {

    private Map<String, Integer> costMap;

    public int getMinCostForCompany(int[] employees, int salary, int hiring, int severence) {
        int cost = employees[0] * (salary + hiring);
        costMap = new HashMap<>();
        return getMinCost(employees, 1, salary, hiring, severence, cost, employees[0]);
    }

    private int getMinCost(int[] employees, int month, int salary, int hiring, int severence, int costSoFar, int numEmployees) {
        String key = month + "," + numEmployees + "," + costSoFar;
        if (costMap.containsKey(key)) {
            return costMap.get(key);
        }
        int minCost = Integer.MAX_VALUE;
        if (month >= employees.length) {
            return costSoFar;
        }
        if (employees[month] > employees[month - 1]) {
            int hiringCost = (employees[month] - employees[month - 1]) * hiring;
            minCost = getMinCost(employees, month + 1, salary, hiring, severence,
                    costSoFar + employees[month] * salary + hiringCost, employees[month]);
        } else {
            int diff = employees[month - 1] - employees[month];
            for (int extra = 0; extra <= diff; extra++) {
                int severenceCost = (diff - extra) * severence;
                int salaryCost = (employees[month] + extra) * salary;
                minCost = Math.min(minCost, getMinCost(employees, month + 1, salary, hiring, severence,
                        costSoFar + salaryCost + severenceCost, employees[month] + extra));
            }

        }

        costMap.putIfAbsent(key, minCost);
        return minCost;

    }

    public static void main(String[] args) {
        EmployeesPerMonth employeesPerMonth = new EmployeesPerMonth();
        //2000+2000+2000+2000=8000 OR 2000+2000+1000+5000+1000+2000
        System.out.println(employeesPerMonth.getMinCostForCompany(new int[]{10, 5, 10}, 200, 200, 1000));
//        System.out.println(employeesPerMonth.getMinCostForCompany(new int[]{10, 10, 10, 10, 10, 10}, 200, 200, 200));
//        System.out.println(employeesPerMonth.getMinCostForCompany(new int[]{5, 12, 8, 15, 20, 10}, 200, 400, 600));
    }
}
