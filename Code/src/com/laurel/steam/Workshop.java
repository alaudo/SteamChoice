package com.laurel.steam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alexg on 3/14/2017.
 */
public class Workshop {
    public int Id;
    public String Title;
    public Map<Integer,Session> Sessions;
    private String Leader;
    private String Location;

    public Workshop () {
        Sessions = new HashMap<>();
    }

    public static Workshop FromString(String s) {
        Workshop w = new Workshop();

        String[] ss = s.split("\\t");
        w.Id = Integer.parseInt(ss[0]);
        for(int i = 1; i < 6;i ++) {
            Session es = new Session();
            es.Capacity = Integer.parseInt(ss[i]);
            es.Workshop = w;
            es.Position = i;
            w.Sessions.put(i,es);

            // adding to a global store
            SteamChoice.Sessions.add(es);
        }
        w.Title = ss[6];
        w.Location = ss[7];
        w.Leader = ss[8];
        return w;
    }
}
