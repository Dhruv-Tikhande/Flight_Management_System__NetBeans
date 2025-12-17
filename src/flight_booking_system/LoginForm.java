package flight_booking_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;
    private JLabel statusLabel;
    private JLabel i2;
    
    public LoginForm() {
        setTitle("User Login");
        setBounds(500, 200, 600, 300);  // Adjust window size and position
        setLocation(600,350);
        setResizable(false);  // Make the window non-resizable
        setLayout(null);  // Use null layout to manually set the positions of components

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(290, 60, 100, 30);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(290, 110, 100, 30);
        
        usernameField = new JTextField();
        usernameField.setBounds(360, 60, 200, 30);
        passwordField = new JPasswordField();
        passwordField.setBounds(360, 110, 200, 30);
        
        loginButton = new JButton("Login");
        loginButton.setFocusable(false);
        loginButton.setBounds(420, 170, 120, 30);
        loginButton.addActionListener((ActionEvent e) -> {
            loginUser();
        });
        
        backButton = new JButton("Back");
        backButton.setFocusable(false);
        backButton.setBounds(280, 170, 120, 30);
        backButton.addActionListener((ActionEvent e) -> {
            dispose();
        });
        
        // Place the image on the right side of the frame
        ImageIcon image2 = new ImageIcon(ClassLoader.getSystemResource("Images/userLoginIcon.png"));
        i2 = new JLabel(image2);
        i2.setBounds(40, 40, 160, 160);  // Adjust the size and position of the image

        statusLabel = new JLabel();
        statusLabel.setBounds(290, 200, 300, 30);  // Adjust the position of status label
        statusLabel.setForeground(Color.RED);

        // Add components to the frame
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
        add(backButton);
        add(statusLabel);
        add(i2);

        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void loginUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                new UserFlightSearch(username).setVisible(true);  // Redirect to flight search
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials, try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        new LoginForm();
    }
}
