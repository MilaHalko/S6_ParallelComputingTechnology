import java.util.ArrayList;
import java.util.Random;

public class addMarkThread extends Thread {
    private Random random = new Random();
    private SubjectJournal journal;
    private ArrayList<Group> groups;
    private int TeacherID;

    public addMarkThread(SubjectJournal journal, ArrayList<Group> groups, int TeacherID) {
        this.journal = journal;
        this.groups = groups;
        this.TeacherID = TeacherID;
    }

    @Override
    public void run() {
        for (Group group : groups) {
            for (int i = 0; i < group.getSize(); i++) {
                int mark = random.nextInt(100) + 1;
                journal.addMark(group.getStudent(i), mark);
                System.out.print("Teacher " + TeacherID + ":    " +
                        "Student " + group.getStudent(i).getID() + ": +" + mark + "\n");
            }
        }
    }
}
