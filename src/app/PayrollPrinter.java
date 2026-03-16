package app;

import helper.CurrencyFormatter;
import model.PayrollEntry;
import model.PayrollReport;

public class PayrollPrinter {

    public static void printReport(PayrollReport report) {
        System.out.println("Payroll report:");

        for (PayrollEntry entry : report.getEntries()) {
            System.out.println(
                entry.getEmployeeName()
                    + " | "
                    + entry.getEmploymentType()
                    + " | gross: "
                    + CurrencyFormatter.format(entry.getGrossPay())
                    + " | tax: "
                    + CurrencyFormatter.format(entry.getTax())
                    + " | net: "
                    + CurrencyFormatter.format(entry.getNetPay())
            );
        }

        if (report.getUnknownEmployeeCount() > 0) {
            System.out.println("Unknown employees skipped: " + report.getUnknownEmployeeCount());
        }

        System.out.println("Totals:");
        System.out.println("  gross: " + CurrencyFormatter.format(report.getTotalGross()));
        System.out.println("  tax: " + CurrencyFormatter.format(report.getTotalTax()));
        System.out.println("  net: " + CurrencyFormatter.format(report.getTotalNet()));
    }
}
