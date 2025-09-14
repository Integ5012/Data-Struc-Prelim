package prelim;

import javax.swing.*;
import java.awt.*;

public class Menu {
	public void showGUI(GmailSimulator simulator, User user) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Gmail Simulator");
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setSize(700, 500);
			frame.setLocationRelativeTo(null);

			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.setBackground(new Color(245, 248, 255));

			
            
			JPanel menuPanel = new JPanel();
			menuPanel.setLayout(new GridLayout(2, 2, 30, 30));
			menuPanel.setBackground(new Color(245, 248, 255));

			JButton sendEmailBtn = new JButton("Send Email", UIManager.getIcon("FileView.fileIcon"));
			JButton inboxBtn = new JButton("Read Inbox", UIManager.getIcon("FileView.directoryIcon"));
			JButton sentBtn = new JButton("View Sent Emails", UIManager.getIcon("FileView.hardDriveIcon"));
			JButton logoutBtn = new JButton("Logout", UIManager.getIcon("OptionPane.errorIcon"));

			JButton[] buttons = {sendEmailBtn, inboxBtn, sentBtn, logoutBtn};
			for (JButton btn : buttons) {
				UIUtils.styleButton(btn);
				btn.setFont(UIUtils.DEFAULT_FONT.deriveFont(Font.BOLD, 16f));
			}

			menuPanel.add(sendEmailBtn);
			menuPanel.add(inboxBtn);
			menuPanel.add(sentBtn);
			menuPanel.add(logoutBtn);

			panel.add(menuPanel, BorderLayout.CENTER);

			sendEmailBtn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent ev) {
					SendEmailDialog dlg = new SendEmailDialog(frame, user, simulator);
					dlg.setVisible(true);
				}
			});
			inboxBtn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent ev) {
					InboxDialog dlg = new InboxDialog(frame, user);
					dlg.setVisible(true);
				}
			});
			sentBtn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent ev) {
					SentEmailsDialog dlg = new SentEmailsDialog(frame, user);
					dlg.setVisible(true);
				}
			});
			logoutBtn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent ev) {
					frame.dispose();
					simulator.logout();
					SwingUtilities.invokeLater(() -> new LogIn().createAndShowGUI(simulator));
				}
			});

			frame.setContentPane(panel);
			frame.setVisible(true);
		});
	}
}
