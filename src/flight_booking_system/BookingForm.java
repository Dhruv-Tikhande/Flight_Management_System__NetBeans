package flight_booking_system;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;

public class BookingForm extends JFrame {
    private JTextField fullNameField, phoneField, emailField;
    private JComboBox<String> classComboBox;
    private JLabel availableSeatsLabel;
    private JButton bookButton;
    private String flightId;
    private String username;

    public BookingForm(String flightId, String username) {
        this.flightId = flightId;
        this.username = username;
        setTitle("Flight Booking Form");
        setBounds(400, 200, 500, 400);
        setLayout(null);
        setResizable(false);

        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setBounds(50, 30, 100, 30);
        add(nameLabel);
        fullNameField = new JTextField();
        fullNameField.setBounds(150, 30, 200, 30);
        add(fullNameField);

        JLabel phoneLabel = new JLabel("Phone No.:");
        phoneLabel.setBounds(50, 80, 100, 30);
        add(phoneLabel);
        phoneField = new JTextField();
        phoneField.setBounds(150, 80, 200, 30);
        add(phoneField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 130, 100, 30);
        add(emailLabel);
        emailField = new JTextField();
        emailField.setBounds(150, 130, 200, 30);
        add(emailField);

        JLabel classLabel = new JLabel("Class:");
        classLabel.setBounds(50, 180, 100, 30);
        add(classLabel);
        classComboBox = new JComboBox<>(new String[]{"First Class", "Business Class", "Economy Class"});
        classComboBox.setBounds(150, 180, 200, 30);
        classComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAvailableSeats();
            }
        });
        add(classComboBox);

        JLabel seatsLabel = new JLabel("Available Seats:");
        seatsLabel.setBounds(50, 230, 100, 30);
        add(seatsLabel);
        availableSeatsLabel = new JLabel("Loading...");
        availableSeatsLabel.setBounds(150, 230, 100, 30);
        add(availableSeatsLabel);

        bookButton = new JButton("Book Flight");
        bookButton.setBounds(150, 280, 120, 30);
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookFlight();
            }
        });
        add(bookButton);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        updateAvailableSeats();  // Load available seats on form open
    }

    private void updateAvailableSeats() {
        String selectedClass = classComboBox.getSelectedItem().toString();
        Connection conn = DatabaseConnection.getConnection();

        try {
            String query = "SELECT available_seats_first, available_seats_business, available_seats_economy FROM flights WHERE flight_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, flightId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int availableSeats;
                availableSeats = switch (selectedClass) {
                    case "First Class" -> rs.getInt("available_seats_first");
                    case "Business Class" -> rs.getInt("available_seats_business");
                    case "Economy Class" -> rs.getInt("available_seats_economy");
                    default -> 0;
                };
                availableSeatsLabel.setText(String.valueOf(availableSeats));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void bookFlight() {
        String fullName = fullNameField.getText();
        String phoneNo = phoneField.getText();
        String email = emailField.getText();
        String selectedClass = classComboBox.getSelectedItem().toString();
        int bookedSeats = 1; // You can add an option for user to select seats

        if (fullName.isEmpty() || phoneNo.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        Connection conn = DatabaseConnection.getConnection();

        try {
            // Check availability of seats again before booking
            String query = "SELECT source, destination, available_seats_first, available_seats_business, available_seats_economy, price_first, price_business, price_economy FROM flights WHERE flight_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, flightId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int availableSeats;
                String source = rs.getString("source");
                String destination = rs.getString("destination");
                double totalPrice = 0.0;
                String updateQuery = "";
                String seatColumn = "";
                String priceColumn = "";

                switch (selectedClass) {
                    case "First Class":
                        availableSeats = rs.getInt("available_seats_first");
                        if (availableSeats >= bookedSeats) {
                            updateQuery = "UPDATE flights SET available_seats_first = available_seats_first - ? WHERE flight_id = ?";
                            seatColumn = "seats_booked_first";
                            priceColumn = "price_first";
                            totalPrice = bookedSeats * rs.getDouble("price_first");
                        }
                        break;
                    case "Business Class":
                        availableSeats = rs.getInt("available_seats_business");
                        if (availableSeats >= bookedSeats) {
                            updateQuery = "UPDATE flights SET available_seats_business = available_seats_business - ? WHERE flight_id = ?";
                            seatColumn = "seats_booked_business";
                            priceColumn = "price_business";
                            totalPrice = bookedSeats * rs.getDouble("price_business");
                        }
                        break;
                    case "Economy Class":
                        availableSeats = rs.getInt("available_seats_economy");
                        if (availableSeats >= bookedSeats) {
                            updateQuery = "UPDATE flights SET available_seats_economy = available_seats_economy - ? WHERE flight_id = ?";
                            seatColumn = "seats_booked_economy";
                            priceColumn = "price_economy";
                            totalPrice = bookedSeats * rs.getDouble("price_economy");
                        }
                        break;
                    default:
                        availableSeats = 0;
                }

                if (!updateQuery.isEmpty()) {
                    // Proceed to book flight
                    PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                    updateStmt.setInt(1, bookedSeats); // Deduct booked seats
                    updateStmt.setString(2, flightId);
                    updateStmt.executeUpdate();

                    // Insert booking information into the bookings table
                    String bookingQuery = "INSERT INTO bookings (fullname, phone_no, email, flight_id, " + seatColumn + ", total_amount, username, source, destination, booking_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement bookingStmt = conn.prepareStatement(bookingQuery);
                    bookingStmt.setString(1, fullName);
                    bookingStmt.setString(2, phoneNo);
                    bookingStmt.setString(3, email);
                    bookingStmt.setString(4, flightId);
                    bookingStmt.setInt(5, bookedSeats);  // Set seats booked in the selected class
                    bookingStmt.setDouble(6, totalPrice);
                    bookingStmt.setString(7, username);
                    bookingStmt.setString(8, source);
                    bookingStmt.setString(9, destination);

                    // Get the current date and set it as the booking date
                    LocalDate currentDate = LocalDate.now();  // Capture the current date
                    bookingStmt.setDate(10, Date.valueOf(currentDate));  // Set the booking date

                    bookingStmt.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Booking Successful!");
                    dispose();  // Close the form after successful booking
                } else {
                    JOptionPane.showMessageDialog(this, "No seats available in selected class.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


//    public static void main(String[] args) {
//        new BookingForm("FL001");  // Example flight ID, replace with actual flight ID when invoked
//    }
}
