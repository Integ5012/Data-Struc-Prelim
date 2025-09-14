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

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBackground(Color.WHITE);
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

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
		userLabel.setFont(UIUtils.DEFAULT_FONT);
		JTextField userField = new JTextField(15);
		JLabel passLabel = new JLabel("Password:");
		passLabel.setFont(UIUtils.DEFAULT_FONT);
		JPasswordField passField = new JPasswordField(15);
		JButton loginButton = UIUtils.createPrimaryButton("Login");
		JButton registerButton = new JButton("Register");
		UIUtils.styleButton(registerButton);
		JLabel notRegisteredLabel = new JLabel("Not registered?");
		notRegisteredLabel.setFont(UIUtils.DEFAULT_FONT);
		JLabel messageLabel = new JLabel("");
		messageLabel.setFont(UIUtils.DEFAULT_FONT);
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

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
		gbc.anchor = GridBagConstraints.CENTER;
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

		registerButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent ev) {
				SwingUtilities.invokeLater(() -> Register.showRegisterGUI(frame, simulator));
			}
		});

		
		JPanel container = new JPanel(new GridBagLayout());
		container.setBackground(UIUtils.BACKGROUND);
		container.add(panel);
		frame.getContentPane().add(container);
		frame.setVisible(true);
	}
}
