package com.laurel.steam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alexg on 3/14/2017.
 */
public class Student {
    public String FirstName;
    public String LastName;
    public String Teacher;

    public Map<Integer,Choice> Preferences;
    public Map<Integer,Session> Sessions;

    public Student() {
        Sessions = new HashMap<>();
        Preferences = new HashMap<>();
    }

    public static Student FromString(String s) {
        Student st = new Student();

        String[] ss = s.split("\\t");

        st.LastName = ss[0];
        st.FirstName = ss[1];
        st.Teacher = ss[2];

        for(int i = 1; i < 6;i++) {
            if (ss[i + 3] == "0") break;

            Choice es = new Choice();
            es.Position = i;
            es.Student = st;
            es.Workshop = SteamChoice.Workshops.get(Integer.parseInt(ss[i + 3]));

            st.Preferences.put(i,es);

            // adding to a global repository
            SteamChoice.Choices.add(es);
        }

        return st;
    }


}
