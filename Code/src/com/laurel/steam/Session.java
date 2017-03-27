package com.laurel.steam;

import java.io.PrintWriter;
import java.io.StringWriter;
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

    public int getAvailable() {
        return AssignedChoices.size() - getCapacity();
    }

    public boolean isFull () {
        return getCapacity() <= AssignedChoices.size();
    }

    public boolean assignChoice(Choice c) {
        if (isFull()) return false;
        AssignedChoices.add(c);
        c.setSession(this);
        return true;
    }

    public Session(com.laurel.steam.Workshop workshop, int position, int capacity) {
        Workshop = workshop;
        Position = position;
        Capacity = capacity;
        AssignedChoices = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter, true);
        writer.println("Session " + getPosition());
        AssignedChoices
                .forEach(c -> writer.println(Formatting.getTabs(1) + c.getStudent().getLastName() +", " + c.getStudent().getFirstName()));
        writer.println(Formatting.getTabs(2) + AssignedChoices.size() + " students in this session.");
        return stringWriter.toString();

    }
}
