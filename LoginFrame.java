
package lab7.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

import lab7.EmployeeManager;

public class LoginFrame extends JFrame {

    // alwan el hanestkhdmha
    private final Color BACKGROUND = Color.decode("#FFF4E5");
    private final Color CARD = Color.decode("#FFFCF7");
    private final Color TITLE = Color.decode("#1034A6");
    private final Color BUTTON = Color.decode("#A666E0");
    private final Color LAVENDER = Color.decode("#D8C4F0");
    private final Color TEXT = Color.decode("#444444");

    // fonts used
    private final Font TITLE_FONT =
            new Font("Georgia", Font.BOLD, 34);

    private final Font BODY_FONT =
            new Font("Trebuchet MS", Font.PLAIN, 15);

    private final Font LABEL_FONT =
            new Font("Trebuchet MS", Font.BOLD, 14);

    private final Font BUTTON_FONT =
            new Font("Trebuchet MS", Font.BOLD, 16);

    // el inputs
    private JTextField usernameField;
    private JPasswordField passwordField;

    // buttons
    private JButton loginButton;
    private JButton exitButton;

    // from backend
    private EmployeeManager employeeManager;

    // constructor
    public LoginFrame(EmployeeManager employeeManager) {

        this.employeeManager = employeeManager;

        // window display
        setTitle("Employee Management System");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // the main background
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(BACKGROUND);

        // goz2 el login 
        JPanel loginCard = new JPanel();

        loginCard.setLayout(
                new BoxLayout(loginCard, BoxLayout.Y_AXIS)
        );

        loginCard.setBackground(CARD);
        loginCard.setPreferredSize(new Dimension(500, 450));

        loginCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(LAVENDER, 2),
                        BorderFactory.createEmptyBorder(30, 45, 30, 45)
                )
        );

        // the welcome title
        JLabel welcomeLabel = new JLabel("Welcome");

        welcomeLabel.setFont(TITLE_FONT);
        welcomeLabel.setForeground(TITLE);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // subtitle
        JLabel subtitleLabel =
                new JLabel("Employee Management System");

        subtitleLabel.setFont(BODY_FONT);
        subtitleLabel.setForeground(TEXT);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // header where title + subtitle join

        loginCard.add(welcomeLabel);
        loginCard.add(Box.createVerticalStrut(8));

        loginCard.add(subtitleLabel);
        loginCard.add(Box.createVerticalStrut(35));

        // Username label
        JLabel usernameLabel = new JLabel("Username");
        styleLabel(usernameLabel);

        loginCard.add(usernameLabel);
        loginCard.add(Box.createVerticalStrut(7));

        // Username field
        usernameField = new JTextField();
        styleTextField(usernameField);

        loginCard.add(usernameField);
        loginCard.add(Box.createVerticalStrut(20));

        // Password label
        JLabel passwordLabel = new JLabel("Password");
        styleLabel(passwordLabel);

        loginCard.add(passwordLabel);
        loginCard.add(Box.createVerticalStrut(7));

        // Password field
        passwordField = new JPasswordField();
        styleTextField(passwordField);

        loginCard.add(passwordField);
        loginCard.add(Box.createVerticalStrut(30));

        // Login button
        loginButton = new JButton("Login");
        styleLoginButton(loginButton);

        loginCard.add(loginButton);
        loginCard.add(Box.createVerticalStrut(10));

        // Exit button
        exitButton = new JButton("Exit");
        styleExitButton(exitButton);

        loginCard.add(exitButton);

        // Center login card in window
        mainPanel.add(loginCard);

        add(mainPanel);

        // Button actions
        loginButton.addActionListener(e -> handleLogin());

        exitButton.addActionListener(e -> System.exit(0));

        // Display window
        setVisible(true);
    }

    // Style labels
    private void styleLabel(JLabel label) {

        label.setFont(LABEL_FONT);
        label.setForeground(TEXT);

        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setHorizontalAlignment(SwingConstants.CENTER);
    }

    // Style text fields
    private void styleTextField(JTextField field) {

        field.setFont(BODY_FONT);
        field.setBackground(Color.WHITE);
        field.setForeground(TEXT);

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(LAVENDER, 2),
                        BorderFactory.createEmptyBorder(8, 10, 8, 10)
                )
        );

        // Fixed dimensions for both fields
        Dimension fieldSize = new Dimension(360, 42);

        field.setPreferredSize(fieldSize);
        field.setMinimumSize(fieldSize);
        field.setMaximumSize(fieldSize);

        field.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    // Style login button
    private void styleLoginButton(JButton button) {

        button.setFont(BUTTON_FONT);
        button.setBackground(BUTTON);
        button.setForeground(Color.WHITE);

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        button.setMaximumSize(
                new Dimension(360, 45)
        );

        button.setPreferredSize(
                new Dimension(360, 45)
        );

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    // Style exit button
    private void styleExitButton(JButton button) {

        button.setFont(BODY_FONT);
        button.setBackground(CARD);
        button.setForeground(BUTTON);

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createLineBorder(LAVENDER, 1)
        );

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        button.setMaximumSize(
                new Dimension(360, 35)
        );

        button.setPreferredSize(
                new Dimension(360, 35)
        );

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    // Handle login using EmployeeManager
    private void handleLogin() {

        String username = usernameField.getText().trim();

        String password =
                new String(passwordField.getPassword());

        boolean isAuthenticated =
                employeeManager.authenticate(username, password);

        if (isAuthenticated) {

            new MainDashboardFrame(employeeManager);
            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Incorrect username or password.",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE
            );

            passwordField.setText("");
        }
    }
}
