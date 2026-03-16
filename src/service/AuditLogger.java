package service;

import helper.CurrencyFormatter;
import model.Employee;
import model.PayrollEntry;
import model.PayrollReport;

public class AuditLogger {

    public static void logEmployeeAdded(Employee employee) {
        System.out.println("[audit] employee added: " + safeName(employee));
    }

    public static void logPayrollStart(int employeeCount) {
        System.out.println("[audit] payroll run started for " + employeeCount + " employees");
    }

    public static void logPayrollEnd(PayrollReport report) {
        System.out.println("[audit] payroll run finished: " + report.getEmployeeCount() + " employees processed");
    }

    public static void logPayComputed(Employee employee, PayrollEntry entry) {
        System.out.println(
            "[audit] pay computed for "
                + safeName(employee)
                + ": gross="
                + CurrencyFormatter.format(entry.getGrossPay())
                + ", tax="
                + CurrencyFormatter.format(entry.getTax())
                + ", net="
                + CurrencyFormatter.format(entry.getNetPay())
        );
    }

    private static String safeName(Employee employee) {
        if (employee == null || employee.getName() == null || employee.getName().trim().isEmpty()) {
            return "<unknown>";
        }
        return employee.getName();
    }
}
