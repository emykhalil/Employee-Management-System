
package lab7.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

import lab7.Employee;
import lab7.EmployeeManager;
import lab7.Department;
import lab7.FullTimeEmployee;
import lab7.PartTimeEmployee;

public class SearchUpdatePanel extends JPanel {

    // Theme colors
    private final Color BACKGROUND = Color.decode("#FFF4E5");
    private final Color SIDEBAR = Color.decode("#1034A6");
    private final Color PURPLE = Color.decode("#A666E0");
    private final Color CARD = Color.decode("#FFFCF7");

    // Fonts
    private final Font TITLE_FONT =
        new Font("Georgia", Font.BOLD, 26);

    private final Font BODY_FONT =
        new Font("Trebuchet MS", Font.PLAIN, 14);

    private final Font BUTTON_FONT =
        new Font("Trebuchet MS", Font.BOLD, 13);

    private EmployeeManager employeeManager;
    private int originalId;

    private JTextField searchField;
    private JComboBox<String> searchType;

    private JTable employeeTable;
    private DefaultTableModel tableModel;

    private JTextField idField;
    private JTextField nameField;
    private JTextField ageField;
    private JTextField positionField;

    private JComboBox<Department> departmentBox;

    private JTextField basicSalaryField;
    private JTextField bonusField;
    private JTextField hourlyRateField;
    private JTextField hoursWorkedField;

    private JPanel salaryPanel;
    private CardLayout salaryLayout;

    private String selectedEmployeeType;

    public SearchUpdatePanel(EmployeeManager employeeManager) {

        this.employeeManager = employeeManager;

        setLayout(new BorderLayout(12, 12));
        setBackground(BACKGROUND);
        setBorder(new EmptyBorder(20, 25, 20, 25));

        JLabel title = new JLabel("Search & Update Employees");
        title.setFont(TITLE_FONT);
        title.setForeground(SIDEBAR);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        add(title, BorderLayout.NORTH);

        // Main content: search/table on the left, form on the right
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        mainPanel.setBackground(BACKGROUND);

        mainPanel.add(createSearchPanel());
        mainPanel.add(createUpdatePanel());

        add(mainPanel, BorderLayout.CENTER);
    }

    // ---------------- SEARCH SECTION ----------------

    private JPanel createSearchPanel() {

        JPanel panel = new JPanel(new BorderLayout(10, 12));
        panel.setBackground(CARD);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PURPLE, 2),
            new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel heading = new JLabel("Find Employee");
        heading.setFont(new Font("Georgia", Font.BOLD, 19));
        heading.setForeground(SIDEBAR);

        JPanel topPanel = new JPanel(new BorderLayout(5, 10));
        topPanel.setBackground(CARD);

        topPanel.add(heading, BorderLayout.NORTH);

        JPanel searchControls = new JPanel(new GridLayout(3, 1, 5, 5));
        searchControls.setBackground(CARD);

        searchType = new JComboBox<>(
            new String[]{"Employee ID", "Department"}
        );

        searchType.setFont(BODY_FONT);

        searchField = new JTextField();
        searchField.setFont(BODY_FONT);

        JButton searchButton = createButton("Search");
        searchButton.addActionListener(e -> searchEmployees());

        searchControls.add(searchType);
        searchControls.add(searchField);
        searchControls.add(searchButton);

        topPanel.add(searchControls, BorderLayout.CENTER);

        panel.add(topPanel, BorderLayout.NORTH);

