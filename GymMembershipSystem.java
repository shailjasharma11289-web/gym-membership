import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

/*
 * =====================================================
 *  GYM MEMBERSHIP SYSTEM
 *  Made by: [Your Name]
 *  Roll No: [Your Roll No]
 *  Class  : [Your Class]
 *
 *  OOP Concepts Used:
 *  1. Class & Object
 *  2. Encapsulation
 *  3. Inheritance
 *  4. Polymorphism
 *  5. Abstraction
 * =====================================================
 */

// -------------------------------------------------------
// BASE CLASS - Member
// Concept Used: Encapsulation (private fields + getters)
//               Abstraction  (getRowData() hides details)
// -------------------------------------------------------
class Member {

    private int    id;
    private String name;
    private int    age;
    private String plan;

    public Member(int id, String name, int age, String plan) {
        this.id   = id;
        this.name = name;
        this.age  = age;
        this.plan = plan;
    }

    public int    getId()   { return id;   }
    public String getName() { return name; }
    public int    getAge()  { return age;  }
    public String getPlan() { return plan; }

    // abstraction - returns data as array for table row
    public Object[] getRowData() {
        return new Object[]{ id, name, age, plan, "—" };
    }
}

// -------------------------------------------------------
// CHILD CLASS - PremiumMember
// Concept Used: Inheritance  (extends Member)
//               Polymorphism (overrides getRowData())
// -------------------------------------------------------
class PremiumMember extends Member {

    private String trainerName;

    public PremiumMember(int id, String name, int age, String trainerName) {
        super(id, name, age, "Premium");
        this.trainerName = trainerName;
    }

    public String getTrainer() { return trainerName; }

    @Override
    public Object[] getRowData() {
        return new Object[]{ getId(), getName(), getAge(), getPlan(), trainerName };
    }
}

// -------------------------------------------------------
// MAIN CLASS - Swing UI
// -------------------------------------------------------
public class GymMembershipSystem extends JFrame {

    static ArrayList<Member> memberList = new ArrayList<>();
    static int autoId = 1;

    // Table setup
    DefaultTableModel tableModel;
    JTable table;
    JLabel statusLabel;

    // Colors
    static final Color BG_DARK    = new Color(28, 28, 40);
    static final Color BG_PANEL   = new Color(40, 40, 58);
    static final Color BG_TABLE   = new Color(50, 50, 70);
    static final Color BG_HEADER  = new Color(90, 60, 160);
    static final Color CLR_ACCENT = new Color(160, 100, 255);
    static final Color CLR_GREEN  = new Color(100, 220, 130);
    static final Color CLR_RED    = new Color(255, 90, 90);
    static final Color CLR_YELLOW = new Color(255, 210, 80);
    static final Color CLR_TEXT   = new Color(230, 230, 245);
    static final Color CLR_SUBTEXT= new Color(160, 160, 185);

    // -------------------------------------------------------
    // CONSTRUCTOR - builds the full UI
    // -------------------------------------------------------
    public GymMembershipSystem() {

        // Preload sample members
        memberList.add(new Member(autoId++, "Rahul Sharma", 22, "Basic"));
        memberList.add(new PremiumMember(autoId++, "Priya Mehta", 25, "Coach Arjun"));
        memberList.add(new Member(autoId++, "Sarthak", 20, "Basic"));
        memberList.add(new Member(autoId++, "Shailja", 21, "Basic"));

        setTitle("Gym Membership System");
        setSize(860, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(BG_DARK);
        setLayout(new BorderLayout(0, 0));

        add(buildHeader(),      BorderLayout.NORTH);
        add(buildTablePanel(),  BorderLayout.CENTER);
        add(buildButtonPanel(), BorderLayout.SOUTH);

        refreshTable();
        setVisible(true);
    }

    // -------------------------------------------------------
    // HEADER BAR
    // -------------------------------------------------------
    JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_HEADER);
        header.setPreferredSize(new Dimension(860, 64));
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel title = new JLabel("  GYM MEMBERSHIP SYSTEM");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(Color.WHITE);

        JLabel sub = new JLabel("Java OOP Mini Project");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        sub.setForeground(new Color(200, 180, 255));

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        textPanel.add(title);
        textPanel.add(sub);

