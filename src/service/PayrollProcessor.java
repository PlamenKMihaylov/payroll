package service;

import model.Employee;
import model.PayrollEntry;
import model.PayrollReport;

import java.util.ArrayList;
import java.util.List;

public class PayrollProcessor {

    public static List<Employee> employees = new ArrayList<>();

    public static void addEmployee(Employee e) {
        employees.add(e);
        AuditLogger.logEmployeeAdded(e);
    }

    public static void clearEmployees() {
        employees.clear();
    }

    public static PayrollReport processPayroll() {
        PayrollReport report = new PayrollReport();
        AuditLogger.logPayrollStart(employees.size());

        for (Employee e : employees) {
            ValidationService.validateEmployee(e);
            PayrollEntry entry = calculatePayrollEntry(e);
            if (entry == null) {
                report.recordUnknownEmployee();
                continue;
            }
            report.addEntry(entry);
            AuditLogger.logPayComputed(e, entry);
        }

        AuditLogger.logPayrollEnd(report);
        return report;
    }

    public static PayrollEntry calculatePayrollEntry(Employee e) {
        if (e == null) {
            return null;
        }
        return new PayrollEntry(
            safeName(e),
            e.getEmploymentType(),
            e.calculateGrossPay(),
            e.calculateTax(),
            e.calculatePay()
        );
    }

    private static String safeName(Employee employee) {
        if (employee == null || employee.getName() == null || employee.getName().trim().isEmpty()) {
            return "<unknown>";
        }
        return employee.getName();
    }
}
