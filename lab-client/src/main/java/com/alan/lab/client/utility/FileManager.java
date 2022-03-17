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
        Scanner scanner = new Scanner(file);
        String json = "";
        while (scanner.hasNextLine()) {
            json += scanner.nextLine() + '\n';
        }
        scanner.close();
        return json;
    }

    public void write(String json) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(path);
        printWriter.write(json);
        printWriter.close();
    }
}