        statusLabel = new JLabel("4 members loaded.");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusLabel.setForeground(CLR_GREEN);
        statusLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        header.add(textPanel,  BorderLayout.WEST);
        header.add(statusLabel, BorderLayout.EAST);
        return header;
    }

    // -------------------------------------------------------
    // TABLE PANEL (center)
    // -------------------------------------------------------
    JPanel buildTablePanel() {

        String[] cols = { "ID", "Name", "Age", "Plan", "Trainer" };
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(32);
        table.setBackground(BG_TABLE);
        table.setForeground(CLR_TEXT);
        table.setGridColor(new Color(70, 70, 95));
        table.setSelectionBackground(new Color(120, 80, 200));
        table.setSelectionForeground(Color.WHITE);
        table.setShowVerticalLines(true);
        table.setFocusable(false);

        // Column widths
        int[] widths = {50, 200, 60, 100, 180};
        for (int i = 0; i < widths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }

        // Center-align ID and Age
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        // Plan column - color coded
        table.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value,
                    boolean sel, boolean focus, int row, int col) {
                super.getTableCellRendererComponent(t, value, sel, focus, row, col);
                setHorizontalAlignment(SwingConstants.CENTER);
                if ("Premium".equals(value)) {
                    setForeground(CLR_YELLOW);
                    setFont(getFont().deriveFont(Font.BOLD));
                } else {
                    setForeground(CLR_GREEN);
                    setFont(getFont().deriveFont(Font.PLAIN));
                }
                setBackground(sel ? new Color(120, 80, 200) : BG_TABLE);
                return this;
            }
        });

        // Header style
        JTableHeader th = table.getTableHeader();
        th.setBackground(new Color(65, 45, 110));
        th.setForeground(CLR_TEXT);
        th.setFont(new Font("SansSerif", Font.BOLD, 13));
        th.setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(BG_TABLE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(12, 16, 8, 16));
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    // -------------------------------------------------------
    // BUTTON PANEL (bottom)
    // -------------------------------------------------------
    JPanel buildButtonPanel() {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 14));
        panel.setBackground(BG_DARK);

        JButton btnAdd    = makeButton("Add Member",   CLR_GREEN);
        JButton btnSearch = makeButton("Search",       CLR_ACCENT);
        JButton btnRemove = makeButton("Remove",       CLR_RED);
        JButton btnClear  = makeButton("Clear Search", CLR_SUBTEXT);
        JButton btnExit   = makeButton("Exit",         new Color(180, 180, 180));

        btnAdd   .addActionListener(e -> showAddDialog());
        btnSearch.addActionListener(e -> showSearchDialog());
        btnRemove.addActionListener(e -> showRemoveDialog());
        btnClear .addActionListener(e -> { refreshTable(); setStatus("Showing all members.", CLR_SUBTEXT); });
        btnExit  .addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Exit the application?", "Confirm Exit",
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) System.exit(0);
        });

        panel.add(btnAdd);
        panel.add(btnSearch);
        panel.add(btnRemove);
        panel.add(btnClear);
        panel.add(btnExit);
        return panel;
    }

    // -------------------------------------------------------
    // DIALOG: ADD MEMBER
    // -------------------------------------------------------
    void showAddDialog() {

        JDialog dialog = createDialog("Add New Member", 380, 300);

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 12));
        form.setBackground(BG_PANEL);
        form.setBorder(BorderFactory.createEmptyBorder(20, 24, 10, 24));

        JTextField tfName    = styledField();
        JTextField tfAge     = styledField();
        JComboBox<String> cbPlan = new JComboBox<>(new String[]{"Basic", "Premium"});
        cbPlan.setBackground(BG_TABLE);
        cbPlan.setForeground(CLR_TEXT);
        cbPlan.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JTextField tfTrainer = styledField();
        JLabel lblTrainer    = styledLabel("Trainer Name:");
        tfTrainer.setEnabled(false);
        tfTrainer.setBackground(new Color(40, 40, 55));

        cbPlan.addActionListener(e -> {
            boolean isPremium = cbPlan.getSelectedItem().equals("Premium");
            tfTrainer.setEnabled(isPremium);
            tfTrainer.setBackground(isPremium ? BG_TABLE : new Color(40, 40, 55));
        });

        form.add(styledLabel("Name:"));  form.add(tfName);
        form.add(styledLabel("Age:"));   form.add(tfAge);
        form.add(styledLabel("Plan:"));  form.add(cbPlan);
        form.add(lblTrainer);            form.add(tfTrainer);

        JButton btnSave = makeButton("Save Member", CLR_GREEN);
        btnSave.addActionListener(e -> {
            String name    = tfName.getText().trim();
            String ageStr  = tfAge.getText().trim();
            String trainer = tfTrainer.getText().trim();

            if (name.isEmpty())   { showError(dialog, "Name cannot be empty.");  return; }
            if (ageStr.isEmpty()) { showError(dialog, "Age cannot be empty.");   return; }

            int age;
            try { age = Integer.parseInt(ageStr); }
            catch (NumberFormatException ex) { showError(dialog, "Age must be a number."); return; }

            if (age <= 0 || age > 120) { showError(dialog, "Enter a valid age."); return; }

            if (cbPlan.getSelectedItem().equals("Premium")) {
                if (trainer.isEmpty()) { showError(dialog, "Enter trainer name for Premium."); return; }
                memberList.add(new PremiumMember(autoId++, name, age, trainer));
            } else {
                memberList.add(new Member(autoId++, name, age, "Basic"));
            }

            refreshTable();
            setStatus("Member '" + name + "' added successfully!", CLR_GREEN);
            dialog.dispose();
        });

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnRow.setBackground(BG_PANEL);
        btnRow.add(btnSave);

        dialog.add(form,   BorderLayout.CENTER);
        dialog.add(btnRow, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    // -------------------------------------------------------
    // DIALOG: SEARCH MEMBER
    // -------------------------------------------------------
    void showSearchDialog() {

        JDialog dialog = createDialog("Search Member", 360, 160);

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 12));
        form.setBackground(BG_PANEL);
        form.setBorder(BorderFactory.createEmptyBorder(24, 24, 10, 24));

        JTextField tfKeyword = styledField();
        form.add(styledLabel("Name contains:")); form.add(tfKeyword);

        JButton btnSearch = makeButton("Search", CLR_ACCENT);
        btnSearch.addActionListener(e -> {
            String kw = tfKeyword.getText().trim().toLowerCase();
            if (kw.isEmpty()) {
                refreshTable();
                setStatus("Showing all members.", CLR_SUBTEXT);
                dialog.dispose();
                return;
            }

            tableModel.setRowCount(0);
            int count = 0;
            for (Member m : memberList) {
                if (m.getName().toLowerCase().contains(kw)) {
                    tableModel.addRow(m.getRowData());
                    count++;
                }
            }

            if (count == 0) {
                setStatus("No member found with: '" + kw + "'", CLR_RED);
            } else {
                setStatus(count + " result(s) for '" + kw + "'", CLR_ACCENT);
            }
            dialog.dispose();
        });

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnRow.setBackground(BG_PANEL);
        btnRow.add(btnSearch);

        dialog.add(form,   BorderLayout.CENTER);
        dialog.add(btnRow, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    // -------------------------------------------------------
    // DIALOG: REMOVE MEMBER
    // -------------------------------------------------------
    void showRemoveDialog() {

        if (memberList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No members to remove.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // If a row is selected in the table, remove directly
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String name = (String) tableModel.getValueAt(selectedRow, 1);
            int confirm = JOptionPane.showConfirmDialog(this,
                "Remove member '" + name + "' (ID " + id + ")?",
                "Confirm Remove", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) removeById(id, null);
            return;
        }

        // Otherwise ask for ID
        JDialog dialog = createDialog("Remove Member", 360, 160);

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 12));
        form.setBackground(BG_PANEL);
        form.setBorder(BorderFactory.createEmptyBorder(24, 24, 10, 24));

        JTextField tfId = styledField();
        form.add(styledLabel("Member ID:")); form.add(tfId);

        JButton btnRemove = makeButton("Remove", CLR_RED);
        btnRemove.addActionListener(e -> {
            try {
                int id = Integer.parseInt(tfId.getText().trim());
                removeById(id, dialog);
            } catch (NumberFormatException ex) {
                showError(dialog, "Enter a valid numeric ID.");
            }
        });

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnRow.setBackground(BG_PANEL);
        btnRow.add(btnRemove);

        dialog.add(form,   BorderLayout.CENTER);
        dialog.add(btnRow, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    void removeById(int id, JDialog dialog) {
        for (int i = 0; i < memberList.size(); i++) {
            if (memberList.get(i).getId() == id) {
                String name = memberList.get(i).getName();
                memberList.remove(i);
                refreshTable();
                setStatus("Member '" + name + "' removed.", CLR_RED);
                if (dialog != null) dialog.dispose();
                return;
            }
        }
        showError(dialog != null ? dialog : this, "No member found with ID: " + id);
    }

    // -------------------------------------------------------
    // HELPERS
    // -------------------------------------------------------
    void refreshTable() {
        tableModel.setRowCount(0);
        for (Member m : memberList) {
            tableModel.addRow(m.getRowData());
        }
        setStatus(memberList.size() + " member(s) in system.", CLR_SUBTEXT);
    }

    void setStatus(String msg, Color color) {
        statusLabel.setText(msg);
        statusLabel.setForeground(color);
    }

    JButton makeButton(String text, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setForeground(fg);
        btn.setBackground(BG_PANEL);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(fg, 1, true),
            BorderFactory.createEmptyBorder(7, 18, 7, 18)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btn.setBackground(fg.darker()); btn.setForeground(Color.WHITE); }
            @Override public void mouseExited (MouseEvent e) { btn.setBackground(BG_PANEL);    btn.setForeground(fg); }
        });
        return btn;
    }

    JTextField styledField() {
        JTextField tf = new JTextField();
        tf.setBackground(BG_TABLE);
        tf.setForeground(CLR_TEXT);
        tf.setCaretColor(CLR_TEXT);
        tf.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(90, 90, 120), 1),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        return tf;
    }

    JLabel styledLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(CLR_SUBTEXT);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
        return lbl;
    }

    JDialog createDialog(String title, int w, int h) {
        JDialog d = new JDialog(this, title, true);
        d.setSize(w, h);
        d.setLocationRelativeTo(this);
        d.setResizable(false);
        d.getContentPane().setBackground(BG_PANEL);
        d.setLayout(new BorderLayout(0, 8));
        return d;
    }

    void showError(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // -------------------------------------------------------
    // ENTRY POINT
    // -------------------------------------------------------
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); }
        catch (Exception ignored) {}

        SwingUtilities.invokeLater(GymMembershipSystem::new);
    }
}
