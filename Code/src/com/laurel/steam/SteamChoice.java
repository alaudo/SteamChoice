package com.laurel.steam;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by Alexander Galkin on 3/14/2017.
 */
public class SteamChoice {
    public static void main(String[] args) {

        String wpath = "";
        // reading workshops first -- they have no dependencies
        try (Stream<String> stream = Files.lines(Paths.get(wpath))) {
            stream.forEach(System.out::println);
        }
        catch (Exception ex)
        {}
    }

}
