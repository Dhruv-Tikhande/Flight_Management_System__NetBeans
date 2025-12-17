package flight_booking_system;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BoardingPassFrame extends JFrame {

    private String bookingId;

    public BoardingPassFrame(String bookingId) {
        this.bookingId = bookingId;
        setTitle("Boarding Pass");
        setBounds(500, 200, 820, 390);
        setLayout(null);

        // Load the boarding pass image
        ImageIcon boardingPassImage = new ImageIcon(ClassLoader.getSystemResource("Images/BoardingPass.png"));  // Path to the image
        JLabel imageLabel = new JLabel(boardingPassImage);
        imageLabel.setBounds(0, 0, 800, 400);
        add(imageLabel);

        
        Font labelFont = new Font("Arial", Font.BOLD, 15);
        Color fontColor = Color.RED;
        // Name Label
        JLabel nameLabel = new JLabel();
        nameLabel.setBounds(210, 147, 200, 20); // Adjust based on image positioning
        nameLabel.setFont(labelFont);
        nameLabel.setForeground(fontColor);
        imageLabel.add(nameLabel);

        // From Label
        JLabel fromLabel = new JLabel();
        fromLabel.setBounds(210, 192, 200, 20);
        fromLabel.setFont(labelFont);
        fromLabel.setForeground(fontColor);// Set the font size
        imageLabel.add(fromLabel);

        // To Label
        JLabel toLabel = new JLabel();
        toLabel.setBounds(320, 192, 200, 20);
        toLabel.setFont(labelFont);
        toLabel.setForeground(fontColor);// Set the font size
        imageLabel.add(toLabel);

        // Flight Label
        JLabel flightLabel = new JLabel();
        flightLabel.setBounds(210, 240, 200, 20);
        flightLabel.setFont(labelFont);
        flightLabel.setForeground(fontColor);// Set the font size
        imageLabel.add(flightLabel);

        // Date Label
        JLabel dateLabel = new JLabel();
        dateLabel.setBounds(325, 240, 200, 20);
        dateLabel.setFont(labelFont);
        dateLabel.setForeground(fontColor);// Set the font size
        imageLabel.add(dateLabel);

        // Seat Label
        JLabel seatLabel = new JLabel("1");
        seatLabel.setBounds(455, 240, 200, 20);
        seatLabel.setFont(labelFont);
        seatLabel.setForeground(fontColor);// Set the font size
        imageLabel.add(seatLabel);
        
        JLabel gateLabel = new JLabel("G2");
        gateLabel.setBounds(210, 285, 200, 20);
        gateLabel.setFont(labelFont);
        gateLabel.setForeground(fontColor);// Set the font size
        imageLabel.add(gateLabel);

        // Departure Time Label
        JLabel departureTimeLabel = new JLabel();
        departureTimeLabel.setBounds(410, 285, 200, 20);
        departureTimeLabel.setFont(labelFont);
        departureTimeLabel.setForeground(fontColor);// Set the font size
        imageLabel.add(departureTimeLabel);


        // Load the booking and flight details
        loadBoardingPassDetails(nameLabel, fromLabel, toLabel, flightLabel, dateLabel, seatLabel, departureTimeLabel);

        setVisible(true);
    }

    private void loadBoardingPassDetails(JLabel nameLabel, JLabel fromLabel, JLabel toLabel, JLabel flightLabel, JLabel dateLabel, JLabel seatLabel, JLabel departureTimeLabel) {
        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "SELECT b.fullname, b.source, b.destination, b.booking_date, f.flight_id, f.departure_time " +
                           "FROM bookings b JOIN flights f ON b.flight_id = f.flight_id WHERE b.id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, bookingId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("fullname");
                String from = rs.getString("source");
                String to = rs.getString("destination");
                String flight = rs.getString("flight_id");
                String date = rs.getString("booking_date");
                String departureTime = rs.getString("departure_time");

                // Set details in labels
                nameLabel.setText(name);
                fromLabel.setText(from);
                toLabel.setText(to);
                flightLabel.setText(flight);
                dateLabel.setText(date);
                departureTimeLabel.setText(departureTime);
                // You can also add seat details based on your logic
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        new BoardingPassFrame("7");  // Sample bookingId
//    }
}
