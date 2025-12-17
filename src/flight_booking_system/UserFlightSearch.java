package flight_booking_system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserFlightSearch extends JFrame {
    private JComboBox<String> sourceComboBox;
    private JComboBox<String> destinationComboBox;
    private JButton searchButton;
    private JTable flightsTable;
    private DefaultTableModel flightsModel;
    private JButton bookButton;
    private String username;

    public UserFlightSearch(String username) {
        this.username = username;
        setTitle("Search Flights");
        setBounds(400, 200, 800, 400);
        setLayout(null);
        setResizable(false);
        
        // Add menu bar
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);

        // Profile Menu
        JMenu profileMenu = new JMenu("Profile");
        menubar.add(profileMenu);

        JMenuItem editProfile = new JMenuItem("Edit Profile");
        editProfile.addActionListener((ActionEvent e) -> {
            new EditProfileFrame(username).setVisible(true);  // Pass the logged-in user's username
        });
        profileMenu.add(editProfile);

        JMenuItem previousBookings = new JMenuItem("Previously Booked Flights");
        previousBookings.addActionListener((ActionEvent e) -> {
            new PreviousBookingsFrame(username).setVisible(true);  // Pass the logged-in user's username
        });
        profileMenu.add(previousBookings);

        JMenuItem logout = new JMenuItem("Logout");
        logout.addActionListener((ActionEvent e) -> {
            dispose();  // Logout and close the window
            new MainGUI().setVisible(true);  // Show Main GUI after logout
        });
        profileMenu.add(logout);

        // Help Menu
        JMenu helpMenu = new JMenu("Help");
        menubar.add(helpMenu);

        JMenuItem helpItem = new JMenuItem("Help");
        helpItem.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(this, "Contact us: +91 8369665589, +91 9860267919\n" +
                    "Email: pulkit.saini@vit.edu.in, dhruv.tikhande@vit.edu.in",
                    "Help", JOptionPane.INFORMATION_MESSAGE);
        });
        helpMenu.add(helpItem);

        // Tickets Menu
        JMenu ticketMenu = new JMenu("Tickets");
        menubar.add(ticketMenu);

        JMenuItem boardingPass = new JMenuItem("Boarding Pass");
        ticketMenu.add(boardingPass);

        // Labels and Comboboxes for flight search
        JLabel sourceLabel = new JLabel("Source:");
        sourceLabel.setBounds(50, 30, 100, 30);
        add(sourceLabel);

        sourceComboBox = new JComboBox<>(getCities());
        sourceComboBox.setBounds(120, 30, 150, 30);
        add(sourceComboBox);

        JLabel destinationLabel = new JLabel("Destination:");
        destinationLabel.setBounds(300, 30, 100, 30);
        add(destinationLabel);

        destinationComboBox = new JComboBox<>(getCities());
        destinationComboBox.setBounds(400, 30, 150, 30);
        add(destinationComboBox);

        searchButton = new JButton("Search");
        searchButton.setBounds(600, 30, 100, 30);
        searchButton.addActionListener((ActionEvent e) -> {
            searchFlights();
        });
        add(searchButton);

        flightsModel = new DefaultTableModel(new Object[]{"Flight ID", "Source", "Destination", "Time", "Seats"}, 0);
        flightsTable = new JTable(flightsModel);
        JScrollPane scrollPane = new JScrollPane(flightsTable);
        scrollPane.setBounds(50, 80, 700, 200);
        add(scrollPane);

        bookButton = new JButton("Book Flight");
        bookButton.setBounds(600, 300, 120, 30);
        bookButton.addActionListener((ActionEvent e) -> {
            int selectedRow = flightsTable.getSelectedRow();
            if (selectedRow != -1) {
                String flightId = flightsModel.getValueAt(selectedRow, 0).toString();
                new BookingForm(flightId,username).setVisible(true);  // Pass flight ID to booking form
            } else {
                JOptionPane.showMessageDialog(UserFlightSearch.this, "Please select a flight to book.");
            }
        });
        add(bookButton);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void searchFlights() {
        String source = sourceComboBox.getSelectedItem().toString();
        String destination = destinationComboBox.getSelectedItem().toString();
        
        flightsModel.setRowCount(0); // Clear existing rows

        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM flights WHERE source = ? AND destination = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, source);
            stmt.setString(2, destination);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String flightID = rs.getString("flight_id");
                String flightSource = rs.getString("source");
                String flightDestination = rs.getString("destination");
                String flightTime = rs.getString("departure_time");
                int seats = (rs.getInt("available_seats_first") + rs.getInt("available_seats_business") + rs.getInt("available_seats_economy")) ;

                flightsModel.addRow(new Object[]{flightID, flightSource, flightDestination, flightTime, seats});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String[] getCities() {
        // Hardcoded list of cities for now, you can modify this to fetch dynamically from the database
        return new String[]{"Mumbai", "Delhi", "Bangalore", "Chennai", "Kolkata"};
    }

//    public static void main(String[] args) {
//        String username;
//        new UserFlightSearch(username);
//    }
}
