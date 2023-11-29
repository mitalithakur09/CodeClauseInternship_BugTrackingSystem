import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    private UserDao userDao;

    public LoginForm() {
        super("Bug Tracking System - Login");

        userDao = new UserDao();

        //UI 
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        loginButton = new JButton("Login");

        //layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(loginButton, gbc);

        //action listener
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticateUser();
            }
        });
    }

    private void authenticateUser() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();
        String passwordStr = new String(password);

        if (userDao.validateUser(username, passwordStr)) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            openBugTrackingSystem();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        usernameField.setText("");
        passwordField.setText("");
    }

    private void openBugTrackingSystem() {
        //close current login form
        this.dispose();

        //open  bug tracking system frame
        BugTrackingSystemFrame bugTrackingSystemFrame = new BugTrackingSystemFrame();
        bugTrackingSystemFrame.setSize(800, 600);
        bugTrackingSystemFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bugTrackingSystemFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginForm loginForm = new LoginForm();
            loginForm.setSize(300, 150);
            loginForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginForm.setVisible(true);
        });
    }
}

