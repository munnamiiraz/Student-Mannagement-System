package src.model;

public class StudentMarks {
    private String id;
    private String name;
    private String email;
    private int attendance;
    private int ct1;
    private int ct2;
    private int viva;
    private int assignment;
    private int finalExam;
    private int total;

    public StudentMarks(String id, String name,
                    int attendance, int ct1, int ct2, int viva, int assignment, int finalExam) {
    this.id = id;
    this.name = name;
    this.attendance = attendance;
    this.ct1 = ct1;
    this.ct2 = ct2;
    this.viva = viva;
    this.assignment = assignment;
    this.finalExam = finalExam;
}



    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAttendance() { return attendance; }
    public void setAttendance(int attendance) { this.attendance = attendance; }

    public int getCt1() { return ct1; }
    public void setCt1(int ct1) { this.ct1 = ct1; }

    public int getCt2() { return ct2; }
    public void setCt2(int ct2) { this.ct2 = ct2; }

    public int getViva() { return viva; }
    public void setViva(int viva) { this.viva = viva; }

    public int getAssignment() { return assignment; }
    public void setAssignment(int assignment) { this.assignment = assignment; }

    public int getFinalExam() { return finalExam; }
    public void setFinalExam(int finalExam) { this.finalExam = finalExam; }

    public int getTotal() {
        return getAssignment() + getAttendance() + getCt1() + getCt2() + getViva() + getFinalExam();
    }
    
    public String getTotalMarksCSV() {
        return attendance + "," + ct1 + "," + ct2 + "," + viva + "," + assignment + "," + finalExam;
    }

}