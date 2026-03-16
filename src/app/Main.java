package app;

import model.ContractorEmployee;
import model.Employee;
import model.HourlyEmployee;
import model.PayrollReport;
import model.SalariedEmployee;
import service.PayrollProcessor;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
            new SalariedEmployee("Alice", 5000),
            new ContractorEmployee("Bob", 50, 170),
            new ContractorEmployee("Charlie", 60, 0),
            new HourlyEmployee("Dana", 30, 165, 0.15)
        );

        for (Employee employee : employees) {
            System.out.println(employee.getName() + " net pay preview: " + employee.calculatePay());
            PayrollProcessor.addEmployee(employee);
        }

        PayrollReport report = PayrollProcessor.processPayroll();
        PayrollPrinter.printReport(report);
    }
}
