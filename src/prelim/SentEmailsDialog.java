package prelim;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;

public class SentEmailsDialog extends JDialog {
    private JTable table;
    private DefaultTableModel model;
    private User currentUser;
    private final SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public SentEmailsDialog(JFrame parent, User user) {
        super(parent, "Sent", true);
        this.currentUser = user;
        setupUI();
        loadSentEmails();
    }

    private void setupUI() {
        setSize(720, 480);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());

        // Header bar
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(248, 249, 251));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 235, 245)));
        JLabel title = new JLabel("Sent – " + currentUser.getEmail());
        title.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        title.setFont(UIUtils.DEFAULT_FONT.deriveFont(Font.BOLD, 14f));
        header.add(title, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // Table
        model = new DefaultTableModel(new Object[]{"To", "Subject", "Date"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(28);
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setFont(UIUtils.DEFAULT_FONT);
        table.setFont(UIUtils.DEFAULT_FONT);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        add(scroll, BorderLayout.CENTER);

        // Footer actions
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(248, 249, 251));
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 235, 245)));

        JPanel leftActions = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        leftActions.setOpaque(false);
        JButton refreshBtn = new JButton("Refresh");
        UIUtils.styleButton(refreshBtn);
        leftActions.add(refreshBtn);
        footer.add(leftActions, BorderLayout.WEST);

        add(footer, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> loadSentEmails());
    }

    private void loadSentEmails() {
        try {
            model.setRowCount(0);
            prelim.LinkedList<Email> sent = currentUser.getSent();
            if (sent == null || sent.isEmpty()) {
                return;
            }
            for (int i = 0; i < sent.size(); i++) {
                Email email = sent.get(i);
                String to = email.getRecipient().getName() + " (" + email.getRecipient().getEmail() + ")";
                String subject = email.getSubject();
                String date = dateFmt.format(email.getDate());
                model.addRow(new Object[]{to, subject, date});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error viewing sent emails: " + ex.getMessage());
        }
    }
}