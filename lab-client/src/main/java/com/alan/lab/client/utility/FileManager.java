package com.alan.lab.client.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileManager {
    private final String path;

    public FileManager(String filename) {
        this.path = filename;
    }

    public StringBuilder read() throws FileNotFoundException {
        File file = new File(this.path);
        StringBuilder json = new StringBuilder("");
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                json.append(scanner.nextLine()).append('\n');
            }
        }
        return json;
    }

    public void write(String json) throws FileNotFoundException {
        try (PrintWriter printWriter = new PrintWriter(path)) {
            printWriter.write(json);
        }
    }
}
