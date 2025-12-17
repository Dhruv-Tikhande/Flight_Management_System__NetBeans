package flight_booking_system;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditProfileFrame extends JFrame {

    private JTextField nameField, emailField, phoneField;
    private String username;  // Assuming username is the unique identifier for a user

    public EditProfileFrame(String username) {
        this.username = username;
        
        setTitle("Edit Profile");
        setBounds(400, 200, 400, 300);
        setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 50, 100, 30);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 50, 150, 30);
        add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 100, 100, 30);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(150, 100, 150, 30);
        add(emailField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(50, 150, 100, 30);
        add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(150, 150, 150, 30);
        add(phoneField);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(150, 200, 100, 30);
        saveButton.addActionListener((ActionEvent e) -> {
            updateProfile();
        });
        add(saveButton);

        // Load the user's current details
        loadUserProfile();

        setVisible(true);
    }

    private void loadUserProfile() {
        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "SELECT fullname, email, phone_no FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nameField.setText(rs.getString("fullname"));
                emailField.setText(rs.getString("email"));
                phoneField.setText(rs.getString("phone_no"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateProfile() {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "UPDATE users SET fullname = ?, email = ?, phone_no = ? WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, username);

            int updated = stmt.executeUpdate();
            if (updated > 0) {
                JOptionPane.showMessageDialog(this, "Profile updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update profile.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new EditProfileFrame("Dhruv");  // Use logged-in user's username here
    }
}
