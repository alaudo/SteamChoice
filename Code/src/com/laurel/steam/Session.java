package com.laurel.steam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexg on 3/14/2017.
 */
public class Session {
    private final Workshop Workshop;
    private final int Position;
    private final int Capacity;
    private final List<Choice> AssignedChoices;

    public com.laurel.steam.Workshop getWorkshop() {
        return Workshop;
    }

    public int getPosition() {
        return Position;
    }

    public int getCapacity() {
        return Capacity;
    }

    public Session(com.laurel.steam.Workshop workshop, int position, int capacity) {
        Workshop = workshop;
        Position = position;
        Capacity = capacity;
        AssignedChoices = new ArrayList<>();
    }
}
