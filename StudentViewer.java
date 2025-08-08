import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;
import src.model.*;
import utils.FileManager;

public class StudentViewer {
    public static void showViewer(String courseFilePath) {
        JFrame frame = new JFrame("Student Viewer - " + new File(courseFilePath).getName());
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        List<StudentMarks> students = FileManager.loadStudentMarks(courseFilePath);
        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No data found.");
            frame.dispose();
            return;
        }

        DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID","Name", "Attendance", "CT1", "CT2", "Viva", "Assignment", "Final", "Total"},
            0
        );
        for (StudentMarks s : students) {
            model.addRow(new Object[]{
                s.getId(), s.getName(),
                s.getAttendance(), s.getCt1(), s.getCt2(), s.getViva(), s.getAssignment(), s.getFinalExam(),
                s.getTotal()
            });
        }

        JTable table = new JTable(model);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // For testing: prompt file chooser
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            showViewer(chooser.getSelectedFile().getAbsolutePath());
        }
    }
}
