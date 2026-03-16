package model;

public final class ContractorEmployee extends Employee {

    private final double hourlyRate;
    private final int hoursWorked;

    public ContractorEmployee(String name, double hourlyRate, int hoursWorked) {
        super(name);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public double calculatePay() {
        return calculateGrossPay();
    }

    @Override
    public double calculateGrossPay() {
        return calculateHourlyGross(hourlyRate, hoursWorked);
    }

    @Override
    public double calculateTax() {
        return 0;
    }

    @Override
    public String getEmploymentType() {
        return "contractor";
    }

    @Override
    public void validate() {
        if (hourlyRate < 0) {
            System.out.println("[warn] negative hourly rate for " + getName());
        }

        if (hoursWorked < 0) {
            System.out.println("[warn] negative hours worked for " + getName());
        }
    }
}
