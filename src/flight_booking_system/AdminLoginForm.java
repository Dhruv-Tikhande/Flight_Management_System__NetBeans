package flight_booking_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminLoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;
    private JLabel statusLabel;
    private JLabel i2;
    
    public AdminLoginForm() {
        setTitle("Admin Login");
        setBounds(500, 200, 600, 300);  // Adjust window size and position
        setLocation(600,350);
        setResizable(false);  // Make the window non-resizable
        setLayout(null);  // Use null layout to manually set the positions of components

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(290, 40, 100, 30);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(290, 90, 100, 30);
        
        usernameField = new JTextField();
        usernameField.setBounds(360, 40, 200, 30);
        passwordField = new JPasswordField();
        passwordField.setBounds(360, 90, 200, 30);
        
        loginButton = new JButton("Login");
        loginButton.setBounds(420, 150, 120, 30);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginAdmin();
            }
        });
        
        backButton = new JButton("Back");
        backButton.setBounds(280, 150, 120, 30);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        // Place the image on the right side of the frame
        ImageIcon image2 = new ImageIcon(ClassLoader.getSystemResource("Images/adminIcon.png"));
        i2 = new JLabel(image2);
        i2.setBounds(50,20, 200, 200);  // Adjust the size and position of the image

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

    private void loginAdmin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Admin Login Successful!");
                new AdminPanel1().setVisible(true);  // Redirect to the admin panel
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid admin credentials, try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        AdminLoginForm adminLoginForm = new AdminLoginForm();
        adminLoginForm.setVisible(true);
    }
}
