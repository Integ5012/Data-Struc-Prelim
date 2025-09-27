package prelim;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LogIn {
	public static void main(String[] args) {
		GmailSimulator simulator = new GmailSimulator();
		SwingUtilities.invokeLater(() -> new LogIn().createAndShowGUI(simulator));
	}

	public void createAndShowGUI(GmailSimulator simulator) {
		JFrame frame = new JFrame("Login");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(520, 420);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(UIUtils.BACKGROUND);

		// Create main panel with simple vertical layout
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.WHITE);
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// Add logo at the top
		ImageIcon logoIcon = new ImageIcon("src/prelim/Logo/gmail_logo_64x64.png");
		JLabel logoLabel = new JLabel(logoIcon);
		logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(logoLabel);

		// Add space after logo
		panel.add(Box.createRigidArea(new Dimension(0, 20)));

		// Email field
		JLabel userLabel = new JLabel("Email:");
		userLabel.setFont(UIUtils.DEFAULT_FONT);
		userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(userLabel);

		JTextField userField = new JTextField(20);
		userField.setMaximumSize(new Dimension(300, 30));
		userField.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(userField);

		// Add space between fields
		panel.add(Box.createRigidArea(new Dimension(0, 10)));

		// Password field
		JLabel passLabel = new JLabel("Password:");
		passLabel.setFont(UIUtils.DEFAULT_FONT);
		passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(passLabel);

		JPasswordField passField = new JPasswordField(20);
		passField.setMaximumSize(new Dimension(300, 30));
		passField.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(passField);

		// Add space before login button
		panel.add(Box.createRigidArea(new Dimension(0, 20)));

		// Login button
		JButton loginButton = UIUtils.createPrimaryButton("Login");
		loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(loginButton);

		// Add space before message
		panel.add(Box.createRigidArea(new Dimension(0, 10)));

		// Message label
		JLabel messageLabel = new JLabel("");
		messageLabel.setFont(UIUtils.DEFAULT_FONT);
		messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(messageLabel);

		// Add space before registration section
		panel.add(Box.createRigidArea(new Dimension(0, 20)));

		// Registration section - use a sub-panel for horizontal layout
		JPanel registerPanel = new JPanel();
		registerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
		registerPanel.setBackground(Color.WHITE);
		registerPanel.setMaximumSize(new Dimension(300, 40));

		JLabel notRegisteredLabel = new JLabel("Not registered?");
		notRegisteredLabel.setFont(UIUtils.DEFAULT_FONT);

		JButton registerButton = new JButton("Register");
		UIUtils.styleButton(registerButton);

		registerPanel.add(notRegisteredLabel);
		registerPanel.add(registerButton);
		registerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(registerPanel);

		// Login button action
		loginButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent ev) {
				String email = userField.getText().trim().toLowerCase();
				String password = new String(passField.getPassword());
				UserDatabase db = simulator.userDatabase;
				User user = db.findUserByEmail(email);
				if (user != null && user.getPassword().equals(password)) {
					messageLabel.setText("Login successful! Welcome, " + user.getName() + ".");
					messageLabel.setForeground(UIUtils.PRIMARY);
					frame.dispose();
					new Menu().showGUI(simulator, user);
				} else {
					messageLabel.setText("Invalid email or password.");
					messageLabel.setForeground(Color.RED);
				}
			}
		});

		// Register button action
		registerButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent ev) {
				SwingUtilities.invokeLater(() -> Register.showRegisterGUI(frame, simulator));
			}
		});

		// Center the panel in the frame
		JPanel container = new JPanel(new GridBagLayout());
		container.setBackground(UIUtils.BACKGROUND);
		container.add(panel);
		frame.getContentPane().add(container);
		frame.setVisible(true);
	}
}