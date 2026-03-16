import java.util.ArrayList;
import java.util.List;

public class PayrollProcessor {

    public static List<Employee> employees = new ArrayList<>();

    public static double TAX_RATE = 0.2;
    public static int REGULAR_HOURS_LIMIT = 160;
    public static double OVERTIME_MULTIPLIER = 1.5;

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
                AuditLogger.logUnknownType(e);
                continue;
            }

            report.addEntry(entry);
            AuditLogger.logPayComputed(e, entry);
        }

        AuditLogger.logPayrollEnd(report);
        return report;
    }

    public static PayrollEntry calculatePayrollEntry(Employee e) {
        if (e == null || e.type == null) {
            return null;
        }

        if ("salaried".equals(e.type)) {
            double gross = e.monthlySalary;
            double tax = gross * TAX_RATE;
            return new PayrollEntry(safeName(e), e.type, gross, tax, gross - tax);
        }

        if ("contractor".equals(e.type)) {
            double gross = calculateHourlyGross(e.hourlyRate, e.hoursWorked);
            return new PayrollEntry(safeName(e), e.type, gross, 0, gross);
        }

        if ("hourly".equals(e.type)) {
            double gross = calculateHourlyGross(e.hourlyRate, e.hoursWorked);
            double tax = gross * e.taxRate;
            return new PayrollEntry(safeName(e), e.type, gross, tax, gross - tax);
        }

        return null;
    }

    public static double calculateHourlyGross(double hourlyRate, int hoursWorked) {
        int regularHours = Math.min(hoursWorked, REGULAR_HOURS_LIMIT);
        int overtimeHours = Math.max(hoursWorked - REGULAR_HOURS_LIMIT, 0);
        return (regularHours * hourlyRate) + (overtimeHours * hourlyRate * OVERTIME_MULTIPLIER);
    }

    private static String safeName(Employee employee) {
        if (employee == null || employee.name == null || employee.name.trim().isEmpty()) {
            return "<unknown>";
        }
        return employee.name;
    }
}
