import javax.swing.*;
import utils.FileManager;

public class tempCodeRunnerFile {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Teacher Login");
        frame.setSize(350,200);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel uL = new JLabel("Username:"); uL.setBounds(30,30,80,25);
        JTextField uF = new JTextField(); uF.setBounds(120,30,150,25);
        JLabel pL = new JLabel("Password:"); pL.setBounds(30,70,80,25);
        JPasswordField pF = new JPasswordField(); pF.setBounds(120,70,150,25);
        JButton btn = new JButton("Login"); btn.setBounds(120,110,100,25);

        btn.addActionListener(e -> {
            String u = uF.getText().trim();
            String p = new String(pF.getPassword());
            if (FileManager.validateLogin(u,p)) {
                frame.dispose();
                CourseManagement.showFrame(u);
            } else JOptionPane.showMessageDialog(frame,"Invalid login");
        });

        frame.add(uL); frame.add(uF);
        frame.add(pL); frame.add(pF);
        frame.add(btn);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}