package src.model;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import src.model.*;
import utils.FileManager;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class StudentCourseForm extends JFrame implements ActionListener {
    private DefaultTableModel tableModel;
    private JTable studentTable;
    private JTextField tfId, tfName, tfAttendance, tfCT1, tfCT2, tfViva, tfAssign, tfFinal;
    private JButton btnAdd, btnUpdate, btnDelete;
    private String courseName;
    private String fileName;
    private List<StudentMarks> students;

    public static void showForm(String courseName) {
        String fn = "course_" + courseName + "_students.csv";
        StudentCourseForm form = new StudentCourseForm(courseName, fn);
        form.setVisible(true);
    }

    private StudentCourseForm(String courseName, String fileName) {
        super("Manage Students - " + courseName);
        this.courseName = courseName;
        this.fileName = fileName;
        students = FileManager.loadStudentMarks(courseName);

        // Full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        // Table on top
        tableModel = new DefaultTableModel(new String[]{
            "ID","Name","Att/10","CT1/15","CT2/15","Viva/10","Assign/10","Final/40"}, 0);
        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(30);
        loadTableData();
        add(new JScrollPane(studentTable), BorderLayout.CENTER);

        // Input form panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Student Entry"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;

        String[] labels = {
            "Student ID","Name",
            "Attendance (Max 10)","CT1 (Max 15)","CT2 (Max 15)",
            "Viva (Max 10)","Assignment (Max 10)","Final Exam (Max 40)"
        };
        JTextField[] fields = new JTextField[9];
        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i;
            inputPanel.add(new JLabel(labels[i] + ":"), gbc);
            gbc.gridx = 1;
            fields[i] = new JTextField();
            fields[i].setFont(new Font("SansSerif", Font.PLAIN, 18));
            inputPanel.add(fields[i], gbc);
        }
        // after you build fields[] of length = labels.length = 8
        tfId         = fields[0];  // Student ID
        tfName       = fields[1];  // Name
        tfAttendance = fields[2];
        tfCT1        = fields[3];
        tfCT2        = fields[4];
        tfViva       = fields[5];
        tfAssign     = fields[6];
        tfFinal      = fields[7];

        // Buttons panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnAdd = new JButton("Add"); btnUpdate = new JButton("Update"); btnDelete = new JButton("Delete");
        btnAdd.setPreferredSize(new Dimension(120,40));
        btnUpdate.setPreferredSize(new Dimension(120,40));
        btnDelete.setPreferredSize(new Dimension(120,40));
        btnAdd.addActionListener(this); btnUpdate.addActionListener(this); btnDelete.addActionListener(this);
        btnPanel.add(btnAdd); btnPanel.add(btnUpdate); btnPanel.add(btnDelete);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(inputPanel, BorderLayout.CENTER);
        southPanel.add(btnPanel, BorderLayout.SOUTH);
        southPanel.setPreferredSize(new Dimension(400, getHeight()));

        add(southPanel, BorderLayout.EAST);
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        for (StudentMarks s : students) {
            tableModel.addRow(new Object[]{
                s.getId(), s.getName(),
                s.getAttendance(), s.getCt1(), s.getCt2(),
                s.getViva(), s.getAssignment(), s.getFinalExam()
            });
        }
    }

    @Override
public void actionPerformed(ActionEvent e) {
    int sel = studentTable.getSelectedRow();

    try {
        // parse all fields just like you do for Add/Update
        String id   = tfId.getText().trim();
        String name = tfName.getText().trim();
        int att     = Integer.parseInt(tfAttendance.getText().trim());
        int ct1     = Integer.parseInt(tfCT1.getText().trim());
        int ct2     = Integer.parseInt(tfCT2.getText().trim());
        int viva    = Integer.parseInt(tfViva.getText().trim());
        int assign  = Integer.parseInt(tfAssign.getText().trim());
        int fin     = Integer.parseInt(tfFinal.getText().trim());

        if (e.getSource() == btnAdd) {
            // your existing Add logic…
            StudentMarks s = new StudentMarks(id, name, att, ct1, ct2, viva, assign, fin);
            students.add(s);

        } else if (e.getSource() == btnUpdate) {
            if (sel < 0) {
                JOptionPane.showMessageDialog(this, "Select a student to update.");
                return;
            }
            // your existing Update logic…

        } else if (e.getSource() == btnDelete) {
            if (sel < 0) {
                JOptionPane.showMessageDialog(this, "Select a student to delete.");
                return;
            }
            // **DELETE logic:**
            students.remove(sel);
            JOptionPane.showMessageDialog(this, "Deleted student at row " + (sel+1));

        } else {
            return;  // nothing to do
        }

        // save & refresh table (for Add, Update, or Delete)
        FileManager.saveListToCSV(fileName, students);
        loadTableData();

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(
            this,
            "Please enter valid numeric values.",
            "Input Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}

}
