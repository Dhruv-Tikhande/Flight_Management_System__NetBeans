package flight_booking_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AdminPanel1 extends JFrame {

    public AdminPanel1() {
        setTitle("Admin Panel - Flight Management");
        setBounds(300, 100, 1400, 900);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Images/adminPanel1.jpg"));
        JLabel image = new JLabel(i1);
        image.setBounds(0,0,1400,900);
        add(image);
        
        JLabel text = new JLabel("Midway Airlines");
        text.setBounds(50,10,700,100);
        text.setForeground(Color.WHITE);
        text.setFont(new Font("Comic Sans MS", Font.PLAIN , 70));
        image.add(text);
        
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);
        
        JMenu details = new JMenu("Details");
        menubar.add(details);
        
        JMenuItem flightDetails = new JMenuItem("Flight Details");
        flightDetails.addActionListener((ActionEvent e) -> {
            new AdminPanel();
        });
        details.add(flightDetails);
        
        JMenuItem customerDetails = new JMenuItem("Customer Details");
        customerDetails.addActionListener((ActionEvent e) -> {
            new customerDetails();
        });
        details.add(customerDetails);
        
        JMenuItem AddFlight = new JMenuItem("Add Flights");
        AddFlight.addActionListener((ActionEvent e) -> {
            new AddFlightForm();
        });
        details.add(AddFlight);
        
        JMenuItem Logout = new JMenuItem("Logout");
        Logout.addActionListener((ActionEvent e) -> {
            // Dispose of the current frame
            dispose();

            // Close all active frames (to ensure everything is closed)
            for (Frame frame : JFrame.getFrames()) {
                frame.dispose();
            }

            // Open the Main GUI
            new MainGUI().setVisible(true);
        });
        details.add(Logout);
        
                JMenu helpMenu = new JMenu("Help");
        menubar.add(helpMenu);

        JMenuItem helpItem = new JMenuItem("Help");
        helpItem.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(this, "Contact us: +91 8369665589, \n\t+91 9860267919\n" +
                    "Email: pulkit.saini@vit.edu.in,\n\t dhruv.tikhande@vit.edu.in",
                    "Help", JOptionPane.INFORMATION_MESSAGE);
        });
        helpMenu.add(helpItem);
        setVisible(true);
    }

    public static void main(String[] args) {
        new AdminPanel1();
    }
}
