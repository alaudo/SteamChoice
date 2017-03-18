package com.laurel.steam;

import java.io.*;
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


        WriteReport("./data/report.txt");
        WriteCards("./data/cards.csv");
        WriteSummary("./data/summary.txt");

        System.out.println("Finished");

    }

    public static void WriteReport(String filename)
    {
        try(  PrintStream out = new PrintStream(new File(filename)) ){
            for(Workshop w: Workshops.values()) {
                out.println(w.toString());
            }
        } catch (Exception ex) {

        }
    }


    public static void WriteSummary(String filename)
    {
        try(  PrintStream out = new PrintStream(new File(filename)) ){
            out.println("");
            out.println("");
            out.println(" Wrkshop  Enrolled/Limit  Space   Description              ----- Choice -----");
            out.println("");
            for(Workshop w: Workshops.values()) {
                out.println(
                        String.format("%1$6d%2$10d  /%3$3d     %4$-7d%5$-20s", w.getId(), 34,23,23,w.getTitle())
                );
            }

        } catch (Exception ex) {
            System.out.println(ex.toString());

        }
    }

    public static void WriteCards(String filename)
    {
        Workshop dworkshop = new Workshop(0, "", "", "");
        Session dsession = new Session(dworkshop,0,0);
        try(  PrintStream out = new PrintStream(new File(filename)) ){
            out.println("student,teacher,n1,w1,l1,n2,w2,l2,n3,w3,l3,n4,w4,l4"); // header
            for(Student s: Students) {
                out.println(
                        String.join(",",
                                s.getFirstName() + " " + s.getLastName(),
                                s.getTeacher(),
                                "1", s.getSessions().getOrDefault(1, dsession).getWorkshop().getTitle(), s.getSessions().getOrDefault(1, dsession).getWorkshop().getLocation(),
                                "2", s.getSessions().getOrDefault(2, dsession).getWorkshop().getTitle(), s.getSessions().getOrDefault(2, dsession).getWorkshop().getLocation(),
                                "3", s.getSessions().getOrDefault(3, dsession).getWorkshop().getTitle(), s.getSessions().getOrDefault(3, dsession).getWorkshop().getLocation(),
                                "4", s.getSessions().getOrDefault(4, dsession).getWorkshop().getTitle(), s.getSessions().getOrDefault(4, dsession).getWorkshop().getLocation()
                                )
                );
            }
        } catch (Exception ex) {

        }
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
