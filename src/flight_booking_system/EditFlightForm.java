package flight_booking_system;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditFlightForm extends JFrame {
    private JTextField flightNoField, flightNameField, sourceField, destinationField, dateField, AtimeField, DtimeField, FpriceField, BpriceField, EpriceField, Fseats, Bseats, Eseats;
    private JButton updateButton, deleteButton, cancelButton;

    public EditFlightForm(String flightNo) {
        setTitle("Edit Flight");
        setBounds(400, 200, 850, 430);
        setLayout(null); // Using null layout for absolute positioning
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Fields for flight details
        flightNoField = new JTextField();
        flightNoField.setEditable(false); // Flight number is non-editable
        flightNameField = new JTextField();
        sourceField = new JTextField();
        destinationField = new JTextField();
        dateField = new JTextField();
        AtimeField = new JTextField();
        DtimeField = new JTextField();
        FpriceField = new JTextField();
        BpriceField = new JTextField();
        EpriceField = new JTextField();
        Fseats = new JTextField();
        Bseats = new JTextField();
        Eseats = new JTextField();

        // Fetch the current flight data and populate the fields
        loadFlightDetails(flightNo);

        // Initialize labels
        JLabel flightNoLabel = new JLabel("Flight No:");
        JLabel flightIdLabel = new JLabel("Flight ID:");
        JLabel sourceLabel = new JLabel("Source:");
        JLabel destinationLabel = new JLabel("Destination:");
        JLabel dateLabel = new JLabel("Date:");
        JLabel arrivalTimeLabel = new JLabel("Arrival Time:");
        JLabel departureTimeLabel = new JLabel("Departure Time:");
        JLabel fSeatsLabel = new JLabel("First Class Seats:");
        JLabel bSeatsLabel = new JLabel("Business Class Seats:");
        JLabel eSeatsLabel = new JLabel("Economy Class Seats:");
        JLabel fPriceLabel = new JLabel("First Class Price:");
        JLabel bPriceLabel = new JLabel("Business Class Price:");
        JLabel ePriceLabel = new JLabel("Economy Class Price:");

        // Set bounds for labels and text fields
        int labelWidth = 150;
        int fieldWidth = 200;
        int fieldHeight = 30;
        int labelHeight = 30;

        flightNoLabel.setBounds(30, 30, labelWidth, labelHeight);
        flightNoField.setBounds(200, 30, fieldWidth, fieldHeight);
        
        flightIdLabel.setBounds(30, 70, labelWidth, labelHeight);
        flightNameField.setBounds(200, 70, fieldWidth, fieldHeight);
        
        sourceLabel.setBounds(30, 110, labelWidth, labelHeight);
        sourceField.setBounds(200, 110, fieldWidth, fieldHeight);
        
        destinationLabel.setBounds(30, 150, labelWidth, labelHeight);
        destinationField.setBounds(200, 150, fieldWidth, fieldHeight);
        
        dateLabel.setBounds(30, 190, labelWidth, labelHeight);
        dateField.setBounds(200, 190, fieldWidth, fieldHeight);
        
        arrivalTimeLabel.setBounds(30, 230, labelWidth, labelHeight);
        AtimeField.setBounds(200, 230, fieldWidth, fieldHeight);
        
        departureTimeLabel.setBounds(30, 270, labelWidth, labelHeight);
        DtimeField.setBounds(200, 270, fieldWidth, fieldHeight);
        
        fSeatsLabel.setBounds(420, 30, labelWidth, labelHeight);
        Fseats.setBounds(590, 30, fieldWidth, fieldHeight);
        
        bSeatsLabel.setBounds(420, 70, labelWidth, labelHeight);
        Bseats.setBounds(590, 70, fieldWidth, fieldHeight);
        
        eSeatsLabel.setBounds(420, 110, labelWidth, labelHeight);
        Eseats.setBounds(590, 110, fieldWidth, fieldHeight);
        
        fPriceLabel.setBounds(420, 150, labelWidth, labelHeight);
        FpriceField.setBounds(590, 150, fieldWidth, fieldHeight);
        
        bPriceLabel.setBounds(420, 190, labelWidth, labelHeight);
        BpriceField.setBounds(590, 190, fieldWidth, fieldHeight);
        
        ePriceLabel.setBounds(420, 230, labelWidth, labelHeight);
        EpriceField.setBounds(590, 230, fieldWidth, fieldHeight);

        // Buttons
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        cancelButton = new JButton("Cancel");

        updateButton.setBounds(500, 330, 100, 30);
        deleteButton.setBounds(350, 330, 100, 30);
        cancelButton.setBounds(200, 330, 100, 30);

        // Add components to the frame
        add(flightNoLabel);
        add(flightNoField);
        add(flightIdLabel);
        add(flightNameField);
        add(sourceLabel);
        add(sourceField);
        add(destinationLabel);
        add(destinationField);
        add(dateLabel);
        add(dateField);
        add(arrivalTimeLabel);
        add(AtimeField);
        add(departureTimeLabel);
        add(DtimeField);
        add(fSeatsLabel);
        add(Fseats);
        add(bSeatsLabel);
        add(Bseats);
        add(eSeatsLabel);
        add(Eseats);
        add(fPriceLabel);
        add(FpriceField);
        add(bPriceLabel);
        add(BpriceField);
        add(ePriceLabel);
        add(EpriceField);
        
        add(updateButton);
        add(deleteButton);
        add(cancelButton);

        // Update flight details
        updateButton.addActionListener((ActionEvent e) -> {
            updateFlight();
        });

        // Delete flight
        deleteButton.addActionListener((ActionEvent e) -> {
            deleteFlight();
        });

        // Cancel action
        cancelButton.addActionListener((ActionEvent e) -> {
            dispose();
        });

        setVisible(true);
    }

    // Method to load flight details into the fields
    private void loadFlightDetails(String flightNo) {
        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM flights WHERE flight_no = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, flightNo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                flightNoField.setText(rs.getString("flight_no"));
                flightNameField.setText(rs.getString("flight_id"));
                sourceField.setText(rs.getString("source"));
                destinationField.setText(rs.getString("destination"));
                dateField.setText(rs.getString("date"));
                AtimeField.setText(rs.getString("arrival_time"));
                DtimeField.setText(rs.getString("departure_time"));
                Fseats.setText(String.valueOf(rs.getInt("available_seats_first")));
                Bseats.setText(String.valueOf(rs.getInt("available_seats_business")));
                Eseats.setText(String.valueOf(rs.getInt("available_seats_economy")));
                FpriceField.setText(String.valueOf(rs.getDouble("price_first")));
                BpriceField.setText(String.valueOf(rs.getDouble("price_business")));
                EpriceField.setText(String.valueOf(rs.getDouble("price_economy")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update flight details in the database
    private void updateFlight() {
        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "UPDATE flights SET flight_id = ?, source = ?, destination = ?, date = ?, arrival_time = ?, departure_time = ?, available_seats_first = ?, available_seats_business = ?, available_seats_economy = ?, price_first = ?, price_business = ?, price_economy = ? WHERE flight_no = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, flightNameField.getText());
            stmt.setString(2, sourceField.getText());
            stmt.setString(3, destinationField.getText());
            stmt.setString(4, dateField.getText());
            stmt.setString(5, AtimeField.getText());
            stmt.setString(6, DtimeField.getText());
            stmt.setInt(7, Integer.parseInt(Fseats.getText()));
            stmt.setInt(8, Integer.parseInt(Bseats.getText()));
            stmt.setInt(9, Integer.parseInt(Eseats.getText()));
            stmt.setDouble(10, Double.parseDouble(FpriceField.getText()));
            stmt.setDouble(11, Double.parseDouble(BpriceField.getText()));
            stmt.setDouble(12, Double.parseDouble(EpriceField.getText()));
            stmt.setString(13, flightNoField.getText());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Flight updated successfully!");
                dispose();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for prices and seats.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to delete flight from the database
    private void deleteFlight() {
        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "DELETE FROM flights WHERE flight_no = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, flightNoField.getText());

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Flight deleted successfully!");
                dispose();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
