
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

public class DeleteEmployeePanel extends JPanel {

    // Theme colors
    private final Color BACKGROUND = Color.decode("#FFF4E5");
    private final Color SIDEBAR = Color.decode("#1034A6");
    private final Color PURPLE = Color.decode("#A666E0");
    private final Color CARD = Color.decode("#FFFCF7");

    // Fonts
    private final Font TITLE_FONT =
        new Font("Georgia", Font.BOLD, 28);

    private final Font BODY_FONT =
        new Font("Trebuchet MS", Font.PLAIN, 14);

    private final Font BUTTON_FONT =
        new Font("Trebuchet MS", Font.BOLD, 14);

    private EmployeeManager employeeManager;

    private JTable employeeTable;
    private DefaultTableModel tableModel;

    public DeleteEmployeePanel(EmployeeManager employeeManager) {

        this.employeeManager = employeeManager;

        setLayout(new BorderLayout(15, 15));
        setBackground(BACKGROUND);
        setBorder(new EmptyBorder(25, 25, 25, 25));

        // Page title
        JLabel titleLabel = new JLabel("Delete Employee");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(SIDEBAR);
        titleLabel.setHorizontalAlignment(
            SwingConstants.CENTER
        );

        add(titleLabel, BorderLayout.NORTH);

        // Table columns
        String[] columns = {
            "Employee ID",
            "Full Name",
            "Department",
            "Position",
            "Employee Type"
        };

        // Create a non-editable table
        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        employeeTable = new JTable(tableModel);

        employeeTable.setFont(BODY_FONT);
        employeeTable.setRowHeight(32);
        // Allow horizontal scrolling instead of shrinking columns
employeeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

// Set suitable widths for each column
    int[] columnWidths = {
    100,  // Employee ID
    200,  // Full Name
    200,  // Department
    220,  // Position
    150   // Employee Type 
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

    JScrollPane scrollPane = new JScrollPane(
        employeeTable,
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        scrollPane.setBorder(
            BorderFactory.createLineBorder(PURPLE, 2)
        );

        scrollPane.getViewport().setBackground(CARD);

        add(scrollPane, BorderLayout.CENTER);

        // Bottom buttons
        JPanel bottomPanel = new JPanel(
            new FlowLayout(FlowLayout.CENTER, 20, 10)
        );

        bottomPanel.setBackground(BACKGROUND);

        JButton deleteButton = new JButton(
            "Delete Selected Employee"
        );

        styleButton(deleteButton);

        deleteButton.addActionListener(e -> deleteEmployee());

        JButton refreshButton = new JButton("Refresh");

        styleButton(refreshButton);

        refreshButton.addActionListener(e -> loadEmployees());

        bottomPanel.add(deleteButton);
        bottomPanel.add(refreshButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // Load employees when page opens
        loadEmployees();
    }

    // Gives buttons the project theme
    private void styleButton(JButton button) {

        button.setFont(BUTTON_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(PURPLE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
        );
    }

    // Loads all employees into the table
    public void loadEmployees() {

        tableModel.setRowCount(0);

        List<Employee> employees =
            employeeManager.getAllEmployees();

        for (Employee employee : employees) {

            String employeeType;

            if (employee instanceof FullTimeEmployee) {
                employeeType = "Full-Time";
            }
            else if (employee instanceof PartTimeEmployee) {
                employeeType = "Part-Time";
            }
            else {
                employeeType = "Unknown";
            }

            Object[] row = {
                employee.getId(),
                employee.getFullName(),
                employee.getDepartment().getDepartmentName(),
                employee.getPosition(),
                employeeType
            };

            tableModel.addRow(row);
        }
    }

    // Deletes the selected employee
    private void deleteEmployee() {

        int selectedRow = employeeTable.getSelectedRow();

        // Check whether an employee was selected
        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                this,
                "Please select an employee first.",
                "No Employee Selected",
                JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Get the selected employee ID
        int employeeId = Integer.parseInt(
            tableModel.getValueAt(selectedRow, 0).toString()
        );

        String employeeName =
            tableModel.getValueAt(selectedRow, 1).toString();

        // Ask for confirmation
        int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete "
                + employeeName + "?",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {

            // Delete through EmployeeManager
            boolean deleted =
                employeeManager.deleteEmployee(employeeId);

            if (deleted) {

                JOptionPane.showMessageDialog(
                    this,
                    "Employee deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );

                loadEmployees();

            } else {

                JOptionPane.showMessageDialog(
                    this,
                    "Employee could not be deleted.",
                    "Deletion Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
} 