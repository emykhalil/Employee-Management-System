
package lab7.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

import lab7.Employee;
import lab7.EmployeeManager;
import lab7.FullTimeEmployee;
import lab7.PartTimeEmployee;

public class ViewEmployeesFrame extends JPanel {

    // Theme colors
    private final Color BACKGROUND = Color.decode("#FFF4E5");
    private final Color SIDEBAR = Color.decode("#1034A6");
    private final Color PURPLE = Color.decode("#A666E0");
    private final Color CARD = Color.decode("#FFFCF7");

    // Fonts
    private final Font TITLE_FONT = new Font("Georgia", Font.BOLD, 28);
    private final Font BODY_FONT = new Font("Trebuchet MS", Font.PLAIN, 14);
    private final Font BUTTON_FONT = new Font("Trebuchet MS", Font.BOLD, 14);

    private EmployeeManager employeeManager;

    private JTable employeeTable;
    private DefaultTableModel tableModel;

    public ViewEmployeesFrame(EmployeeManager employeeManager) {

        this.employeeManager = employeeManager;

        setLayout(new BorderLayout(15, 15));
        setBackground(BACKGROUND);
        setBorder(new EmptyBorder(25, 25, 25, 25));

        // Page title
        JLabel titleLabel = new JLabel("View Employees");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(SIDEBAR);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(titleLabel, BorderLayout.NORTH);

        // Table columns
        String[] columns = {
            "ID",
            "Full Name",
            "Age",
            "Department",
            "Position",
            "Employee Type",
            "Salary Details",
            "Calculated Salary"
        };

        // Create a non-editable table model
        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        employeeTable = new JTable(tableModel);

        employeeTable.setFont(BODY_FONT);
        employeeTable.setRowHeight(30);
        // Allow columns to keep their assigned widths
employeeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

// Set a suitable width for each column
int[] columnWidths = {
    70,   // ID
    160,  // Full Name
    65,   // Age
    170,  // Department
    190,  // Position
    130,  // Employee Type
    250,  // Salary Details
    150   // Calculated Salary
};

for (int i = 0; i < columnWidths.length; i++) {
    employeeTable.getColumnModel()
        .getColumn(i)
        .setPreferredWidth(columnWidths[i]);
}
        employeeTable.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
        );

        employeeTable.setBackground(CARD);
        employeeTable.setForeground(SIDEBAR);
        employeeTable.setSelectionBackground(
            new Color(225, 205, 250)
        );
        employeeTable.setSelectionForeground(SIDEBAR);

        employeeTable.getTableHeader().setFont(BUTTON_FONT);
        employeeTable.getTableHeader().setBackground(PURPLE);
        employeeTable.getTableHeader().setForeground(Color.WHITE);
        employeeTable.getTableHeader().setReorderingAllowed(false);

        // Put the table inside a scroll pane
    JScrollPane scrollPane = new JScrollPane(
        employeeTable,
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );       
    scrollPane.getViewport().setBackground(CARD);

        scrollPane.setBorder(
            BorderFactory.createLineBorder(PURPLE, 2)
        );

        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = new JPanel(
            new FlowLayout(FlowLayout.CENTER, 15, 5)
        );

        bottomPanel.setBackground(BACKGROUND);

        JButton refreshButton = new JButton("Refresh");

        refreshButton.setFont(BUTTON_FONT);
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setBackground(PURPLE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorderPainted(false);
        refreshButton.setOpaque(true);
        refreshButton.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
        );

        refreshButton.addActionListener(e -> loadEmployees());

        bottomPanel.add(refreshButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // Load employees when the panel opens
        loadEmployees();
    }

    // Loads employee information into the table
    public void loadEmployees() {

        tableModel.setRowCount(0);

        List<Employee> employees =
            employeeManager.getAllEmployees();

        for (Employee employee : employees) {

            String employeeType;
            String salaryDetails;

            // Full-time employee information
            if (employee instanceof FullTimeEmployee) {

                FullTimeEmployee fullTime =
                    (FullTimeEmployee) employee;

                employeeType = "Full-Time";

                salaryDetails =
                    "Basic: " + fullTime.getBasicSalary()
                    + ", Bonus: " + fullTime.getBonus();

            }

            // Part-time employee information
            else if (employee instanceof PartTimeEmployee) {

                PartTimeEmployee partTime =
                    (PartTimeEmployee) employee;

                employeeType = "Part-Time";

                salaryDetails =
                    "Rate: " + partTime.getHourlyRate()
                    + ", Hours: " + partTime.getHoursWorked();

            }

            else {

                employeeType = "Unknown";
                salaryDetails = "N/A";
            }

            Object[] row = {

                employee.getId(),
                employee.getFullName(),
                employee.getAge(),
                employee.getDepartment().getDepartmentName(),
                employee.getPosition(),
                employeeType,
                salaryDetails,
                String.format("%.2f", employee.calculateSalary())
            };

            tableModel.addRow(row);
        }
    }
}
