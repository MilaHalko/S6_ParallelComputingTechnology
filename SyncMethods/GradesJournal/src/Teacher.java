import java.util.ArrayList;

public class Teacher {
    public static int ID = 1;
    private final int PersonalID;
    private SubjectJournal journal;
    private ArrayList<Group> groups = new ArrayList<>();

    public Teacher(SubjectJournal journal, Group group) {
        PersonalID = ID++;
        this.journal = journal;
        groups.add(group);
    }

    public void addGroup(Group group) {
        journal.addGroup(group);
    }

    public Thread fillJournal() {
        addMarkThread thread = new addMarkThread(journal, groups, PersonalID);
        thread.start();
        return thread;
    }
}
