package lab7.gui;
import javax.swing.*;
import java.awt.*;
import lab7.EmployeeManager;

public class MainDashboardFrame extends JFrame {

    // Theme colors
    private final Color BACKGROUND = Color.decode("#FFF4E5");
    private final Color SIDEBAR = Color.decode("#1034A6");
    private final Color PURPLE = Color.decode("#A666E0");
    private final Color CARD = Color.decode("#FFFCF7");
    private final Color WHITE = Color.WHITE;

    // Fonts
    private final Font TITLE_FONT = new Font("Georgia", Font.BOLD, 28);
    private final Font BODY_FONT = new Font("Trebuchet MS", Font.PLAIN, 15);
    private final Font BUTTON_FONT = new Font("Trebuchet MS", Font.BOLD, 14);

    private EmployeeManager employeeManager;

    private JPanel contentPanel;
    private CardLayout cardLayout;

    private JButton dashboardButton;
    private JButton addEmployeeButton;
    private JButton viewEmployeesButton;
    private JButton searchButton;
    private JButton deleteButton;
    private JButton departmentsButton;
    private SearchUpdatePanel searchUpdatePanel;

    public MainDashboardFrame(EmployeeManager employeeManager) {

        this.employeeManager = employeeManager;

        setTitle("Employee Management System");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(900, 600));

        // Main layout
        setLayout(new BorderLayout());

        // Create sidebar and content area
        add(createSidebar(), BorderLayout.WEST);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(BACKGROUND);

        contentPanel.add(createDashboardPanel(), "Dashboard");

        // pages (connected from their files)
    contentPanel.add(new AddEmployeePanel(employeeManager),"Add Employee");
    contentPanel.add(new ViewEmployeesFrame(employeeManager),"View Employees");  
    searchUpdatePanel = new SearchUpdatePanel(employeeManager);
    contentPanel.add(searchUpdatePanel, "Search & Update");   
    contentPanel.add(new DeleteEmployeePanel(employeeManager),"Delete Employee");        
    contentPanel.add(new DepartmentPanel(employeeManager),"Departments"); add(contentPanel, BorderLayout.CENTER);

