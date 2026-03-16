public class Main {

    public static void main(String[] args) {
        Employee e1 = new Employee("Alice", "salaried");
        e1.monthlySalary = 5000;

        Employee e2 = new Employee("Bob", "contractor");
        e2.hourlyRate = 50;
        e2.hoursWorked = 170;

        Employee e3 = new Employee("Charlie", "contractor");
        e3.hourlyRate = 60;
        e3.hoursWorked = 0;

        Employee e4 = new Employee("Dana", "hourly");
        e4.hourlyRate = 30;
        e4.hoursWorked = 165;
        e4.taxRate = 0.15;

        PayrollProcessor.addEmployee(e1);
        PayrollProcessor.addEmployee(e2);
        PayrollProcessor.addEmployee(e3);
        PayrollProcessor.addEmployee(e4);

        PayrollReport report = PayrollProcessor.processPayroll();
        PayrollPrinter.printReport(report);
    }
}
