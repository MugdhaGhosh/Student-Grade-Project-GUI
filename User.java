import java.util.ArrayList;

public class User {
    public String username;
    public String password;
    public String firstName;
    public String lastName;
    public ArrayList<Semester> semesters = new ArrayList<>();

    public User(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public Semester getOrCreateSemester(String semName) {
        for (Semester s : semesters) {
            if (s.semesterName.equalsIgnoreCase(semName)) {
                return s;
            }
        }
        Semester newSem = new Semester(semName);
        semesters.add(newSem);
        return newSem;
    }
}