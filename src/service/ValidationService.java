package service;

import model.Employee;

public class ValidationService {

    public static void validateEmployee(Employee employee) {
        if (employee == null) {
            System.out.println("[warn] employee record is null");
            return;
        }

        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            System.out.println("[warn] employee name is missing");
        }
        employee.validate();
    }
}
