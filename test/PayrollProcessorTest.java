import model.ContractorEmployee;
import model.Employee;
import model.HourlyEmployee;
import model.PayrollEntry;
import model.PayrollReport;
import model.SalariedEmployee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import service.PayrollProcessor;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PayrollProcessorTest {

    private static final double DELTA = 0.0001;

    @AfterEach
    void tearDown() {
        PayrollProcessor.clearEmployees();
    }

    @Test
    void salariedEmployeesApplyPayrollTax() {
        Employee employee = new SalariedEmployee("Alice", 5000);

        PayrollEntry entry = PayrollProcessor.calculatePayrollEntry(employee);

        assertEquals(5000, entry.getGrossPay(), DELTA);
        assertEquals(1000, entry.getTax(), DELTA);
        assertEquals(4000, entry.getNetPay(), DELTA);
    }

    @Test
    void contractorsReceiveOvertimeWithoutTax() {
        Employee employee = new ContractorEmployee("Bob", 50, 170);

        PayrollEntry entry = PayrollProcessor.calculatePayrollEntry(employee);

        assertEquals(8750, entry.getGrossPay(), DELTA);
        assertEquals(0, entry.getTax(), DELTA);
        assertEquals(8750, entry.getNetPay(), DELTA);
    }

    @Test
    void hourlyEmployeesApplyOvertimeAndTax() {
        Employee employee = new HourlyEmployee("Dana", 30, 165, 0.15);

        PayrollEntry entry = PayrollProcessor.calculatePayrollEntry(employee);

        assertEquals(5025, entry.getGrossPay(), DELTA);
        assertEquals(753.75, entry.getTax(), DELTA);
        assertEquals(4271.25, entry.getNetPay(), DELTA);
    }

    @Test
    void payrollReportTotalsAggregateEntries() {
        Employee salaried = new SalariedEmployee("Alice", 5000);
        Employee contractor = new ContractorEmployee("Bob", 50, 170);
        Employee hourly = new HourlyEmployee("Dana", 30, 165, 0.15);

        PayrollProcessor.addEmployee(salaried);
        PayrollProcessor.addEmployee(contractor);
        PayrollProcessor.addEmployee(hourly);

        PayrollReport report = PayrollProcessor.processPayroll();

        assertEquals(18775, report.getTotalGross(), DELTA);
        assertEquals(1753.75, report.getTotalTax(), DELTA);
        assertEquals(17021.25, report.getTotalNet(), DELTA);
        assertEquals(3, report.getProcessedEmployeeCount());
    }

    @Test
    void employeeListsUsePolymorphicPayCalculation() {
        Employee[] employees = {
            new SalariedEmployee("Alice", 5000),
            new ContractorEmployee("Bob", 50, 170),
            new HourlyEmployee("Dana", 30, 165, 0.15)
        };

        double totalNetPay = 0;
        for (Employee employee : employees) {
            totalNetPay += employee.calculatePay();
        }

        assertEquals(17021.25, totalNetPay, DELTA);
    }
}
