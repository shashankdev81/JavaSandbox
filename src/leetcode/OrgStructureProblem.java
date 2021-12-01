package leetcode;

import java.util.ArrayList;
import java.util.List;

public class OrgStructureProblem {

    private static class Employee {
        String employeeId;
        boolean isEngineer;
        List<Employee> reportees;

        public Employee(String employeeId, boolean isEngineer) {
            this.employeeId = employeeId;
            this.isEngineer = isEngineer;
            this.reportees = new ArrayList<Employee>();
        }
    }

    public List<Employee> doReOrg(Employee employee) {

        List<Employee> result = new ArrayList<>();

        for (Employee reportee : employee.reportees) {
            result.addAll(doReOrg(reportee));
        }
        employee.reportees = result;

        if (!employee.isEngineer) {
            return employee.reportees;
        } else {
            result = new ArrayList<>();
            result.add(employee);
            return result;
        }
    }

    public static void main(String[] args) {
        Employee manager = new Employee("Amitabh", true);
        Employee shreesha = new Employee("Shreesha", true);
        Employee rajeev = new Employee("Rajeev", true);
        Employee kishore = new Employee("kishore", false);
        Employee dd = new Employee("Durgadas", false);
        Employee prabhat = new Employee("Prabhat", true);
        Employee Shashank = new Employee("Shashank", true);


        manager.reportees.add(shreesha);
        shreesha.reportees.add(rajeev);
        shreesha.reportees.add(kishore);

        dd.reportees.add(prabhat);
        manager.reportees.add(dd);

        manager.reportees.add(Shashank);

        OrgStructureProblem problem = new OrgStructureProblem();
        manager = problem.doReOrg(manager).get(0);
        System.out.println("Re org done");
    }
}
