package prelim;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;

public class InboxDialog extends JDialog {
    private JTable table;
    private DefaultTableModel model;
    private User currentUser;
    private final SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public InboxDialog(JFrame parent, User user) {
        super(parent, "Inbox", true);
        this.currentUser = user;
        setupUI();
        loadInbox();
    }

    private void setupUI() {
        setSize(720, 480);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());

        // Header bar
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(248, 249, 251));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 235, 245)));
        JLabel title = new JLabel("Inbox – " + currentUser.getEmail());
        title.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        title.setFont(UIUtils.DEFAULT_FONT.deriveFont(Font.BOLD, 14f));
        header.add(title, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // Table
        model = new DefaultTableModel(new Object[]{"From", "Subject", "Date"}, 0) {
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

        // Actions footer
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(248, 249, 251));
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 235, 245)));

        JPanel leftActions = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        leftActions.setOpaque(false);
        JButton deleteBtn = UIUtils.createPrimaryButton("Delete");
        JButton refreshBtn = new JButton("Refresh");
        UIUtils.styleButton(refreshBtn);
        leftActions.add(deleteBtn);
        leftActions.add(refreshBtn);
        footer.add(leftActions, BorderLayout.WEST);

        add(footer, BorderLayout.SOUTH);

        // Listeners
        deleteBtn.addActionListener(e -> deleteSelected());
        refreshBtn.addActionListener(e -> loadInbox());
    }

    private void loadInbox() {
        try {
            model.setRowCount(0);
            prelim.LinkedList<Email> inbox = currentUser.getInbox();
            if (inbox == null || inbox.isEmpty()) {
                return;
            }
            for (int i = 0; i < inbox.size(); i++) {
                Email email = inbox.get(i);
                String from = email.getSender().getName() + " (" + email.getSender().getEmail() + ")";
                String subject = email.getSubject();
                String date = dateFmt.format(email.getDate());
                model.addRow(new Object[]{from, subject, date});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error reading inbox: " + ex.getMessage());
        }
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a message to delete.");
            return;
        }
        // Row corresponds to index in LinkedList
        boolean ok = currentUser.deleteInboxEmailAtIndex(row);
        if (ok) {
            model.removeRow(row);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete message.");
        }
    }
}