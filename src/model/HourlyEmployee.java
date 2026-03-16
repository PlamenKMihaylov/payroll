package model;

public final class HourlyEmployee extends Employee {

    private final double hourlyRate;
    private final int hoursWorked;
    private final double taxRate;

    public HourlyEmployee(String name, double hourlyRate, int hoursWorked, double taxRate) {
        super(name);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
        this.taxRate = taxRate;
    }

    @Override
    public double calculatePay() {
        return calculateGrossPay() - calculateTax();
    }

    @Override
    public double calculateGrossPay() {
        return calculateHourlyGross(hourlyRate, hoursWorked);
    }

    @Override
    public double calculateTax() {
        return calculateGrossPay() * taxRate;
    }

    @Override
    public String getEmploymentType() {
        return "hourly";
    }

    @Override
    public void validate() {
        if (hourlyRate < 0) {
            System.out.println("[warn] negative hourly rate for " + getName());
        }

        if (hoursWorked < 0) {
            System.out.println("[warn] negative hours worked for " + getName());
        }

        if (taxRate < 0 || taxRate > 1) {
            System.out.println("[warn] invalid tax rate for " + getName());
        }
    }
}
