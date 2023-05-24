import java.util.ArrayList;

public class Group {
    public static int GroupsID = 1;
    private ArrayList<Student> students;
    private final int ID;

    public Group(int size) {
        ID = GroupsID++;
        students = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            students.add(new Student());
        }
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public int getSize() {
        return students.size();
    }

    public Student getStudent(int i) {
        return students.get(i);
    }

    public Integer getID() {
        return ID;
    }
}
