
package lab7.gui;

import javax.swing.SwingUtilities;
import java.util.List;

import lab7.Employee;
import lab7.Department;
import lab7.EmployeeManager;
import lab7.FileHandler;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            // Create file handler
            FileHandler fileHandler = new FileHandler();

            // Load existing data
            List<Employee> employees =
                fileHandler.loadEmployees();

            List<Department> departments =
                fileHandler.loadDepartments();

            // Create backend manager
            EmployeeManager employeeManager =
                new EmployeeManager(
                    employees,
                    departments,
                    fileHandler
                );

            // Open login screen
            new LoginFrame(employeeManager);

        });
    }
}
