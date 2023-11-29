import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BugTrackingSystemFrame extends JFrame {

    private BugDao bugDao;
    private JTable bugTable;
    private DefaultTableModel tableModel;

    private JTextField titleField;
    private JTextArea descriptionArea;
    private JComboBox<String> statusComboBox;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;

    public BugTrackingSystemFrame() {
        super("Bug Tracking System");

        bugDao = new BugDao();

        //UI
        titleField = new JTextField(30);
        descriptionArea = new JTextArea(5, 30);
        statusComboBox = new JComboBox<>(new String[]{"Open", "In Progress", "Closed"});
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");

        //set up table model
        tableModel = new DefaultTableModel();
        bugTable = new JTable(tableModel);
        tableModel.addColumn("ID");
        tableModel.addColumn("Title");
        tableModel.addColumn("Description");
        tableModel.addColumn("Status");

        //initial bug data into the table
        refreshBugTable();

        //layout
        setLayout(new BorderLayout());

        //panels for input and buttons
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Bug Details"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Title:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Description:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(new JScrollPane(descriptionArea), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Status:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        inputPanel.add(statusComboBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        inputPanel.add(addButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        inputPanel.add(updateButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 3;
        inputPanel.add(deleteButton, gbc);

        //panel for the bug table
        JScrollPane tableScrollPane = new JScrollPane(bugTable);

        //panels to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);

        //action listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBug();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBug();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBug();
            }
        });

        //table selection listener
        bugTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = bugTable.getSelectedRow();
            if (selectedRow >= 0) {
                displayBugDetails(selectedRow);
            }
        });
    }

    private void addBug() {
        String title = titleField.getText();
        String description = descriptionArea.getText();
        String status = (String) statusComboBox.getSelectedItem();

        Bug newBug = new Bug();
        newBug.setTitle(title);
        newBug.setDescription(description);
        newBug.setStatus(status);

        bugDao.addBug(newBug);

        refreshBugTable();
        clearInputFields();
    }

    private void updateBug() {
        int selectedRow = bugTable.getSelectedRow();
        if (selectedRow >= 0) {
            int bugId = (int) bugTable.getValueAt(selectedRow, 0);
            String title = titleField.getText();
            String description = descriptionArea.getText();
            String status = (String) statusComboBox.getSelectedItem();

            Bug updatedBug = new Bug();
            updatedBug.setId(bugId);
            updatedBug.setTitle(title);
            updatedBug.setDescription(description);
            updatedBug.setStatus(status);

            bugDao.updateBug(updatedBug);

            refreshBugTable();
            clearInputFields();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a bug to update.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteBug() {
        int selectedRow = bugTable.getSelectedRow();
        if (selectedRow >= 0) {
            int bugId = (int) bugTable.getValueAt(selectedRow, 0);

            bugDao.deleteBug(bugId);

            refreshBugTable();
            clearInputFields();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a bug to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayBugDetails(int row) {
        int bugId = (int) bugTable.getValueAt(row, 0);
        Bug bug = bugDao.getBugById(bugId);

        titleField.setText(bug.getTitle());
        descriptionArea.setText(bug.getDescription());
        statusComboBox.setSelectedItem(bug.getStatus());
    }

    private void refreshBugTable() {
        //clear existing data
        tableModel.setRowCount(0);

        //retrieve bugs from the database
        List<Bug> bugs = bugDao.getAllBugs();

        //populate the table with bug data
        for (Bug bug : bugs) {
            tableModel.addRow(new Object[]{bug.getId(), bug.getTitle(), bug.getDescription(), bug.getStatus()});
        }
    }

    private void clearInputFields() {
        titleField.setText("");
        descriptionArea.setText("");
        statusComboBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BugTrackingSystemFrame bugTrackingSystemFrame = new BugTrackingSystemFrame();
            bugTrackingSystemFrame.setSize(800, 600);
            bugTrackingSystemFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            bugTrackingSystemFrame.setVisible(true);
        });
    }
}


