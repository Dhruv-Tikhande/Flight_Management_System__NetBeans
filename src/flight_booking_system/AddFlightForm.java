package flight_booking_system;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddFlightForm extends JFrame {
    private JTextField flightIdField, sourceField, destinationField, dateField, arrivalTimeField, departureTimeField, 
                       availableSeatsFirstField, availableSeatsBusinessField, availableSeatsEconomyField, 
                       priceFirstField, priceBusinessField, priceEconomyField;
    private JButton addButton, cancelButton;

    public AddFlightForm() {
        setTitle("Add New Flight");
        setBounds(400, 200, 560, 600);
        setLayout(null); // Setting layout to null for absolute positioning
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialize labels and fields for flight details
        JLabel flightIdLabel = new JLabel("Flight ID:");
        JLabel sourceLabel = new JLabel("Source:");
        JLabel destinationLabel = new JLabel("Destination:");
        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        JLabel arrivalTimeLabel = new JLabel("Arrival Time (HH:MM:SS):");
        JLabel departureTimeLabel = new JLabel("Departure Time (HH:MM:SS):");
        JLabel availableSeatsFirstLabel = new JLabel("Available Seats (First Class):");
        JLabel availableSeatsBusinessLabel = new JLabel("Available Seats (Business Class):");
        JLabel availableSeatsEconomyLabel = new JLabel("Available Seats (Economy Class):");
        JLabel priceFirstLabel = new JLabel("Price (First Class):");
        JLabel priceBusinessLabel = new JLabel("Price (Business Class):");
        JLabel priceEconomyLabel = new JLabel("Price (Economy Class):");

        flightIdField = new JTextField();
        sourceField = new JTextField();
        destinationField = new JTextField();
        dateField = new JTextField();
        arrivalTimeField = new JTextField();
        departureTimeField = new JTextField();
        availableSeatsFirstField = new JTextField();
        availableSeatsBusinessField = new JTextField();
        availableSeatsEconomyField = new JTextField();
        priceFirstField = new JTextField();
        priceBusinessField = new JTextField();
        priceEconomyField = new JTextField();

        // Buttons
        addButton = new JButton("Add Flight");
        cancelButton = new JButton("Cancel");

        // Set bounds for labels and fields
        int labelWidth = 200;
        int fieldWidth = 250;
        int height = 30;
        int xLabel = 50;
        int xField = 250;
        int gap = 40;
        int y = 30;

        flightIdLabel.setBounds(xLabel, y, labelWidth, height);
        flightIdField.setBounds(xField, y, fieldWidth, height);
        y += gap;

        sourceLabel.setBounds(xLabel, y, labelWidth, height);
        sourceField.setBounds(xField, y, fieldWidth, height);
        y += gap;

        destinationLabel.setBounds(xLabel, y, labelWidth, height);
        destinationField.setBounds(xField, y, fieldWidth, height);
        y += gap;

        dateLabel.setBounds(xLabel, y, labelWidth, height);
        dateField.setBounds(xField, y, fieldWidth, height);
        y += gap;

        arrivalTimeLabel.setBounds(xLabel, y, labelWidth, height);
        arrivalTimeField.setBounds(xField, y, fieldWidth, height);
        y += gap;

        departureTimeLabel.setBounds(xLabel, y, labelWidth, height);
        departureTimeField.setBounds(xField, y, fieldWidth, height);
        y += gap;

        availableSeatsFirstLabel.setBounds(xLabel, y, labelWidth, height);
        availableSeatsFirstField.setBounds(xField, y, fieldWidth, height);
        y += gap;

        availableSeatsBusinessLabel.setBounds(xLabel, y, labelWidth, height);
        availableSeatsBusinessField.setBounds(xField, y, fieldWidth, height);
        y += gap;

        availableSeatsEconomyLabel.setBounds(xLabel, y, labelWidth, height);
        availableSeatsEconomyField.setBounds(xField, y, fieldWidth, height);
        y += gap;

        priceFirstLabel.setBounds(xLabel, y, labelWidth, height);
        priceFirstField.setBounds(xField, y, fieldWidth, height);
        y += gap;

        priceBusinessLabel.setBounds(xLabel, y, labelWidth, height);
        priceBusinessField.setBounds(xField, y, fieldWidth, height);
        y += gap;

        priceEconomyLabel.setBounds(xLabel, y, labelWidth, height);
        priceEconomyField.setBounds(xField, y, fieldWidth, height);
        y += gap;

        // Set bounds for buttons
        addButton.setBounds(300, y, 150, height);
        cancelButton.setBounds(100, y, 150, height);

        // Add components to the form
        add(flightIdLabel);
        add(flightIdField);
        add(sourceLabel);
        add(sourceField);
        add(destinationLabel);
        add(destinationField);
        add(dateLabel);
        add(dateField);
        add(arrivalTimeLabel);
        add(arrivalTimeField);
        add(departureTimeLabel);
        add(departureTimeField);
        add(availableSeatsFirstLabel);
        add(availableSeatsFirstField);
        add(availableSeatsBusinessLabel);
        add(availableSeatsBusinessField);
        add(availableSeatsEconomyLabel);
        add(availableSeatsEconomyField);
        add(priceFirstLabel);
        add(priceFirstField);
        add(priceBusinessLabel);
        add(priceBusinessField);
        add(priceEconomyLabel);
        add(priceEconomyField);
        add(addButton);
        add(cancelButton);

        // Action listener for adding a new flight
        addButton.addActionListener((ActionEvent e) -> {
            addFlight();
        });

        // Action listener for cancel
        cancelButton.addActionListener((ActionEvent e) -> {
            dispose();
        });

        setVisible(true);
    }

    // Method to add flight to the database
    private void addFlight() {
        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "INSERT INTO flights (flight_id, source, destination, date, arrival_time, departure_time, " +
                    "available_seats_first, available_seats_business, available_seats_economy, price_first, price_business, price_economy) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, flightIdField.getText());
            stmt.setString(2, sourceField.getText());
            stmt.setString(3, destinationField.getText());
            stmt.setString(4, dateField.getText());
            stmt.setString(5, arrivalTimeField.getText());
            stmt.setString(6, departureTimeField.getText());
            stmt.setInt(7, Integer.parseInt(availableSeatsFirstField.getText()));
            stmt.setInt(8, Integer.parseInt(availableSeatsBusinessField.getText()));
            stmt.setInt(9, Integer.parseInt(availableSeatsEconomyField.getText()));
            stmt.setDouble(10, Double.parseDouble(priceFirstField.getText()));
            stmt.setDouble(11, Double.parseDouble(priceBusinessField.getText()));
            stmt.setDouble(12, Double.parseDouble(priceEconomyField.getText()));

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Flight added successfully!");
                dispose();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding flight. Please check your inputs.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
