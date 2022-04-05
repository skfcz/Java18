package de.groygroy.java18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;

public class DefaultEncoding {

    public static void main(String ... args) throws Exception {

        // Lesen und Schreiben mit ISO-8859-1.
        // Standardencoding ist sonst immer UTF-8
        try (var out = new FileWriter("out.txt", StandardCharsets.ISO_8859_1)) {
            out.write("äöüß°");
        }
        // Lesen und Schreiben in UTF-8 (Standardencoding)
        try (var in = new BufferedReader(new FileReader("out.txt"))) {
            System.out.println("Eingelesen mit falschem Encoding '" + in.readLine() + "'");
        }
        // Lesen und Schreiben in ISO-8859-1
        try (var in = new BufferedReader(new FileReader("out.txt", StandardCharsets.ISO_8859_1))) {
            System.out.println("Eingelesen mit korrekten Encoding '" + in.readLine() + "'");
        }
    }
}
