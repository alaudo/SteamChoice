package com.laurel.steam;

/**
 * Created by alexg on 3/14/2017.
 */
public class Choice {
    private final Student Student;
    private final int Position;
    private final Workshop Workshop;
    private Session Session;

    public com.laurel.steam.Student getStudent() {
        return Student;
    }

    public int getPosition() {
        return Position;
    }

    public com.laurel.steam.Workshop getWorkshop() {
        return Workshop;
    }

    public com.laurel.steam.Session getSession() {
        return Session;
    }

    public void setSession(com.laurel.steam.Session session) {
        Session = session;
    }

    public Choice(com.laurel.steam.Student student, int position, com.laurel.steam.Workshop workshop) {
        Student = student;
        Position = position;
        Workshop = workshop;
        Session = null;
    }
}
