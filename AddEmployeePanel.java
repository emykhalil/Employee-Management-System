package lab7.gui;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import lab7.Employee;
import lab7.EmployeeManager;
import lab7.Department;
import lab7.FullTimeEmployee;
import lab7.PartTimeEmployee;

public class AddEmployeePanel extends JPanel {

    // Theme colors
    private final Color BACKGROUND = Color.decode("#FFF4E5");
    private final Color SIDEBAR = Color.decode("#1034A6");
    private final Color PURPLE = Color.decode("#A666E0");
    private final Color CARD = Color.decode("#FFFCF7");
    private final Color LAVENDER = new Color(248, 241, 255);

    // Fonts
    private final Font TITLE_FONT = new Font("Georgia", Font.BOLD, 28);
    private final Font BODY_FONT = new Font("Trebuchet MS", Font.PLAIN, 15);
    private final Font BUTTON_FONT = new Font("Trebuchet MS", Font.BOLD, 14);

    private EmployeeManager employeeManager;

    // Common employee fields
    private JTextField idField;
    private JTextField nameField;
    private JTextField ageField;
    private JTextField positionField;

    private JComboBox<Department> departmentComboBox;
    private JComboBox<String> employeeTypeComboBox;

    // Full-time fields
    private JTextField basicSalaryField;
    private JTextField bonusField;

    // Part-time fields
    private JTextField hourlyRateField;
    private JTextField hoursWorkedField;

    // Changes salary fields according to employee type
    private JPanel salaryPanel;
    private CardLayout salaryLayout;

