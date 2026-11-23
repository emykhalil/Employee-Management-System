
package lab7.gui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

import lab7.Department;
import lab7.EmployeeManager;

public class DepartmentPanel extends JPanel {

    // Theme colors
    private final Color BACKGROUND = Color.decode("#FFF4E5");
    private final Color BLUE = Color.decode("#1034A6");
    private final Color PURPLE = Color.decode("#A666E0");
    private final Color CARD = Color.decode("#FFFCF7");

    // Fonts
    private final Font TITLE_FONT =
        new Font("Georgia", Font.BOLD, 26);

    private final Font BODY_FONT =
        new Font("Trebuchet MS", Font.PLAIN, 14);

    private final Font BUTTON_FONT =
        new Font("Trebuchet MS", Font.BOLD, 14);

    private EmployeeManager employeeManager;

    private JTextField idField;
    private JTextField nameField;

    private JTable departmentTable;
    private DefaultTableModel tableModel;

    public DepartmentPanel(EmployeeManager employeeManager) {

        this.employeeManager = employeeManager;

        setLayout(new BorderLayout(15, 15));
        setBackground(BACKGROUND);
        setBorder(new EmptyBorder(20, 25, 20, 25));

        // Page title
        JLabel title = new JLabel("Department Management");
        title.setFont(TITLE_FONT);
        title.setForeground(BLUE);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        add(title, BorderLayout.NORTH);

        // Main content
        JPanel contentPanel = new JPanel(
            new GridLayout(2, 1, 0, 15)
        );

        contentPanel.setBackground(BACKGROUND);

        contentPanel.add(createAddDepartmentPanel());
        contentPanel.add(createDepartmentTablePanel());

        add(contentPanel, BorderLayout.CENTER);

        // Load existing departments
        loadDepartments();
    }

    // ---------------- ADD DEPARTMENT SECTION ----------------

    private JPanel createAddDepartmentPanel() {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(CARD);

        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PURPLE, 2),
            new EmptyBorder(15, 20, 15, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel heading = new JLabel("Add New Department");
        heading.setFont(new Font("Georgia", Font.BOLD, 19));
        heading.setForeground(BLUE);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        panel.add(heading, gbc);

        // Department ID
        JLabel idLabel = createLabel("Department ID:");

        idField = createField();

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;

        panel.add(idLabel, gbc);

        gbc.gridx = 1;
        panel.add(idField, gbc);

        // Department name
        JLabel nameLabel = createLabel("Department Name:");

        nameField = createField();

        gbc.gridy = 2;
        gbc.gridx = 0;

        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        panel.add(nameField, gbc);

        // Add button
        JButton addButton = createButton("Add Department");

        addButton.addActionListener(e -> addDepartment());

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;

        panel.add(addButton, gbc);

        return panel;
    }

    // ---------------- DEPARTMENT TABLE SECTION ----------------

    private JPanel createDepartmentTablePanel() {

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(CARD);

        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PURPLE, 2),
            new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel heading = new JLabel("Existing Departments");
        heading.setFont(new Font("Georgia", Font.BOLD, 19));
        heading.setForeground(BLUE);

        panel.add(heading, BorderLayout.NORTH);

        String[] columns = {
            "Department ID",
            "Department Name"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        departmentTable = new JTable(tableModel);

        departmentTable.setFont(BODY_FONT);
        departmentTable.setRowHeight(30);
        departmentTable.setBackground(CARD);
        departmentTable.setForeground(BLUE);

        departmentTable.setAutoResizeMode(
            JTable.AUTO_RESIZE_OFF
        );

        departmentTable.getColumnModel()
            .getColumn(0).setPreferredWidth(150);

        departmentTable.getColumnModel()
            .getColumn(1).setPreferredWidth(300);

        departmentTable.getTableHeader().setFont(BUTTON_FONT);
        departmentTable.getTableHeader().setBackground(PURPLE);
        departmentTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(
            departmentTable,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        scrollPane.setBorder(
            BorderFactory.createLineBorder(PURPLE)
        );

        panel.add(scrollPane, BorderLayout.CENTER);

        JButton refreshButton = createButton("Refresh");

        refreshButton.addActionListener(e -> loadDepartments());

        JPanel buttonPanel = new JPanel(
            new FlowLayout(FlowLayout.CENTER)
        );

        buttonPanel.setBackground(CARD);
        buttonPanel.add(refreshButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    // ---------------- ADD DEPARTMENT LOGIC ----------------

    private void addDepartment() {

        String idText = idField.getText().trim();
        String name = nameField.getText().trim();

        // Check empty fields
        if (idText.isEmpty() || name.isEmpty()) {

            showMessage(
                "Please fill in all department information.",
                JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            int id = Integer.parseInt(idText);

            // Validate ID
            if (id <= 0) {

                showMessage(
                    "Department ID must be greater than zero.",
                    JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            // Check duplicate ID
            if (!employeeManager.isDepartmentIdUnique(id)) {

                showMessage(
                    "This department ID already exists.",
                    JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            // Check duplicate name
            if (!employeeManager.isDepartmentNameUnique(name)) {

                showMessage(
                    "This department name already exists.",
                    JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            // Create department
            Department department = new Department(id, name);

            // Add through EmployeeManager
            boolean success =
                employeeManager.addDepartment(department);

            if (success) {

                showMessage(
                    "Department added successfully!",
                    JOptionPane.INFORMATION_MESSAGE
                );

                idField.setText("");
                nameField.setText("");

                loadDepartments();

            } else {

                showMessage(
                    "Could not add the department.",
                    JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException ex) {

            showMessage(
                "Department ID must be a valid integer.",
                JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // ---------------- LOAD DEPARTMENTS ----------------

    private void loadDepartments() {

        tableModel.setRowCount(0);

        List<Department> departments =
            employeeManager.getAllDepartments();

        for (Department department : departments) {

            Object[] row = {
                department.getDepartmentId(),
                department.getDepartmentName()
            };

            tableModel.addRow(row);
        }
    }

    // ---------------- HELPER METHODS ----------------

    private JLabel createLabel(String text) {

        JLabel label = new JLabel(text);
        label.setFont(BODY_FONT);
        label.setForeground(BLUE);

        return label;
    }

    private JTextField createField() {

        JTextField field = new JTextField(18);
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

        button.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
        );

        return button;
    }

    private void showMessage(String message, int messageType) {

        JOptionPane.showMessageDialog(
            this,
            message,
            "Department Management",
            messageType
        );
    }
}
