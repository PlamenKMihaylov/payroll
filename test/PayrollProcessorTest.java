import model.ContractorEmployee;
import model.Employee;
import model.HourlyEmployee;
import model.PayrollEntry;
import model.PayrollReport;
import model.SalariedEmployee;
import service.PayrollProcessor;

public class PayrollProcessorTest {

    private static final double DELTA = 0.0001;

    public static void main(String[] args) {
        run("salaried employees apply payroll tax", PayrollProcessorTest::testSalariedEmployeeTax);
        run("contractors receive overtime without tax", PayrollProcessorTest::testContractorOvertime);
        run("hourly employees apply overtime and tax", PayrollProcessorTest::testHourlyEmployeeOvertimeAndTax);
        run("payroll report totals aggregate entries", PayrollProcessorTest::testPayrollReportTotals);
        run("employee lists use polymorphic pay calculation", PayrollProcessorTest::testPolymorphicPayCalculation);

        System.out.println("All tests passed.");
    }

    private static void testSalariedEmployeeTax() {
        Employee employee = new SalariedEmployee("Alice", 5000);

        PayrollEntry entry = PayrollProcessor.calculatePayrollEntry(employee);

        assertEquals(5000, entry.getGrossPay(), "gross pay");
        assertEquals(1000, entry.getTax(), "tax");
        assertEquals(4000, entry.getNetPay(), "net pay");
    }

    private static void testContractorOvertime() {
        Employee employee = new ContractorEmployee("Bob", 50, 170);

        PayrollEntry entry = PayrollProcessor.calculatePayrollEntry(employee);

        assertEquals(8750, entry.getGrossPay(), "gross pay");
        assertEquals(0, entry.getTax(), "tax");
        assertEquals(8750, entry.getNetPay(), "net pay");
    }

    private static void testHourlyEmployeeOvertimeAndTax() {
        Employee employee = new HourlyEmployee("Dana", 30, 165, 0.15);

        PayrollEntry entry = PayrollProcessor.calculatePayrollEntry(employee);

        assertEquals(5025, entry.getGrossPay(), "gross pay");
        assertEquals(753.75, entry.getTax(), "tax");
        assertEquals(4271.25, entry.getNetPay(), "net pay");
    }

    private static void testPayrollReportTotals() {
        PayrollProcessor.clearEmployees();

        Employee salaried = new SalariedEmployee("Alice", 5000);
        Employee contractor = new ContractorEmployee("Bob", 50, 170);
        Employee hourly = new HourlyEmployee("Dana", 30, 165, 0.15);

        PayrollProcessor.addEmployee(salaried);
        PayrollProcessor.addEmployee(contractor);
        PayrollProcessor.addEmployee(hourly);

        PayrollReport report = PayrollProcessor.processPayroll();

        assertEquals(18775, report.getTotalGross(), "total gross");
        assertEquals(1753.75, report.getTotalTax(), "total tax");
        assertEquals(17021.25, report.getTotalNet(), "total net");
        assertEquals(3, report.getProcessedEmployeeCount(), "processed employees");
    }

    private static void testPolymorphicPayCalculation() {
        Employee[] employees = {
            new SalariedEmployee("Alice", 5000),
            new ContractorEmployee("Bob", 50, 170),
            new HourlyEmployee("Dana", 30, 165, 0.15)
        };

        double totalNetPay = 0;
        for (Employee employee : employees) {
            totalNetPay += employee.calculatePay();
        }

        assertEquals(17021.25, totalNetPay, "polymorphic total net pay");
    }

    private static void run(String name, TestCase testCase) {
        try {
            testCase.run();
            PayrollProcessor.clearEmployees();
            System.out.println("[PASS] " + name);
        } catch (RuntimeException ex) {
            throw new RuntimeException("[FAIL] " + name + ": " + ex.getMessage(), ex);
        }
    }

    private static void assertEquals(double expected, double actual, String label) {
        if (Math.abs(expected - actual) > DELTA) {
            throw new IllegalStateException(label + " expected " + expected + " but was " + actual);
        }
    }

    private static void assertEquals(int expected, int actual, String label) {
        if (expected != actual) {
            throw new IllegalStateException(label + " expected " + expected + " but was " + actual);
        }
    }

    private interface TestCase {
        void run();
    }
}
