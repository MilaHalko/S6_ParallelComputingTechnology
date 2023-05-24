public class Student {
    public static int CurrentID = 1;
    private final int PersonalID;

    public Student() {
        PersonalID = CurrentID++;
    }

    public Integer getID() {
        return PersonalID;
    }
}
