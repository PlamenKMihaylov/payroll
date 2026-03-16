public class Employee {

    public String name;
    public String type; // "salaried", "contractor", "hourly"

    // salaried
    public double monthlySalary;

    // contractor, hourly
    public double hourlyRate;
    public int hoursWorked;

    // hourly
    public double taxRate;

    public Employee(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
