public class PayrollEntry {

    private final String employeeName;
    private final String employmentType;
    private final double grossPay;
    private final double tax;
    private final double netPay;

    public PayrollEntry(String employeeName, String employmentType, double grossPay, double tax, double netPay) {
        this.employeeName = employeeName;
        this.employmentType = employmentType;
        this.grossPay = grossPay;
        this.tax = tax;
        this.netPay = netPay;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public double getGrossPay() {
        return grossPay;
    }

    public double getTax() {
        return tax;
    }

    public double getNetPay() {
        return netPay;
    }
}
