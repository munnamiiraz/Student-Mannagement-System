import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import src.model.*;
import utils.FileManager;
import java.awt.*;
import java.awt.event.*;
import java.util.List;


public class StudentDBFile extends JFrame implements ActionListener {
    private JTable table;
    private DefaultTableModel model;
    private JTextField fId,fFn,fSn;
    private JButton add,del,reset;
    private List<Student> students;

    public StudentDBFile() {
        students = FileManager.loadBasicStudents();
        init();loadData();
    }
    private void init() {
        setTitle("Student Management"); setSize(800,600); setLayout(new BorderLayout());
        model = new DefaultTableModel(new String[]{"ID","Name"},0);
        table = new JTable(model);
        add(new JScrollPane(table),BorderLayout.CENTER);

        JPanel p = new JPanel(new GridLayout(7,2,5,5));
        fId=new JTextField();fFn=new JTextField();fSn=new JTextField();
        add=new JButton("Add");del=new JButton("Delete");reset=new JButton("Reset");
        add.addActionListener(this);del.addActionListener(this);reset.addActionListener(this);
        p.add(new JLabel("ID:"));p.add(fId);
        p.add(new JLabel("First:"));p.add(fFn);
        p.add(new JLabel("Sur:"));p.add(fSn);
        p.add(add);p.add(del);p.add(reset);
        add(p,BorderLayout.EAST);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    private void loadData() {
        model.setRowCount(0);
        for (Student s: students) model.addRow(new Object[]{s.getId(),s.getName()});
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==add) {
            Student s = new Student(fId.getText(),fFn.getText());
            students.add(s); FileManager.saveBasicStudents(students); loadData();
        } else if (e.getSource()==del) {
            int r = table.getSelectedRow(); if (r>=0) {students.remove(r); FileManager.saveBasicStudents(students); loadData();}
        } else {
            fId.setText("");fFn.setText("");fSn.setText("");
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentDBFile().setVisible(true));
    }
}
