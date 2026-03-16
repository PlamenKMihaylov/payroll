package model;

public final class SalariedEmployee extends Employee {

    private static final double PAYROLL_TAX_RATE = 0.2;

    private final double monthlySalary;

    public SalariedEmployee(String name, double monthlySalary) {
        super(name);
        this.monthlySalary = monthlySalary;
    }

    @Override
    public double calculatePay() {
        return calculateGrossPay() - calculateTax();
    }

    @Override
    public double calculateGrossPay() {
        return monthlySalary;
    }

    @Override
    public double calculateTax() {
        return monthlySalary * PAYROLL_TAX_RATE;
    }

    @Override
    public String getEmploymentType() {
        return "salaried";
    }

    @Override
    public void validate() {
        if (monthlySalary < 0) {
            System.out.println("[warn] negative monthly salary for " + getName());
        }
    }
}
