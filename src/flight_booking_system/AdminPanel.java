package flight_booking_system;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminPanel extends JFrame {

    private JTable flightTable;
    private DefaultTableModel flightTableModel;
    private JComboBox<String> sourceComboBox;
    private JComboBox<String> destinationComboBox;

    public AdminPanel() {
        setTitle("Admin Panel - Flight Management");
        setBounds(300, 300, 1400, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize the flight table model
        flightTableModel = new DefaultTableModel();
        flightTableModel.setColumnIdentifiers(new String[]{
            "Flight No", "Flight ID", "Source", "Destination", "Date", "Arrival Time", "Departure Time",
            "Seats (First)", "Seats (Business)", "Seats (Economy)",
            "Price (First)", "Price (Business)", "Price (Economy)"
        });

        // Create the flight table
        flightTable = new JTable(flightTableModel);
        JScrollPane scrollPane = new JScrollPane(flightTable);

        // Load flight data initially
        loadFlightData();

        // Add a MouseListener to handle row selection and opening the edit form
        flightTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && flightTable.getSelectedRow() != -1) {
                    int selectedRow = flightTable.getSelectedRow();
                    String flightNo = flightTableModel.getValueAt(selectedRow, 0).toString();
                    openEditFlightForm(flightNo);
                }
            }
        });

        // Create ComboBoxes for source and destination
        String[] locations = {"Mumbai","Delhi","Bengaluru","Chennai","Kolkata","Hyderabad","Pune","Ahmedabad","Jaipur","Kochi","Guwahati","Chandigarh"}; 
        sourceComboBox = new JComboBox<>(locations);
        destinationComboBox = new JComboBox<>(locations);

        // Search button
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchFlights();
            }
        });

        // Add Flight button
        JButton addFlightButton = new JButton("Add Flight");
        addFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddFlightForm().setVisible(true);
            }
        });

        // Refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshFlightData();
            }
        });

        // Create a panel for the search controls
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Source:"));
        searchPanel.add(sourceComboBox);
        searchPanel.add(new JLabel("Destination:"));
        searchPanel.add(destinationComboBox);
        searchPanel.add(searchButton);

        // Bottom panel to hold buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(addFlightButton);
        bottomPanel.add(refreshButton); // Add the refresh button

        add(searchPanel, BorderLayout.NORTH); // Add the search panel at the top
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Method to load flight data into the table
    private void loadFlightData() {
        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM flights";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            // Clear previous data
            flightTableModel.setRowCount(0);

            while (rs.next()) {
                flightTableModel.addRow(new Object[]{
                    rs.getInt("flight_no"),
                    rs.getString("flight_id"),
                    rs.getString("source"),
                    rs.getString("destination"),
                    rs.getDate("date"),
                    rs.getTime("arrival_time"),
                    rs.getTime("departure_time"),
                    rs.getInt("available_seats_first"),
                    rs.getInt("available_seats_business"),
                    rs.getInt("available_seats_economy"),
                    rs.getBigDecimal("price_first"),
                    rs.getBigDecimal("price_business"),
                    rs.getBigDecimal("price_economy")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to search flights based on source and destination
    private void searchFlights() {
        String source = (String) sourceComboBox.getSelectedItem();
        String destination = (String) destinationComboBox.getSelectedItem();
        Connection conn = DatabaseConnection.getConnection();
        try {
            // Create query to filter results based on source and destination
            String query = "SELECT * FROM flights WHERE source = ? AND destination = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, source);
            stmt.setString(2, destination);
            ResultSet rs = stmt.executeQuery();

            // Clear previous data
            flightTableModel.setRowCount(0);

            while (rs.next()) {
                flightTableModel.addRow(new Object[]{
                    rs.getInt("flight_no"),
                    rs.getString("flight_id"),
                    rs.getString("source"),
                    rs.getString("destination"),
                    rs.getDate("date"),
                    rs.getTime("arrival_time"),
                    rs.getTime("departure_time"),
                    rs.getInt("available_seats_first"),
                    rs.getInt("available_seats_business"),
                    rs.getInt("available_seats_economy"),
                    rs.getBigDecimal("price_first"),
                    rs.getBigDecimal("price_business"),
                    rs.getBigDecimal("price_economy")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to refresh the flight data in the table
    private void refreshFlightData() {
        flightTableModel.setRowCount(0); // Clear the table data
        loadFlightData(); // Reload the data
    }

    // Method to open the Edit Flight form
    private void openEditFlightForm(String flightNo) {
        new EditFlightForm(flightNo).setVisible(true);
    }

    public static void main(String[] args) {
        new AdminPanel().setVisible(true);
    }
}
