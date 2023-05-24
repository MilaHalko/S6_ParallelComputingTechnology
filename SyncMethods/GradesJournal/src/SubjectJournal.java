import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class SubjectJournal {
    ConcurrentHashMap<Student, ArrayList<Integer>> journal = new ConcurrentHashMap<>();
    ArrayList<Group> groups = new ArrayList<>();

    public void addGroup(Group group) {
        groups.add(group);
        for (int i = 0; i < group.getSize(); i++) {
            if (!journal.containsKey(group.getStudent(i))) {
                journal.put(group.getStudent(i), new ArrayList<>());
            }
        }
    }

    public synchronized void addMark(Student student, int mark) {
        journal.get(student).add(mark);
    }

    public int getAverageMark(Student student) {
        int sum = 0;
        for (int mark : journal.get(student)) {
            sum += mark;
        }
        return sum / journal.get(student).size();
    }

    public void printJournalAverage() {
        for (Group group : groups) {
            System.out.println("Group " + group.getID() + ":");
            for (int i = 0; i < group.getSize(); i++) {
                System.out.println("   Student " + group.getStudent(i).getID() + ": " +
                        getAverageMark(group.getStudent(i)));
            }
        }
    }
}
