import java.util.ArrayList;

public class GradesJournalTest {
    public static void main(String[] args) {
        SubjectJournal mathJournal = new SubjectJournal();
        Group group1 = new Group(30);
        Group group2 = new Group(33);
        Group group3 = new Group(35);
        mathJournal.addGroup(group1);
        mathJournal.addGroup(group2);
        mathJournal.addGroup(group3);

        Teacher lector = new Teacher(mathJournal, group1);
        lector.addGroup(group2);
        lector.addGroup(group3);
        Teacher assistant1 = new Teacher(mathJournal, group1);
        Teacher assistant2 = new Teacher(mathJournal, group2);
        Teacher assistant3 = new Teacher(mathJournal, group3);

        ArrayList<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            threads.add(lector.fillJournal());
            threads.add(assistant1.fillJournal());
            threads.add(assistant2.fillJournal());
            threads.add(assistant3.fillJournal());

            try {
                for (Thread thread : threads) {
                    thread.join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Journal WEEK" + (i+1) + ":");
            mathJournal.printJournalAverage();
        }

    }
}