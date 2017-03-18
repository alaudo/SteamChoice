package com.laurel.steam;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by Alexander Galkin on 3/14/2017.
 */
public class SteamChoice {
    public static List<Student> Students;
    public static Map<Integer,Workshop> Workshops;
    public static List<Session> Sessions;
    public static List<Choice> Choices;

    public static List<Choice> BadLuck;

    public static void main(String[] args) {

        // instantiating the core holders
        Students = new AllStudents();
        Workshops = new AllWorkshops();
        Sessions = new ArrayList<>();
        Choices = new ArrayList<>();
        BadLuck = new ArrayList<>();

        // reading data
        ReadInputFiles();

        //AssignSessions
        AssignSessions(Sessions);


        for(Workshop w: Workshops.values()) {
            System.out.println(w.toString());
        }

        System.out.println("Finished");

    }

    public static void AssignSessions(List<Session> s) {

        s.forEach(
                session ->
                {
                            Choices
                                .stream()
                                .filter(ch -> ch.getWorkshop() == session.getWorkshop() && !ch.isAssigned())
                                .sorted(Choice.getComparator())
                                .limit(session.getCapacity())
                                .forEach(
                                        chs -> { session.assignChoice(chs); }
                                );
                }
        );
    }


    public static void ReadInputFiles() {
        readWorkshops();

        readStudents();
    }

    private static void readStudents() {
        String spath = "./data/students.tsv";
        // reading students
        System.out.println("== Reading students from '" + Paths.get(spath) + "' ==");
        try (Stream<String> stream = Files.lines(Paths.get(spath))) {
            stream
                    .skip(1) // first should be the header
                    .forEach(s -> {
                        Student ss = Student.FromString(s);
                        Students.add(ss);
                    });
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
        System.out.println("== Students read, total " + Students.size() + " entries ==");
    }

    private static void readWorkshops() {
        String wpath = "./data/workshops.tsv";

        // reading workshops first -- they have no dependencies
        System.out.println("== Reading workshops from '" + Paths.get(wpath) + "' ==");
        try (Stream<String> stream = Files.lines(Paths.get(wpath))) {
            stream
                    .skip(1) // first should be the header
                    .forEach(w -> {
                        Workshop ww = Workshop.FromString(w);
                        Workshops.put(ww.getId(),ww);
                    });
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
        System.out.println("== Workshops read, total " + Workshops.size() + " entries ==");
    }

}
