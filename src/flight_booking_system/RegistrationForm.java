package flight_booking_system;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationForm extends JFrame {
    private JTextField fullNameField, phoneField, emailField, usernameField, aadharField;
    private JPasswordField passwordField;

    public RegistrationForm() {
        setTitle("New User Registration");
        setSize(400, 380);
        setResizable(false);
        setLocation(600, 350);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null); // Use null layout to set custom positions

        // Create labels
        JLabel fullNameLabel = new JLabel("Full Name:");
        JLabel phoneLabel = new JLabel("Phone No:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel aadharLabel = new JLabel("Aadhaar Card No:");

        // Create input fields
        fullNameField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        aadharField = new JTextField(); // New field for Aadhaar number

        // Create buttons
        JButton submitButton = new JButton("Submit");
        submitButton.setFocusable(false);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFocusable(false);

        // Set bounds for labels
        fullNameLabel.setBounds(30, 30, 120, 25);
        phoneLabel.setBounds(30, 70, 120, 25);
        emailLabel.setBounds(30, 110, 120, 25);
        usernameLabel.setBounds(30, 150, 120, 25);
        passwordLabel.setBounds(30, 190, 120, 25);
        aadharLabel.setBounds(30, 230, 120, 25);

        // Set bounds for text fields
        fullNameField.setBounds(160, 30, 200, 25);
        phoneField.setBounds(160, 70, 200, 25);
        emailField.setBounds(160, 110, 200, 25);
        usernameField.setBounds(160, 150, 200, 25);
        passwordField.setBounds(160, 190, 200, 25);
        aadharField.setBounds(160, 230, 200, 25); // Set bounds for Aadhaar field

        // Set bounds for buttons
        submitButton.setBounds(80, 280, 100, 30);
        cancelButton.setBounds(220, 280, 100, 30);

        // Add components to the frame
        add(fullNameLabel);
        add(fullNameField);
        add(phoneLabel);
        add(phoneField);
        add(emailLabel);
        add(emailField);
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(aadharLabel);
        add(aadharField);
        add(submitButton);
        add(cancelButton);

        // Add action listeners for buttons
        submitButton.addActionListener((ActionEvent e) -> {
            registerUser();
        });

        cancelButton.addActionListener((ActionEvent e) -> {
            dispose();
        });

        setVisible(true);
    }

    private void registerUser() {
        String fullName = fullNameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String aadharCardNo = aadharField.getText(); // Get the Aadhaar card number input

        // Ensure Aadhaar card number is not empty and is 12 digits
        if (aadharCardNo.isEmpty() || aadharCardNo.length() != 12) {
            JOptionPane.showMessageDialog(this, "Please enter a valid 12-digit Aadhaar number.");
            return;
        }

        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "INSERT INTO users (fullname, phone_no, email, username, password, aadhar_card_no) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, fullName);
            stmt.setString(2, phone);
            stmt.setString(3, email);
            stmt.setString(4, username);
            stmt.setString(5, password);
            stmt.setString(6, aadharCardNo); // Insert Aadhaar card number into the database
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Registration Successful!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error registering user!");
        }
    }

    public static void main(String[] args) {
        new RegistrationForm();
    }
}
