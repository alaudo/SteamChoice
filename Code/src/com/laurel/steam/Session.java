package com.laurel.steam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexg on 3/14/2017.
 */
public class Session {
    public Workshop Workshop;
    public int Position;
    public int Capacity;
    public List<Choice> AssignedChoices;

    public Session () {
        AssignedChoices = new ArrayList<>();
    }

}
