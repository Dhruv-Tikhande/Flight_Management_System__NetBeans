package flight_booking_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainGUI extends JFrame {
    public MainGUI() {
        setTitle("Midway Airlines");
        setSize(1530,720);
        setLocation(200,200);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Images/file.jpg"));
        JLabel image = new JLabel(i1);
        setBounds(0,0,1536,720);
        add(image);
        
        JLabel text = new JLabel("Midway Airlines");
        text.setBounds(50,500,700,100);
        text.setForeground(Color.WHITE);
        text.setFont(new Font("Comic Sans MS", Font.PLAIN , 70));
        image.add(text);
        
        JButton newUser = new JButton("New User");
        newUser.setFocusable(false);
        newUser.setFont(new Font("Comic Sans MS", Font.PLAIN , 25));
        newUser.setBounds(1100, 575, 150, 50);
        image.add(newUser);
        
        JButton login = new JButton("Login");
        login.setFocusable(false);
        login.setFont(new Font("Comic Sans MS", Font.PLAIN , 25));
        login.setBounds(1300, 575, 150, 50);
        image.add(login);
        
        JButton admin = new JButton("Admin");
        admin.setFocusable(false);
        admin.setFont(new Font("Comic Sans MS", Font.PLAIN , 25));
        admin.setBounds(1300, 75, 150, 50);
        image.add(admin);
        
        newUser.addActionListener((ActionEvent e) -> {
            new RegistrationForm().setVisible(true);
        });

        login.addActionListener((ActionEvent e) -> {
            new LoginForm().setVisible(true); 
        });

        admin.addActionListener((ActionEvent e) -> {
            new AdminLoginForm().setVisible(true); 
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainGUI();
    }
}
