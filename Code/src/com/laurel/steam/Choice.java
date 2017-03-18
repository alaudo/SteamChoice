package com.laurel.steam;

import java.util.Comparator;
import java.util.Optional;

/**
 * Created by alexg on 3/14/2017.
 */
public class Choice {
    private final Student Student;
    private final int Position;
    private final Workshop Workshop;
    private Optional<Session> Session;

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
        return Session.get();
    }

    public void setSession(com.laurel.steam.Session session) {
        Session = Optional.of(session); // set session
        getStudent().getSessions().put(session.getPosition(),session); // added to the list
        getStudent().updateFitness(); // recompute fitness

    }

    public boolean isAssigned() {
        return Session.isPresent();
    }

    public static Comparator<Choice> getComparator() {
        Comparator<Choice> c =
                Comparator.comparingInt(Choice::getPosition);
        c.thenComparing(
                (f,s) -> (int) (f.getStudent().getFitness() - s.getStudent().getFitness())
        );

        return c;
    }

    public Choice(com.laurel.steam.Student student, int position, com.laurel.steam.Workshop workshop) {
        Student = student;
        Position = position;
        Workshop = workshop;
        Session = Optional.empty();
    }
}