    setVisible(true);
    }

    // Create the blue sidebar
    private JPanel createSidebar() {

        JPanel sidebar = new JPanel();
        sidebar.setBackground(SIDEBAR);
        sidebar.setPreferredSize(new Dimension(230, 700));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel appTitle = new JLabel("Employee");
        appTitle.setFont(new Font("Georgia", Font.BOLD, 23));
        appTitle.setForeground(WHITE);
        appTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel appSubtitle = new JLabel("Management System");
        appSubtitle.setFont(BODY_FONT);
        appSubtitle.setForeground(WHITE);
        appSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebar.add(Box.createVerticalStrut(35));
        sidebar.add(appTitle);
        sidebar.add(appSubtitle);
        sidebar.add(Box.createVerticalStrut(45));

        dashboardButton = createNavButton("Dashboard");
        addEmployeeButton = createNavButton("Add Employee");
        viewEmployeesButton = createNavButton("View Employees");
        searchButton = createNavButton("Search & Update");
        deleteButton = createNavButton("Delete Employee");
        departmentsButton = createNavButton("Departments");

        sidebar.add(dashboardButton);
        sidebar.add(addEmployeeButton);
        sidebar.add(viewEmployeesButton);
        sidebar.add(searchButton);
        sidebar.add(deleteButton);
        sidebar.add(departmentsButton);

        sidebar.add(Box.createVerticalGlue());

        JButton logoutButton = createNavButton("Logout");
        sidebar.add(logoutButton);

        sidebar.add(Box.createVerticalStrut(25));

        // Navigation actions
        dashboardButton.addActionListener(e -> showPage("Dashboard", dashboardButton));
        addEmployeeButton.addActionListener(e -> showPage("Add Employee", addEmployeeButton));
        viewEmployeesButton.addActionListener(e -> showPage("View Employees", viewEmployeesButton));
        searchButton.addActionListener(e -> showPage("Search & Update", searchButton));
        deleteButton.addActionListener(e -> showPage("Delete Employee", deleteButton));
        departmentsButton.addActionListener(e -> showPage("Departments", departmentsButton));

        logoutButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to log out?",
                "Logout",
                JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                new LoginFrame(employeeManager);
                dispose();
            }
        });

        return sidebar;
    }

    // Create a sidebar navigation button
    private JButton createNavButton(String text) {

        JButton button = new JButton(text);

        button.setFont(BUTTON_FONT);
        button.setForeground(WHITE);
        button.setBackground(SIDEBAR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.setMaximumSize(new Dimension(210, 45));
        button.setPreferredSize(new Dimension(210, 45));

        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    // Change the displayed page and selected button
    private void showPage(String pageName, JButton selectedButton) {

        cardLayout.show(contentPanel, pageName);
        if (pageName.equals("Search & Update")) {
    // Refresh the department dropdown
    searchUpdatePanel.refreshDepartments(); }

        JButton[] buttons = {
            dashboardButton,
            addEmployeeButton,
            viewEmployeesButton,
            searchButton,
            deleteButton,
            departmentsButton
        };

        for (JButton button : buttons) {
            button.setBackground(SIDEBAR);
        }

        selectedButton.setBackground(PURPLE);
    }

    // Main dashboard home screen
    
private JPanel createDashboardPanel() {

    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBackground(BACKGROUND);

    // Main container for the welcome card and shortcuts
    JPanel container = new JPanel();
    container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
    container.setBackground(BACKGROUND);

    // Welcome card
    JPanel welcomeCard = new JPanel();
    welcomeCard.setLayout(new BoxLayout(welcomeCard, BoxLayout.Y_AXIS));
    welcomeCard.setBackground(CARD);
    welcomeCard.setBorder(BorderFactory.createCompoundBorder(
    BorderFactory.createLineBorder(PURPLE, 2),
    BorderFactory.createEmptyBorder(25, 30, 25, 30)
));
    welcomeCard.setAlignmentX(Component.CENTER_ALIGNMENT);
    welcomeCard.setMaximumSize(new Dimension(650, 150));

    JLabel welcomeLabel = new JLabel("Welcome!");
    welcomeLabel.setFont(TITLE_FONT);
    welcomeLabel.setForeground(SIDEBAR);
    welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel subtitleLabel = new JLabel(
        "Employee Management System"
    );
    subtitleLabel.setFont(BODY_FONT);
    subtitleLabel.setForeground(Color.DARK_GRAY);
    subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel descriptionLabel = new JLabel(
        "Manage your employees and departments in one place."
    );
    descriptionLabel.setFont(BODY_FONT);
    descriptionLabel.setForeground(Color.DARK_GRAY);
    descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    welcomeCard.add(welcomeLabel);
    welcomeCard.add(Box.createVerticalStrut(10));
    welcomeCard.add(subtitleLabel);
    welcomeCard.add(Box.createVerticalStrut(15));
    welcomeCard.add(descriptionLabel);

    
// Shortcut cards
JPanel shortcuts = new JPanel();
shortcuts.setLayout(new BoxLayout(shortcuts, BoxLayout.Y_AXIS));
shortcuts.setBackground(BACKGROUND);
shortcuts.setMaximumSize(new Dimension(660, 310));
shortcuts.setAlignmentX(Component.CENTER_ALIGNMENT);

// First row: 3 cards
JPanel firstRow = new JPanel(new GridLayout(1, 3, 18, 18));
firstRow.setBackground(BACKGROUND);

firstRow.add(createShortcutButton(
    "Add Employee", "Register a new employee",
    "Add Employee", addEmployeeButton
));

firstRow.add(createShortcutButton(
    "View Employees", "Browse employee records",
    "View Employees", viewEmployeesButton
));

firstRow.add(createShortcutButton(
    "Search & Update", "Find and edit employee records",
    "Search & Update", searchButton
));

// Second row: 2 centered cards
JPanel secondRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 0));
secondRow.setBackground(BACKGROUND);

secondRow.add(createShortcutButton(
    "Delete Employee", "Remove an employee record",
    "Delete Employee", deleteButton
));

secondRow.add(createShortcutButton(
    "Departments", "Manage company departments",
    "Departments", departmentsButton
));

shortcuts.add(firstRow);
shortcuts.add(Box.createVerticalStrut(18));
shortcuts.add(secondRow);

// Add sections to main container
container.add(welcomeCard);
container.add(Box.createVerticalStrut(25));
container.add(shortcuts);


    panel.add(container);

    return panel;
}

// Creates a clickable shortcut card

private JButton createShortcutButton(
    String title,
    String description,
    String pageName,
    JButton navigationButton
) {

    JButton button = new JButton(
        "<html><div style='text-align:center;'>"
        + "<b>" + title + "</b><br><br>"
        + description
        + "</div></html>"
    );

    button.setFont(BODY_FONT);
    button.setForeground(SIDEBAR);

    // Light lavender background
    button.setBackground(new Color(248, 241, 255));

    button.setFocusPainted(false);
    button.setOpaque(true);

    // Purple border with comfortable inner spacing
    button.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(PURPLE, 2),
        BorderFactory.createEmptyBorder(15, 10, 15, 10)
    ));

    button.setPreferredSize(new Dimension(205, 145));
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));

    button.addActionListener(e ->
        showPage(pageName, navigationButton)
    );

    return button;
}


    // Temporary screen until the actual panel is implemented
    private JPanel createPlaceholderPanel(String title) {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND);

        JLabel label = new JLabel(title);
        label.setFont(TITLE_FONT);
        label.setForeground(SIDEBAR);

        panel.add(label);

        return panel;
    }
} 

