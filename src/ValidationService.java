public class ValidationService {

    public static void validateEmployee(Employee employee) {
        if (employee == null) {
            System.out.println("[warn] employee record is null");
            return;
        }

        if (employee.name == null || employee.name.trim().isEmpty()) {
            System.out.println("[warn] employee name is missing");
        }

        if (employee.type == null) {
            System.out.println("[warn] employee type is null for " + safeName(employee));
        }

        if (employee.hourlyRate < 0) {
            System.out.println("[warn] negative hourly rate for " + safeName(employee));
        }

        if (employee.hoursWorked < 0) {
            System.out.println("[warn] negative hours worked for " + safeName(employee));
        }

        if (employee.monthlySalary < 0) {
            System.out.println("[warn] negative monthly salary for " + safeName(employee));
        }

        if (employee.taxRate < 0 || employee.taxRate > 1) {
            System.out.println("[warn] invalid tax rate for " + safeName(employee));
        }
    }

    private static String safeName(Employee employee) {
        if (employee == null || employee.name == null || employee.name.trim().isEmpty()) {
            return "<unknown>";
        }
        return employee.name;
    }
}
