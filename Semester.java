import java.util.ArrayList;

public class Semester {
    public String semesterName;
    public ArrayList<String> subjectNames = new ArrayList<>();
    public ArrayList<Integer> subjectMarks = new ArrayList<>();
    public ArrayList<Integer> creditHours = new ArrayList<>();

    public Semester(String semesterName) {
        this.semesterName = semesterName;
    }
}