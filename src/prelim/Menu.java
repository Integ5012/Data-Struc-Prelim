package prelim;

import javax.swing.*;
import java.awt.*;

public class Menu {
	public void showGUI(GmailSimulator simulator, User user) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Gmail Simulator");
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setSize(960, 600);
			frame.setLocationRelativeTo(null);

			// Root container
			JPanel root = new JPanel(new BorderLayout());
			root.setBackground(new Color(245, 248, 255));

			// Top App Bar (Gmail-like)
			JPanel appBar = new JPanel(new BorderLayout());
			appBar.setBackground(Color.WHITE);
			appBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 235, 245)));

			JPanel leftCluster = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
			leftCluster.setOpaque(false);
			JLabel logo = new JLabel(new ImageIcon("src/prelim/Logo/gmail_logo_64x64.png"));
			JLabel brand = new JLabel("Gmail Simulator");
			brand.setFont(UIUtils.HEADER_FONT);
			leftCluster.add(logo);
			leftCluster.add(brand);
			appBar.add(leftCluster, BorderLayout.WEST);

			// Center welcome label instead of search box
			JLabel welcome = new JLabel("Welcome — " + user.getName(), SwingConstants.CENTER);
			welcome.setFont(UIUtils.DEFAULT_FONT.deriveFont(Font.BOLD, 14f));
			appBar.add(welcome, BorderLayout.CENTER);

			JLabel userLabel = new JLabel(user.getEmail());
			userLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 14));
			appBar.add(userLabel, BorderLayout.EAST);

			root.add(appBar, BorderLayout.NORTH);

			// Left Navigation (vertical buttons)
			JPanel nav = new JPanel();
			nav.setLayout(new BoxLayout(nav, BoxLayout.Y_AXIS));
			nav.setBackground(Color.WHITE);
			nav.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(230, 235, 245)));
			nav.setPreferredSize(new Dimension(220, 0));

			JButton composeBtn = UIUtils.createPrimaryButton("Compose");
			composeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
			composeBtn.setMaximumSize(new Dimension(180, 36));

			JButton inboxBtn = new JButton("Inbox", UIManager.getIcon("FileView.directoryIcon"));
			JButton sentBtn = new JButton("Sent", UIManager.getIcon("FileView.hardDriveIcon"));
			JButton logoutBtn = new JButton("Logout", UIManager.getIcon("OptionPane.errorIcon"));
			JButton[] navBtns = {inboxBtn, sentBtn, logoutBtn};
			for (JButton b : navBtns) {
				UIUtils.styleButton(b);
				b.setAlignmentX(Component.CENTER_ALIGNMENT);
				b.setHorizontalAlignment(SwingConstants.LEFT);
				b.setIconTextGap(10);
				b.setMaximumSize(new Dimension(180, 36));
			}

			nav.add(Box.createRigidArea(new Dimension(0, 14))); 
			nav.add(composeBtn);
			nav.add(Box.createRigidArea(new Dimension(0, 18)));
			nav.add(inboxBtn);
			nav.add(Box.createRigidArea(new Dimension(0, 8)));
			nav.add(sentBtn);
			nav.add(Box.createRigidArea(new Dimension(0, 8)));
			nav.add(logoutBtn);
			nav.add(Box.createVerticalGlue());

			root.add(nav, BorderLayout.WEST);

			// Main content with status line at bottom
			JPanel content = new JPanel(new BorderLayout());
			content.setBackground(new Color(250, 252, 255));
			JLabel placeholder = new JLabel("Select an action from the left", SwingConstants.CENTER);
			placeholder.setFont(UIUtils.DEFAULT_FONT.deriveFont(Font.PLAIN, 16f));
			placeholder.setForeground(new Color(90, 104, 130));
			content.add(placeholder, BorderLayout.CENTER);

			JTextField statusField = new JTextField();
			statusField.setEditable(false);
			statusField.setBackground(Color.WHITE);
			statusField.setForeground(new Color(30, 60, 90));
			statusField.setFont(UIUtils.DEFAULT_FONT);
			statusField.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
			content.add(statusField, BorderLayout.SOUTH);

			root.add(content, BorderLayout.CENTER);

			// Actions
			composeBtn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent ev) {
					SendEmailDialog dlg = new SendEmailDialog(frame, user, simulator, new SendEmailDialog.StatusListener() {
						public void onStatus(String message) {
							statusField.setText(message);
						}
					});
					dlg.setVisible(true);
				}
			});
			inboxBtn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent ev) {
					InboxDialog dlg = new InboxDialog(frame, user);
					dlg.setVisible(true);
					statusField.setText("Inbox opened (" + user.getInbox().size() + ")");
				}
			});
			sentBtn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent ev) {
					SentEmailsDialog dlg = new SentEmailsDialog(frame, user);
					dlg.setVisible(true);
					statusField.setText("Sent opened (" + user.getSent().size() + ")");
				}
			});
			logoutBtn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent ev) {
					frame.dispose();
					simulator.logout();
				}
			});

			frame.setContentPane(root);
			frame.setVisible(true);
		});
	}
}
