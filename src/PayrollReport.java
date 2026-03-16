import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PayrollReport {

    private final List<PayrollEntry> entries = new ArrayList<>();
    private double totalGross;
    private double totalTax;
    private double totalNet;
    private int unknownEmployeeCount;

    public void addEntry(PayrollEntry entry) {
        entries.add(entry);
        totalGross += entry.getGrossPay();
        totalTax += entry.getTax();
        totalNet += entry.getNetPay();
    }

    public void recordUnknownEmployee() {
        unknownEmployeeCount++;
    }

    public List<PayrollEntry> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    public double getTotalGross() {
        return totalGross;
    }

    public double getTotalTax() {
        return totalTax;
    }

    public double getTotalNet() {
        return totalNet;
    }

    public int getUnknownEmployeeCount() {
        return unknownEmployeeCount;
    }

    public int getProcessedEmployeeCount() {
        return entries.size();
    }

    public int getEmployeeCount() {
        return entries.size() + unknownEmployeeCount;
    }
}
