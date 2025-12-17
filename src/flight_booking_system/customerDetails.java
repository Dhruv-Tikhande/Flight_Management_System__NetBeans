package flight_booking_system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class customerDetails extends JFrame {

    private JTable userTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton searchButton;

    public customerDetails() {
        setTitle("Registered Users Details");
        setSize(800, 400);
        setLocation(600,350);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Define the table model and column names
        String[] columnNames = {"ID", "Full Name", "Phone No", "Email", "Username", "Aadhaar Card No"};
        tableModel = new DefaultTableModel(columnNames, 0);
        userTable = new JTable(tableModel);

        // Set up table in a scroll pane
        JScrollPane scrollPane = new JScrollPane(userTable);
        add(scrollPane, BorderLayout.CENTER);

        // Create a panel at the top for search functionality
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());

        JLabel searchLabel = new JLabel("Enter ID:");
        searchField = new JTextField(10);
        searchButton = new JButton("Search");

        // Add components to the search panel
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);

        // Load all user details initially into the table
        loadUserDetails(null);

        // Add action listener for search button
        searchButton.addActionListener((ActionEvent e) -> {
            String userId = searchField.getText();
            if (userId.isEmpty()) {
                // Load all user details if search box is empty
                loadUserDetails(null);
            } else {
                // Load specific user details based on ID
                loadUserDetails(userId);
            }
        });

        setVisible(true);
    }

    /**
     * Loads user details into the table.
     * If an ID is provided, it loads the details for that specific user.
     * If no ID is provided, it loads all users.
     */
    private void loadUserDetails(String userId) {
        Connection conn = DatabaseConnection.getConnection();
        try {
            String query;
            PreparedStatement stmt;

            if (userId == null) {
                // Query to get all users if no specific ID is provided
                query = "SELECT id, fullname, phone_no, email, username, aadhar_card_no FROM users";
                stmt = conn.prepareStatement(query);
            } else {
                // Query to get the user with the provided ID
                query = "SELECT id, fullname, phone_no, email, username, aadhar_card_no FROM users WHERE id = ?";
                stmt = conn.prepareStatement(query);
                stmt.setInt(1, Integer.parseInt(userId));
            }

            ResultSet rs = stmt.executeQuery();

            // Clear the table before loading new data
            tableModel.setRowCount(0);

            // Add rows to the table model
            while (rs.next()) {
                Object[] rowData = {
                        rs.getInt("id"),
                        rs.getString("fullname"),
                        rs.getString("phone_no"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("aadhar_card_no")
                };
                tableModel.addRow(rowData);
            }

            // If no rows found and a specific ID was searched for, show a message
            if (tableModel.getRowCount() == 0 && userId != null) {
                JOptionPane.showMessageDialog(this, "No user found with ID: " + userId);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error retrieving user details!");
        }
    }

    public static void main(String[] args) {
        new customerDetails();
    }
}
