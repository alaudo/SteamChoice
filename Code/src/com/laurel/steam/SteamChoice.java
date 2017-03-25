package com.laurel.steam;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
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

        long seed = System.nanoTime();
        Collections.shuffle(Sessions, new Random(seed));
        Collections.shuffle(Students, new Random(seed));
        // Sessions.sort(Comparator.comparingInt(Session::getPosition));

        //AssignSessions
        AssignSessions(Sessions);


        WriteReport("./data/report.txt");
        WriteCards("./data/cards.csv");
        WriteSummary("./data/summary.txt");
        WriteStatistics("./data/statistics.txt");
        WriteBadluck("./data/badluck.txt");

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

    public static void WriteBadluck(String filename)
    {
        try(  PrintStream out = new PrintStream(new File(filename)) ){
            Workshop dworkshop = new Workshop(0, "", "", "");
            Session dsession = new Session(dworkshop,0,0);

            out.println("");

                Choices.stream().filter(s -> !s.isAssigned() && s.getWorkshop() != null).collect(Collectors.groupingBy(Choice::getPosition)).forEach(
                        (pref,lchoices) -> {
                            out.println("");
                            out.println("Missed " + pref + " choice: ");
                            out.println("");
                            lchoices.forEach(
                                    ch -> {
                                        out.println(
                                                String.format( "%1$-30s %2$-23s",
                                                        ch.getStudent().getLastName() + ", " + ch.getStudent().getFirstName(),
                                                        ch.getWorkshop().getTitle()
                                                        )
                                        );
                                    }
                            );
                            out.println("--------------------------------------------------------");
                        }
                );

        } catch (Exception ex) {
            System.out.println(ex.toString());

        }
    }

    public static void WriteStatistics(String filename)
    {
        try(  PrintStream out = new PrintStream(new File(filename)) ){
            Workshop dworkshop = new Workshop(0, "", "", "");
            Session dsession = new Session(dworkshop,0,0);
            out.println("");
            out.println("");
            out.println(" Student                      Teacher       Fitness    --- Choices ----");
            for(Student s: Students.stream().sorted((l,r) -> l.getLastName().compareToIgnoreCase(r.getLastName())).collect(Collectors.toList())) {
                String prefs = String.join("  ", s.getPreferences().values().stream().map( c -> (c.isAssigned()) ? Integer.toString(c.getSession().getPosition()) : "*").collect(Collectors.toList()));
                out.println(
                        String.format( "%1$-30s%2$-13s %3$6f   %4$13s",
                                s.getLastName() + ", " + s.getFirstName(),
                                s.getTeacher(),
                                s.getFitness(),
                                prefs
                        )
                );
            }

        } catch (Exception ex) {
            System.out.println(ex.toString());

        }
    }



    public static void WriteSummary(String filename)
    {
        try(  PrintStream out = new PrintStream(new File(filename)) ){
            out.println("");
            out.println("");
            out.println(" Wrkshop  SignedUp/Limit  Space   Description              ----- Choice -----");
            out.println("                                                            1   2   3  4  1-4");
            for(Workshop w: Workshops.values()) {
                long enrolled =
                        Choices.stream()
                        .filter(c -> c.getWorkshop() == w)
                        .count();

                long available =
                        w.getSessions()
                                .values()
                                .stream()
                                .mapToInt(Session::getCapacity)
                                .sum();

                Map<Integer,Long> chs =
                        Choices.stream()
                                .filter(c -> c.getWorkshop() == w)
                                .collect(Collectors.groupingBy(Choice::getPosition,Collectors.counting()));

                out.println(
                        String.format("%1$6d%2$10d  /%3$3d     %4$-7d%5$-23s%6$4d%7$4d%8$4d%9$4d%10$4d",
                                w.getId(),
                                    enrolled,
                                    available,
                                    available - enrolled,
                                    w.getTitle(),
                                    chs.getOrDefault(1,0l),
                                    chs.getOrDefault(2,0l),
                                    chs.getOrDefault(3,0l),
                                    chs.getOrDefault(4,0l),
                                    chs.values().stream().mapToLong(a -> a).sum()
                                )
                );
            }

            long totalenrolled =
                    Choices.stream()
                            .filter(c -> c.isAssigned())
                            .count();

            long totalshortage =
                    Choices.stream()
                            .filter(c -> !c.isAssigned() && c.getPosition() <= 4)
                            .count();

            out.println(String.format("Total enrolled: %1$4d   Total shortage: %2$4d",totalenrolled, totalshortage));

            out.println("");
            out.println("");
            out.println(" Wrkshop  Enrolled/Limit  Space   Description              ---- Enrolled ----");
            out.println("                                                            1   2   3  4  1-4");
            for(Workshop w: Workshops.values()) {
                long enrolled =
                        Choices.stream()
                                .filter(c -> c.getWorkshop() == w && c.isAssigned())
                                .count();

                long available =
                        w.getSessions()
                                .values()
                                .stream()
                                .mapToInt(Session::getCapacity)
                                .sum();

                Map<Integer,Long> chs =
                        Choices.stream()
                                .filter(c -> c.getWorkshop() == w && c.isAssigned())
                                .map(d -> d.getSession())
                                .collect(Collectors.groupingBy(Session::getPosition,Collectors.counting()));

                out.println(
                        String.format("%1$6d%2$10d  /%3$3d     %4$-7d%5$-23s%6$4d%7$4d%8$4d%9$4d%10$4d",
                                w.getId(),
                                enrolled,
                                available,
                                available - enrolled,
                                w.getTitle(),
                                chs.getOrDefault(1,0l),
                                chs.getOrDefault(2,0l),
                                chs.getOrDefault(3,0l),
                                chs.getOrDefault(4,0l),
                                chs.values().stream().mapToLong(a -> a).sum()
                        )
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

        for(int i = 1; i <= 6; i ++) {

            // find all sessions of this choice
            int finalI = i;
            Choices
                    .stream()
                    .filter(ch -> !ch.isAssigned() && ch.getPosition() <= finalI) // which is still not assigned
                    .sorted(Choice.getComparator())
                    .forEach(
                            ss -> {
                                List<Session> assignss =
                                Sessions
                                    .stream()
                                        .filter(p -> p.getWorkshop() == ss.getWorkshop() // same workshop
                                                 && !p.isFull()
                                                && ss.getStudent().getSessions().get(p.getPosition()) == null) // and no other colliding session
                                        .collect(Collectors.toList());

                                Collections.shuffle(assignss);

                                Optional<Session> assigns = assignss.stream().findFirst();

                                if (assigns.isPresent()) {
                                    assigns.get().assignChoice(ss);
                                }
                            }
                    );
        }

    }



    public static void AssignSessionsStochastic(List<Session> s) {
        Random random = new Random();

        for(int i = 0; i < 10000; i++) {
            int index = random.nextInt(s.size());
            Session session = s.get(index);
            if (session.isFull()) continue;

            Choices
                    .stream()
                    .filter(ch -> ch.getWorkshop() == session.getWorkshop() // same workshop
                            && !ch.isAssigned() && // which is still not assigned
                            ch.getStudent().getSessions().get(session.getPosition()) == null) // and no other colliding session
                    .sorted(Choice.getComparator())
                    // .limit(session.getCapacity())
                    .limit(1)
                    .forEach(
                            chs -> {
                                session.assignChoice(chs);
                            }
                    );
        }
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
