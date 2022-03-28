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

    public String read() throws FileNotFoundException {
        File file = new File(this.path);
        String json = "";
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                json += scanner.nextLine() + '\n';
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
