package model;

public abstract sealed class Employee permits SalariedEmployee, ContractorEmployee, HourlyEmployee {

    private static final int REGULAR_HOURS_LIMIT = 160;
    private static final double OVERTIME_MULTIPLIER = 1.5;

    private final String name;

    protected Employee(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract double calculatePay();

    public abstract double calculateGrossPay();

    public abstract double calculateTax();

    public abstract String getEmploymentType();

    public abstract void validate();

    protected double calculateHourlyGross(double hourlyRate, int hoursWorked) {
        int regularHours = Math.min(hoursWorked, REGULAR_HOURS_LIMIT);
        int overtimeHours = Math.max(hoursWorked - REGULAR_HOURS_LIMIT, 0);
        return (regularHours * hourlyRate) + (overtimeHours * hourlyRate * OVERTIME_MULTIPLIER);
    }
}
