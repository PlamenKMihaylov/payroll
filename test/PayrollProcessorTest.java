public class PayrollProcessorTest {

    private static final double DELTA = 0.0001;

    public static void main(String[] args) {
        run("salaried employees apply payroll tax", PayrollProcessorTest::testSalariedEmployeeTax);
        run("contractors receive overtime without tax", PayrollProcessorTest::testContractorOvertime);
        run("hourly employees apply overtime and tax", PayrollProcessorTest::testHourlyEmployeeOvertimeAndTax);
        run("payroll report totals aggregate entries", PayrollProcessorTest::testPayrollReportTotals);
        run("unknown employee types are skipped and counted", PayrollProcessorTest::testUnknownEmployees);

        System.out.println("All tests passed.");
    }

    private static void testSalariedEmployeeTax() {
        Employee employee = new Employee("Alice", "salaried");
        employee.monthlySalary = 5000;

        PayrollEntry entry = PayrollProcessor.calculatePayrollEntry(employee);

        assertEquals(5000, entry.getGrossPay(), "gross pay");
        assertEquals(1000, entry.getTax(), "tax");
        assertEquals(4000, entry.getNetPay(), "net pay");
    }

    private static void testContractorOvertime() {
        Employee employee = new Employee("Bob", "contractor");
        employee.hourlyRate = 50;
        employee.hoursWorked = 170;

        PayrollEntry entry = PayrollProcessor.calculatePayrollEntry(employee);

        assertEquals(8750, entry.getGrossPay(), "gross pay");
        assertEquals(0, entry.getTax(), "tax");
        assertEquals(8750, entry.getNetPay(), "net pay");
    }

    private static void testHourlyEmployeeOvertimeAndTax() {
        Employee employee = new Employee("Dana", "hourly");
        employee.hourlyRate = 30;
        employee.hoursWorked = 165;
        employee.taxRate = 0.15;

        PayrollEntry entry = PayrollProcessor.calculatePayrollEntry(employee);

        assertEquals(5025, entry.getGrossPay(), "gross pay");
        assertEquals(753.75, entry.getTax(), "tax");
        assertEquals(4271.25, entry.getNetPay(), "net pay");
    }

    private static void testPayrollReportTotals() {
        PayrollProcessor.clearEmployees();

        Employee salaried = new Employee("Alice", "salaried");
        salaried.monthlySalary = 5000;

        Employee contractor = new Employee("Bob", "contractor");
        contractor.hourlyRate = 50;
        contractor.hoursWorked = 170;

        Employee hourly = new Employee("Dana", "hourly");
        hourly.hourlyRate = 30;
        hourly.hoursWorked = 165;
        hourly.taxRate = 0.15;

        PayrollProcessor.addEmployee(salaried);
        PayrollProcessor.addEmployee(contractor);
        PayrollProcessor.addEmployee(hourly);

        PayrollReport report = PayrollProcessor.processPayroll();

        assertEquals(18775, report.getTotalGross(), "total gross");
        assertEquals(1753.75, report.getTotalTax(), "total tax");
        assertEquals(17021.25, report.getTotalNet(), "total net");
        assertEquals(3, report.getProcessedEmployeeCount(), "processed employees");
    }

    private static void testUnknownEmployees() {
        PayrollProcessor.clearEmployees();

        Employee employee = new Employee("Mystery", "intern");
        PayrollProcessor.addEmployee(employee);

        PayrollReport report = PayrollProcessor.processPayroll();

        assertEquals(0, report.getProcessedEmployeeCount(), "processed employees");
        assertEquals(1, report.getUnknownEmployeeCount(), "unknown employees");
        assertEquals(0, report.getTotalGross(), "total gross");
        assertEquals(0, report.getTotalTax(), "total tax");
        assertEquals(0, report.getTotalNet(), "total net");
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
