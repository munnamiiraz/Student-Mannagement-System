package utils;

import src.model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class FileManager {
    public static final String TEACHERS_FILE = "teachers.csv";
    public static final String COURSES_FILE = "courses.csv";
    public static final String STUDENTS_FILE = "students.csv";

    // --- Teacher methods ---
    public static List<Teacher> loadTeachers() {
        List<Teacher> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(TEACHERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length>=2) list.add(new Teacher(p[0], p[1]));
            }
        } catch (IOException e) { /* ignore */ }
        return list;
    }

    public static boolean validateLogin(String user, String pass) {
        for (Teacher t: loadTeachers()) {
            if (t.getUsername().equals(user) && t.getPassword().equals(pass)) return true;
        }
        return false;
    }

    // --- Course methods ---
    public static List<Course> loadCourses(String teacher) {
        List<Course> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(COURSES_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length>=2 && p[0].equals(teacher)) list.add(new Course(p[0], p[1]));
            }
        } catch (IOException e) { /* ignore */ }
        return list;
    }

    public static void saveCourse(Course c) {
        try (FileWriter fw = new FileWriter(COURSES_FILE, true)) {
            fw.write(c.getTeacherUsername()+","+c.getCourseName()+"\n");
        } catch (IOException e) { e.printStackTrace(); }
    }

    // --- StudentMarks methods ---
    public static List<StudentMarks> loadStudentMarks(String course) {
    List<StudentMarks> list = new ArrayList<>();
        String fn = "course_" + course + "_students.csv";   // ← include “_students”
        try (BufferedReader br = new BufferedReader(new FileReader(fn))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length >= 8) {
                    // p[0]=id, p[1]=name, p[2]=att, p[3]=ct1, p[4]=ct2, p[5]=viva, p[6]=assign, p[7]=final
                    StudentMarks sm = new StudentMarks(
                        p[0],
                        p[1],
                        Integer.parseInt(p[2]),
                        Integer.parseInt(p[3]),
                        Integer.parseInt(p[4]),
                        Integer.parseInt(p[5]),
                        Integer.parseInt(p[6]),
                        Integer.parseInt(p[7])
                    );
                    list.add(sm);
                }
            }
        } catch (IOException e) { /* ignore or log */ }
        return list;
    }


    public static void saveStudentMarks(String course, StudentMarks sm) {
        String fn = "course_"+course+".csv";
        try (FileWriter fw = new FileWriter(fn, true)) {
            fw.write(sm.getId() + "," +
         sm.getName() + "," +
         sm.getTotalMarksCSV() + "\n");
        } catch (IOException e) { e.printStackTrace(); }
    }

    // --- Basic Student CRUD methods ---
    public static List<Student> loadBasicStudents() {
        List<Student> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(STUDENTS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length>=6) list.add(new Student(p[0], p[1]));
            }
        } catch (IOException e) { /* ignore */ }
        return list;
    }

    public static void saveBasicStudents(List<Student> list) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(STUDENTS_FILE))) {
            for (Student s: list) {
                bw.write(String.join(",",
                    s.getId(), s.getName())+"\n");
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    

    public static void ensureCourseStudentFile(String course) {
        String fn = "course_" + course + "_students.csv";
        File f = new File(fn);
        if (!f.exists()) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
                // write header row
                bw.write("ID,Name");
                bw.newLine();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    // Loads Student list from a custom CSV (with header)
    public static List<Student> loadBasicStudentsFromFile(String fileName) {
        List<Student> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine(); 
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length>=6) {
                    list.add(new Student(p[0],p[1]));
                }
            }
        } catch (IOException ex) {}
        return list;
    }

    public static void saveListToCSV(String fileName, List<StudentMarks> list) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Write header
            writer.write("ID,Name,Attendance,CT1,CT2,Viva,Assignment,Final Exam,Total");
            writer.newLine();

            // Write each student's data
            for (StudentMarks s : list) {
                String line = String.join(",",
                    s.getId(),
                    s.getName(),
                    String.valueOf(s.getAttendance()),
                    String.valueOf(s.getCt1()),
                    String.valueOf(s.getCt2()),
                    String.valueOf(s.getViva()),
                    String.valueOf(s.getAssignment()),
                    String.valueOf(s.getFinalExam()),
                    String.valueOf(s.getTotal())
                );
                writer.write(line);
                writer.newLine();
            }

            JOptionPane.showMessageDialog(null, "Student data saved successfully to " + fileName);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving student data: " + e.getMessage());
            e.printStackTrace();
        }
    }

}