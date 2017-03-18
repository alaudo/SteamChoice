package com.laurel.steam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alexg on 3/14/2017.
 */
public class Student {
    private final String FirstName;
    private final String LastName;
    private final String Teacher;

    private final Map<Integer,Choice> Preferences;
    private final Map<Integer,Session> Sessions;

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getTeacher() {
        return Teacher;
    }

    public Map<Integer, Choice> getPreferences() {
        return Preferences;
    }

    public Map<Integer, Session> getSessions() {
        return Sessions;
    }

    public Student(String firstName, String lastName, String teacher) {
        Sessions = new HashMap<>();
        Preferences = new HashMap<>();

        FirstName = firstName;
        LastName = lastName;
        Teacher = teacher;

    }

    public static Student FromString(String s) {

        String[] ss = s.split("\\t");

        Student st = new Student(ss[1],ss[0],ss[1]);

        for(int i = 1; i < 6;i++) {
            if (ss[i + 3] == "0") break;

            Choice es = new Choice(st, i, SteamChoice.Workshops.get(Integer.parseInt(ss[i + 3])));

            st.getPreferences().put(i,es);

            // adding to a global repository
            SteamChoice.Choices.add(es);
        }

        return st;
    }


}