        // Results table
        String[] columns = {
            "ID", "Full Name", "Department", "Type"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        employeeTable = new JTable(tableModel);
        styleTable(employeeTable);

        employeeTable.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {
                loadSelectedEmployee();
            }
        });

        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setBorder(
            BorderFactory.createLineBorder(PURPLE, 1)
        );

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // Search by ID or Department
    private void searchEmployees() {

        tableModel.setRowCount(0);

        String value = searchField.getText().trim();

        if (value.isEmpty()) {
            showMessage("Please enter a search value.");
            return;
        }

        List<Employee> results;

        if ("Employee ID".equals(searchType.getSelectedItem())) {

            try {

                int id = Integer.parseInt(value);

                Employee employee = employeeManager.searchById(id);

                if (employee == null) {
                    showMessage("No employee found with this ID.");
                    return;
                }

                results = new java.util.ArrayList<>();
                results.add(employee);

            } catch (NumberFormatException ex) {

                showMessage("Employee ID must be a valid integer.");
                return;
            }

        } else {

            results = employeeManager.searchByDepartment(value);

            if (results == null || results.isEmpty()) {
                showMessage("No employees found in this department.");
                return;
            }
        }

        for (Employee employee : results) {

            String type = getEmployeeType(employee);

            tableModel.addRow(new Object[]{
                employee.getId(),
                employee.getFullName(),
                employee.getDepartment().getDepartmentName(),
                type
            });
        }
    }

    // ---------------- UPDATE FORM ----------------

    private JPanel createUpdatePanel() {

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(CARD);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PURPLE, 2),
            new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel heading = new JLabel("Employee Information");
        heading.setFont(new Font("Georgia", Font.BOLD, 19));
        heading.setForeground(SIDEBAR);

        panel.add(heading, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(CARD);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 5, 6, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        idField = createField();
        nameField = createField();
        ageField = createField();
        positionField = createField();

        departmentBox = new JComboBox<>();
        departmentBox.setFont(BODY_FONT);

        loadDepartments();

        addFormRow(form, gbc, row++, "Employee ID:", idField);
        addFormRow(form, gbc, row++, "Full Name:", nameField);
        addFormRow(form, gbc, row++, "Age:", ageField);
        addFormRow(form, gbc, row++, "Department:", departmentBox);
        addFormRow(form, gbc, row++, "Position:", positionField);

        // Salary information
        salaryLayout = new CardLayout();
        salaryPanel = new JPanel(salaryLayout);
        salaryPanel.setBackground(CARD);

        createSalaryForms();

        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        form.add(salaryPanel, gbc);

        JButton updateButton = createButton("Save Changes");
        updateButton.addActionListener(e -> updateEmployee());

        JButton clearButton = createButton("Clear Form");
        clearButton.addActionListener(e -> clearForm());

        JPanel buttons = new JPanel(
            new FlowLayout(FlowLayout.CENTER, 10, 5)
        );

        buttons.setBackground(CARD);
        buttons.add(updateButton);
        buttons.add(clearButton);

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(CARD);
        center.add(form, BorderLayout.NORTH);

        panel.add(center, BorderLayout.CENTER);
        panel.add(buttons, BorderLayout.SOUTH);

        // Initially disable editing until an employee is selected
        setFormEnabled(false);

        return panel;
    }

    private void createSalaryForms() {

        JPanel fullTimePanel = new JPanel(new GridBagLayout());
        fullTimePanel.setBackground(CARD);

        JPanel partTimePanel = new JPanel(new GridBagLayout());
        partTimePanel.setBackground(CARD);

        basicSalaryField = createField();
        bonusField = createField();

        hourlyRateField = createField();
        hoursWorkedField = createField();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 5, 6, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addFormRow(
            fullTimePanel, gbc, 0,
            "Basic Salary:", basicSalaryField
        );

        addFormRow(
            fullTimePanel, gbc, 1,
            "Bonus:", bonusField
        );

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

    // ---------------- LOAD SELECTED EMPLOYEE ----------------

    private void loadSelectedEmployee() {

        int selectedRow = employeeTable.getSelectedRow();

        if (selectedRow == -1) {
            return;
        }

        int id = Integer.parseInt(
            tableModel.getValueAt(selectedRow, 0).toString()
        );

        Employee employee = employeeManager.searchById(id);

        if (employee == null) {
            return;
        }

        originalId = employee.getId();

        idField.setText(String.valueOf(employee.getId()));
        nameField.setText(employee.getFullName());
        ageField.setText(String.valueOf(employee.getAge()));
        positionField.setText(employee.getPosition());

        selectDepartment(employee.getDepartment());

        if (employee instanceof FullTimeEmployee) {

            FullTimeEmployee fullTime =
                (FullTimeEmployee) employee;

            selectedEmployeeType = "Full-Time";

            basicSalaryField.setText(
                String.valueOf(fullTime.getBasicSalary())
            );

            bonusField.setText(
                String.valueOf(fullTime.getBonus())
            );

        } else if (employee instanceof PartTimeEmployee) {

            PartTimeEmployee partTime =
                (PartTimeEmployee) employee;

            selectedEmployeeType = "Part-Time";

            hourlyRateField.setText(
                String.valueOf(partTime.getHourlyRate())
            );

            hoursWorkedField.setText(
                String.valueOf(partTime.getHoursWorked())
            );
        }

        salaryLayout.show(salaryPanel, selectedEmployeeType);

        setFormEnabled(true);
    }

    // ---------------- SAVE UPDATED EMPLOYEE ----------------

    private void updateEmployee() {

        try {

            if (selectedEmployeeType == null) {
                showMessage("Please select an employee first.");
                return;
            }

            String name = nameField.getText().trim();
            String position = positionField.getText().trim();

            if (idField.getText().trim().isEmpty()
                    || name.isEmpty()
                    || ageField.getText().trim().isEmpty()
                    || position.isEmpty()) {

                showMessage("Please complete all required fields.");
                return;
            }

            int newId = Integer.parseInt(idField.getText().trim());
            int age = Integer.parseInt(ageField.getText().trim());

            Department department =
                (Department) departmentBox.getSelectedItem();

            if (newId <= 0 || age <= 0) {
                showMessage("ID and age must be greater than zero.");
                return;
            }

            if (department == null) {
                showMessage("Please select a department.");
                return;
            }

            Employee updated;

            if ("Full-Time".equals(selectedEmployeeType)) {

                double basicSalary = Double.parseDouble(
                    basicSalaryField.getText().trim()
                );

                double bonus = Double.parseDouble(
                    bonusField.getText().trim()
                );

                if (!Double.isFinite(basicSalary)
                        || !Double.isFinite(bonus)
                        || basicSalary < 0 || bonus < 0) {

                    showMessage("Salary and bonus must be non-negative numbers.");
                    return;
                }

                updated = new FullTimeEmployee(
                    newId, name, age, department,
                    position, basicSalary, bonus
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

                    showMessage("Hourly rate and hours must be non-negative numbers.");
                    return;
                }

                updated = new PartTimeEmployee(
                    newId, name, age, department,
                    position, hourlyRate, hoursWorked
                );
            }

            boolean success =
                employeeManager.updateEmployee(originalId, updated);

            if (success) {

                JOptionPane.showMessageDialog(
                    this,
                    "Employee updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );

                searchEmployees();
                clearForm();

            } else {

                showMessage(
                    "Update failed. The ID may already belong to another employee."
                );
            }

        } catch (NumberFormatException ex) {

            showMessage("Please enter valid numeric values.");
        }
    }

    // ---------------- HELPER METHODS ----------------

    private void loadDepartments() {

        departmentBox.removeAllItems();

        for (Department department :
                employeeManager.getAllDepartments()) {

            departmentBox.addItem(department);
        }

        departmentBox.setRenderer(new DefaultListCellRenderer() {

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

    private void selectDepartment(Department selected) {

        for (int i = 0; i < departmentBox.getItemCount(); i++) {

            Department department = departmentBox.getItemAt(i);

            if (department.getDepartmentId()
                    == selected.getDepartmentId()) {

                departmentBox.setSelectedIndex(i);
                return;
            }
        }
    }

    private String getEmployeeType(Employee employee) {

        if (employee instanceof FullTimeEmployee) {
            return "Full-Time";
        }

        if (employee instanceof PartTimeEmployee) {
            return "Part-Time";
        }

        return "Unknown";
    }

    private JTextField createField() {

        JTextField field = new JTextField(12);
        field.setFont(BODY_FONT);

        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(
                new Color(216, 196, 240)
            ),
            new EmptyBorder(5, 7, 5, 7)
        ));

        return field;
    }

    private JButton createButton(String text) {

        JButton button = new JButton(text);

        button.setFont(BUTTON_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(PURPLE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private void styleTable(JTable table) {

        table.setFont(BODY_FONT);
        table.setRowHeight(28);
        table.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
        );

        table.setBackground(CARD);
        table.setForeground(SIDEBAR);

        table.setSelectionBackground(
            new Color(225, 205, 250)
        );

        table.setSelectionForeground(SIDEBAR);

        table.getTableHeader().setFont(BUTTON_FONT);
        table.getTableHeader().setBackground(PURPLE);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setReorderingAllowed(false);
    }

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

        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.4;

        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.6;

        panel.add(field, gbc);
    }

    private void setFormEnabled(boolean enabled) {

        idField.setEnabled(enabled);
        nameField.setEnabled(enabled);
        ageField.setEnabled(enabled);
        positionField.setEnabled(enabled);
        departmentBox.setEnabled(enabled);

        basicSalaryField.setEnabled(enabled);
        bonusField.setEnabled(enabled);
        hourlyRateField.setEnabled(enabled);
        hoursWorkedField.setEnabled(enabled);
    }

    private void clearForm() {

        idField.setText("");
        nameField.setText("");
        ageField.setText("");
        positionField.setText("");

        basicSalaryField.setText("");
        bonusField.setText("");
        hourlyRateField.setText("");
        hoursWorkedField.setText("");

        departmentBox.setSelectedIndex(-1);

        selectedEmployeeType = null;

        setFormEnabled(false);
        employeeTable.clearSelection();
    }

    private void showMessage(String message) {

        JOptionPane.showMessageDialog(
            this,
            message,
            "Employee Management System",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    public void refreshDepartments() {
    loadDepartments();
}
}
