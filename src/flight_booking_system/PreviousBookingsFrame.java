package flight_booking_system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PreviousBookingsFrame extends JFrame {
    
    private JTable bookingsTable;
    private DefaultTableModel bookingsModel;
    private String username;
    private JButton showTicketButton;  // Declare the button as an instance variable
    private JButton cancelBookingButton; // Declare the button as an instance variable

    public PreviousBookingsFrame(String username) {
        this.username = username;

        setTitle("Previously Booked Flights");
        setBounds(400, 200, 800, 400);
        setLayout(null);
        
        // Initialize the table model with column names
        bookingsModel = new DefaultTableModel(new Object[]{"Booking ID", "Flight ID", "Source", "Destination", "Date"}, 0);
        bookingsTable = new JTable(bookingsModel);
        JScrollPane scrollPane = new JScrollPane(bookingsTable);
        scrollPane.setBounds(50, 50, 700, 200);
        add(scrollPane);

        // Load the bookings for the user
        loadBookings();

        // Initialize and configure the Show Ticket Button
        showTicketButton = new JButton("Show Ticket");
        showTicketButton.setBounds(250, 270, 150, 30);
        showTicketButton.setEnabled(false); // Initially disabled
        showTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = bookingsTable.getSelectedRow();
                if (selectedRow != -1) {
                    String bookingID = bookingsModel.getValueAt(selectedRow, 0).toString();
                    new BoardingPassFrame(bookingID).setVisible(true);  // Show boarding pass
                }
            }
        });
        add(showTicketButton);

        // Initialize and configure the Cancel Booking Button
        cancelBookingButton = new JButton("Cancel Booking");
        cancelBookingButton.setBounds(450, 270, 150, 30);
        cancelBookingButton.setEnabled(false); // Initially disabled
        cancelBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = bookingsTable.getSelectedRow();
                if (selectedRow != -1) {
                    String bookingID = bookingsModel.getValueAt(selectedRow, 0).toString();
                    cancelBooking(bookingID);
                }
            }
        });
        add(cancelBookingButton);

        // Add Mouse Listener to detect row selection
        bookingsTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                // Enable buttons when a row is selected
                if (bookingsTable.getSelectedRow() != -1) {
                    showTicketButton.setEnabled(true);
                    cancelBookingButton.setEnabled(true);
                }
            }
        });

        // Set close operation and make frame visible
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Ensures proper resource handling
        setVisible(true);
    }

    private void loadBookings() {
        Connection conn = DatabaseConnection.getConnection();
        try {
            // Query to get bookings for the specific username, excluding cancelled bookings
            String query = "SELECT * FROM bookings WHERE username = ? AND cancelled = 0";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            // Populate the table model with the booking data
            while (rs.next()) {
                String bookingID = rs.getString("id");
                String flightID = rs.getString("flight_id");
                String source = rs.getString("source");
                String destination = rs.getString("destination");
                String date = rs.getString("booking_date");

                bookingsModel.addRow(new Object[]{bookingID, flightID, source, destination, date});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading bookings!"); // Error message for user
        } finally {
            try {
                if (conn != null) conn.close(); // Ensure connection is closed
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void cancelBooking(String bookingID) {
        Connection conn = DatabaseConnection.getConnection();
        try {
            // Update the booking to set cancelled = 1
            String query = "UPDATE bookings SET cancelled = 1 WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, bookingID);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Booking cancelled successfully!");
                // Reload bookings to reflect cancellation
                bookingsModel.setRowCount(0); // Clear current bookings
                loadBookings(); // Reload bookings without the cancelled one
            } else {
                JOptionPane.showMessageDialog(this, "Error cancelling booking!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error cancelling booking!"); // Error message for user
        } finally {
            try {
                if (conn != null) conn.close(); // Ensure connection is closed
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