    public AddEmployeePanel(EmployeeManager employeeManager) {

        this.employeeManager = employeeManager;

        setLayout(new BorderLayout());
        setBackground(BACKGROUND);

        // Page title
        JLabel titleLabel = new JLabel("Add New Employee");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(SIDEBAR);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(new EmptyBorder(25, 10, 15, 10));

        add(titleLabel, BorderLayout.NORTH);

        // Main form card
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PURPLE, 2),
            new EmptyBorder(25, 35, 25, 35)
        ));

        // Form fields
        createForm(card);

        // Center the form
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(BACKGROUND);
        centerPanel.add(card);

        add(centerPanel, BorderLayout.CENTER);
    }

    private void createForm(JPanel card) {

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        int row = 0;

        // Employee ID
        idField = createTextField();
        addFormRow(card, gbc, row++, "Employee ID:", idField);

        // Full name
        nameField = createTextField();
        addFormRow(card, gbc, row++, "Full Name:", nameField);

        // Age
        ageField = createTextField();
        addFormRow(card, gbc, row++, "Age:", ageField);

        // Department dropdown
        departmentComboBox = new JComboBox<>();
        departmentComboBox.setFont(BODY_FONT);
        departmentComboBox.setBackground(Color.WHITE);

        loadDepartments();

        addFormRow(
            card, gbc, row++, "Department:", departmentComboBox
        );

        // Position
        positionField = createTextField();
        addFormRow(card, gbc, row++, "Position:", positionField);

        // Employee type
        employeeTypeComboBox = new JComboBox<>(
            new String[]{"Full-Time", "Part-Time"}
        );

        employeeTypeComboBox.setFont(BODY_FONT);
        employeeTypeComboBox.setBackground(Color.WHITE);

        addFormRow(
            card, gbc, row++, "Employee Type:", employeeTypeComboBox
        );

        // Salary fields
        salaryLayout = new CardLayout();
        salaryPanel = new JPanel(salaryLayout);
        salaryPanel.setBackground(CARD);

        createSalaryPanels();

        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.weightx = 1;

        card.add(salaryPanel, gbc);

        // Change salary fields when employee type changes
        employeeTypeComboBox.addActionListener(e -> {

            String selectedType =
                (String) employeeTypeComboBox.getSelectedItem();

            salaryLayout.show(salaryPanel, selectedType);
        });

        // Add employee button
        JButton addButton = new JButton("Add Employee");

        addButton.setFont(BUTTON_FONT);
        addButton.setForeground(Color.WHITE);
        addButton.setBackground(PURPLE);
        addButton.setFocusPainted(false);
        addButton.setBorderPainted(false);
        addButton.setOpaque(true);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        addButton.addActionListener(e -> addEmployee());

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        card.add(addButton, gbc);
    }

    // Creates the two different salary forms
    private void createSalaryPanels() {

        // Full-time salary form
        JPanel fullTimePanel = new JPanel(new GridBagLayout());
        fullTimePanel.setBackground(CARD);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        basicSalaryField = createTextField();
        bonusField = createTextField();

        addFormRow(
            fullTimePanel, gbc, 0,
            "Basic Salary:", basicSalaryField
        );

        addFormRow(
            fullTimePanel, gbc, 1,
            "Monthly Bonus:", bonusField
        );

        // Part-time salary form
        JPanel partTimePanel = new JPanel(new GridBagLayout());
        partTimePanel.setBackground(CARD);

        hourlyRateField = createTextField();
        hoursWorkedField = createTextField();

        addFormRow(
            partTimePanel, gbc, 0,
            "Hourly Rate:", hourlyRateField
        );

        addFormRow(
            partTimePanel, gbc, 1,
            "Hours Worked:", hoursWorkedField
        );

        salaryPanel.add(fullTimePanel, "Full-Time");
        salaryPanel.add(partTimePanel, "Part-Time");
    }

    // Loads existing departments into the dropdown
    private void loadDepartments() {

        departmentComboBox.removeAllItems();

        for (Department department :
                employeeManager.getAllDepartments()) {

            departmentComboBox.addItem(department);
        }

        // Display department names instead of object references
        departmentComboBox.setRenderer(new DefaultListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(
                JList<?> list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus
            ) {

                super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus
                );

                if (value instanceof Department) {
                    setText(
                        ((Department) value).getDepartmentName()
                    );
                }

                return this;
            }
        });
    }

    // Creates a text field with the project theme
    private JTextField createTextField() {

        JTextField field = new JTextField(15);

        field.setFont(BODY_FONT);
        field.setBackground(Color.WHITE);
        field.setForeground(Color.BLACK);

        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(
                new Color(216, 196, 240), 1
            ),
            new EmptyBorder(6, 8, 6, 8)
        ));

        return field;
    }

    // Adds a label and its field to a form row
    private void addFormRow(
        JPanel panel,
        GridBagConstraints gbc,
        int row,
        String labelText,
        Component field
    ) {

        JLabel label = new JLabel(labelText);
        label.setFont(BODY_FONT);
        label.setForeground(SIDEBAR);

        gbc.gridwidth = 1;
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.weightx = 0.4;

        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.6;

        panel.add(field, gbc);
    }

    // Reads the form and adds the employee
    private void addEmployee() {

        try {

            // Check required common fields
            if (idField.getText().trim().isEmpty()
                    || nameField.getText().trim().isEmpty()
                    || ageField.getText().trim().isEmpty()
                    || positionField.getText().trim().isEmpty()) {

                showError("Please complete all employee information.");
                return;
            }

            // Check department selection
            Department selectedDepartment =
                (Department) departmentComboBox.getSelectedItem();

            if (selectedDepartment == null) {
                showError("Please select a department.");
                return;
            }

            // Read common information
            int id = Integer.parseInt(idField.getText().trim());
            int age = Integer.parseInt(ageField.getText().trim());

            String name = nameField.getText().trim();
            String position = positionField.getText().trim();

            if (id <= 0) {
                showError("Employee ID must be greater than zero.");
                return;
            }

            if (age <= 0) {
                showError("Age must be greater than zero.");
                return;
            }

            // Check whether ID already exists
            if (!employeeManager.isEmployeeIdUnique(id)) {
                showError("This employee ID already exists.");
                return;
            }

            Employee employee;

            String selectedType =
                (String) employeeTypeComboBox.getSelectedItem();

            // Create the correct employee object
            if ("Full-Time".equals(selectedType)) {

                double basicSalary = Double.parseDouble(
                    basicSalaryField.getText().trim()
                );

                double bonus = Double.parseDouble(
                    bonusField.getText().trim()
                );

                if (!Double.isFinite(basicSalary)
                        || !Double.isFinite(bonus)
                        || basicSalary < 0 || bonus < 0) {

                    showError(
                        "Salary and bonus must be valid non-negative numbers."
                    );
                    return;
                }

                employee = new FullTimeEmployee(
                    id, name, age,
                    selectedDepartment, position,
                    basicSalary, bonus
                );

            } else {

                double hourlyRate = Double.parseDouble(
                    hourlyRateField.getText().trim()
                );

                double hoursWorked = Double.parseDouble(
                    hoursWorkedField.getText().trim()
                );

                if (!Double.isFinite(hourlyRate)
                        || !Double.isFinite(hoursWorked)
                        || hourlyRate < 0 || hoursWorked < 0) {

                    showError(
                        "Hourly rate and hours must be valid non-negative numbers."
                    );
                    return;
                }

                employee = new PartTimeEmployee(
                    id, name, age,
                    selectedDepartment, position,
                    hourlyRate, hoursWorked
                );
            }

            // Add employee through EmployeeManager
            if (employeeManager.addEmployee(employee)) {

                JOptionPane.showMessageDialog(
                    this,
                    "Employee added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );

                clearFields();

            } else {

                showError(
                    "Could not add employee. Please check the information."
                );
            }

        } catch (NumberFormatException ex) {

            showError(
                "Please enter valid numeric values in the ID, age, and salary fields."
            );
        }
    }

    // Displays an error message
    private void showError(String message) {

        JOptionPane.showMessageDialog(
            this,
            message,
            "Input Error",
            JOptionPane.ERROR_MESSAGE
        );
    }

    // Clears the form after a successful addition
    private void clearFields() {

        idField.setText("");
        nameField.setText("");
        ageField.setText("");
        positionField.setText("");

        basicSalaryField.setText("");
        bonusField.setText("");

        hourlyRateField.setText("");
        hoursWorkedField.setText("");

        departmentComboBox.setSelectedIndex(-1);
        employeeTypeComboBox.setSelectedIndex(0);

        salaryLayout.show(salaryPanel, "Full-Time");
    }
}
