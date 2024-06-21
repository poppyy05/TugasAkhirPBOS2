package data;

public class SessionMethod {
    private static Student loggedInStudent;

    public static void setLoggedInStudent(Student student) {
        loggedInStudent = student;
    }

    public static Student getLoggedInStudent() {
        return loggedInStudent;
    }
}