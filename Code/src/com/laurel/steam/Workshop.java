package com.laurel.steam;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alexg on 3/14/2017.
 */
public class Workshop {
    private final int Id;
    private final String Title;
    private final String Leader;
    private final String Location;

    private final Map<Integer,Session> Sessions;

    public int getId() {
        return Id;
    }

    public String getTitle() {
        return Title;
    }

    public String getLeader() {
        return Leader;
    }

    public String getLocation() {
        return Location;
    }

    public Map<Integer, Session> getSessions() {
        return Sessions;
    }

    public Workshop(int id, String title, String leader, String location) {
        Id = id;
        Title = title;
        Leader = leader;
        Location = location;
        Sessions = new HashMap<>();
    }

    public static Workshop FromString(String s) {

        String[] ss = s.split("\\t");

        Workshop w = new Workshop(Integer.parseInt(ss[0]), ss[6], ss[8], ss[7]);

        for(int i = 1; i < 6;i ++) {
            Session es = new Session(w,i,Integer.parseInt(ss[i]));
            w.getSessions().put(i,es);

            // adding to a global store
            SteamChoice.Sessions.add(es);
        }

        return w;
    }

    @Override
    public String toString() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter, true);
        writer.println(Formatting.padToWidth(getId() + " "+ getTitle(), 70, getLocation().trim()));
        writer.println(Formatting.padToWidth(" ",70, getLeader()));
        for(int i = 1; i <= getSessions().size(); i++) {
            writer.println(getSessions().get(i));
        }

        writer.println("----------------------------------------------------------------------");

        return stringWriter.toString();
    }
}
