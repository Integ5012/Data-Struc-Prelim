package prelim;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LogIn {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new LogIn().createAndShowGUI());
	}

	public void createAndShowGUI() {
		JFrame frame = new JFrame("Login");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setSize(500, 400);
		frame.setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

	// Logo placeholder (use 64x64 image, original size)
	ImageIcon logoIcon = new ImageIcon("src/prelim/Logo/gmail_logo_64x64.png");
	JLabel logoLabel = new JLabel(logoIcon);
	logoLabel.setHorizontalAlignment(JLabel.CENTER);
	logoLabel.setVerticalAlignment(JLabel.CENTER);
	logoLabel.setOpaque(false);
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.gridwidth = 2;
	gbc.anchor = GridBagConstraints.CENTER;
	gbc.fill = GridBagConstraints.NONE;
	panel.add(logoLabel, gbc);
	gbc.gridwidth = 1;
	gbc.anchor = GridBagConstraints.WEST;
	gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel userLabel = new JLabel("Email:");
		JTextField userField = new JTextField(15);
		JLabel passLabel = new JLabel("Password:");
		JPasswordField passField = new JPasswordField(15);
		JButton loginButton = new JButton("Login");
		loginButton.setFont(new Font("Arial", Font.BOLD, 16));
		loginButton.setPreferredSize(new Dimension(120, 35));
		JButton registerButton = new JButton("Register");
		registerButton.setFont(new Font("Arial", Font.PLAIN, 12));
		registerButton.setPreferredSize(new Dimension(90, 25));
		JLabel notRegisteredLabel = new JLabel("Not registered?");
		notRegisteredLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		JLabel messageLabel = new JLabel("");

	gbc.gridx = 0;
	gbc.gridy = 1;
	panel.add(userLabel, gbc);
	gbc.gridx = 1;
	gbc.gridy = 1;
	panel.add(userField, gbc);

	gbc.gridx = 0;
	gbc.gridy = 2;
	panel.add(passLabel, gbc);
	gbc.gridx = 1;
	gbc.gridy = 2;
	panel.add(passField, gbc);

	gbc.gridx = 0;
	gbc.gridy = 3;
	gbc.gridwidth = 2;
	panel.add(loginButton, gbc);

	gbc.gridx = 0;
	gbc.gridy = 4;
	gbc.gridwidth = 2;
	panel.add(messageLabel, gbc);

	gbc.gridx = 0;
	gbc.gridy = 5;
	gbc.gridwidth = 1;
	panel.add(notRegisteredLabel, gbc);
	gbc.gridx = 1;
	gbc.gridy = 5;
	panel.add(registerButton, gbc);

		loginButton.addActionListener(e -> {
			String email = userField.getText().trim().toLowerCase();
			String password = new String(passField.getPassword());
			UserDatabase db = new UserDatabase();
			User user = db.findUserByEmail(email);
			if (user != null && user.getPassword().equals(password)) {
				messageLabel.setText("Login successful! Welcome, " + user.getName() + ".");
				messageLabel.setForeground(Color.BLACK);
			} else {
				messageLabel.setText("Invalid email or password.");
				messageLabel.setForeground(Color.RED);
			}
		});

		registerButton.addActionListener(e -> {
			SwingUtilities.invokeLater(() -> Register.showRegisterGUI(frame));
		});

		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}
}
