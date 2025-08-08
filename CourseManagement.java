import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import src.model.*;
import src.model.*;
import utils.FileManager;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class CourseManagement {
    public static void showFrame(String teacher) {
        JFrame frame = new JFrame("Courses - " + teacher);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Top: Add new course
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField courseF = new JTextField(15);
        JButton addC = new JButton("Add Course");
        top.add(new JLabel("Course:"));
        top.add(courseF);
        top.add(addC);
        frame.add(top, BorderLayout.NORTH);

        // Center: list of courses with Manage button
        DefaultTableModel model = new DefaultTableModel(new String[]{"Course", "Manage"}, 0) {
            public boolean isCellEditable(int r, int c) { return c==1; }
        };
        JTable table = new JTable(model);
        table.getColumn("Manage").setCellRenderer((tbl, val, sel, foc, row, col) ->
            new JButton("Manage Students")
        );
        table.getColumn("Manage").setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            public Component getTableCellEditorComponent(JTable tbl, Object val, boolean isSel, int row, int col) {
                JButton btn = new JButton("Manage Students");
                btn.addActionListener(e -> {
                    String courseName = (String)model.getValueAt(row, 0);
                    String fn = "course_" + courseName + "_students.csv";
                    // ensure file exists
                    FileManager.ensureCourseStudentFile(courseName);
                    // open the per-course student manager
                    StudentCourseForm.showForm(courseName);
                });
                return btn;
            }
        });

        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        // load existing courses
        List<Course> cs = FileManager.loadCourses(teacher);
        cs.forEach(c -> model.addRow(new Object[]{c.getCourseName(), "Manage"}));

        // add new course
        addC.addActionListener(e -> {
            String cn = courseF.getText().trim();
            if (cn.isEmpty()) return;
            FileManager.saveCourse(new Course(teacher, cn));
            model.addRow(new Object[]{cn, "Manage"});
            FileManager.ensureCourseStudentFile(cn);
            JOptionPane.showMessageDialog(frame, "Course added: " + cn);
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
