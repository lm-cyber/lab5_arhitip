package com.alan.lab.client.utility;

import com.alan.lab.client.data.Color;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.Stack;

/**
 * This class is used for all the user input: keyboard and script execution
 */
public class UserInputManager {
    private final Scanner scanner = new Scanner(System.in);
    private final Stack<BufferedReader> currentFilesReaders = new Stack<>();
    private final Stack<File> currentFiles = new Stack<>();

    public Long readLongValue(String message, OutputManager outputManager) {
        boolean shouldContinue = true;
        Long longResult = null;
        while (shouldContinue) {
            outputManager.println("enter" + message + ":");
            try {
                longResult = Long.parseLong(scanner.nextLine());
                shouldContinue = false;
            } catch (NumberFormatException e) {
            }
        }
        return longResult;
    }

    public Double readDoubleValue(String message, OutputManager outputManager) {
        boolean shouldContinue = true;
        Double doubleResult = null;
        while (shouldContinue) {
            outputManager.println("enter" + message + ":");
            try {
                doubleResult = Double.parseDouble(scanner.nextLine());
                shouldContinue = false;
            } catch (NumberFormatException e) {
            }
        }
        return doubleResult;
    }

    public Integer readIntegerValue(String message, OutputManager outputManager) {
        boolean shouldContinue = true;
        Integer integerResult = null;
        while (shouldContinue) {
            outputManager.println("enter" + message + ":");
            try {
                integerResult = Integer.parseInt(scanner.nextLine());
                shouldContinue = false;
            } catch (NumberFormatException e) {
            }
        }
        return integerResult;
    }

    public Float readFloatValue(String message, OutputManager outputManager) {
        boolean shouldContinue = true;
        Float floatResult = null;
        while (shouldContinue) {
            outputManager.println("enter" + message + ":");
            try {
                floatResult = Float.parseFloat(scanner.nextLine());
                shouldContinue = false;
            } catch (NumberFormatException e) {
            }

        }
        return floatResult;
    }

    public Color readHairColorValue(String message, OutputManager outputManager) {
        boolean shouldContinue = true;
        Color colorResult = null;
        while (shouldContinue) {
            outputManager.println("enter" + message + ":");
            try {
                colorResult = Color.valueOf(scanner.nextLine());
                shouldContinue = false;
            } catch (IllegalArgumentException e) {
            }
        }
        return colorResult;
    }

    public LocalDateTime readBirthdayValue(String message, OutputManager outputManager) {
        boolean shouldContinue = true;
        LocalDateTime time = null;
        while (shouldContinue) {
            outputManager.println("enter" + message + ":");
            try {
                time = LocalDateTime.parse(scanner.nextLine());
                shouldContinue = false;
            } catch (DateTimeParseException e) {
            }
        }
        return time;
    }


    public String nextLine() {
        if (!currentFilesReaders.isEmpty()) {
            try {
                String input = currentFilesReaders.peek().readLine();
                if (input == null) {
                    currentFiles.pop();
                    currentFilesReaders.pop().close();
                    return nextLine();
                } else {
                    return input;
                }


            } catch (IOException e) {
                // never throws exception
                e.printStackTrace();
            }

        } else {
            return scanner.nextLine();
        }

        // never returns ""
        return "";
    }

    public void connectToFile(File file) throws IOException, UnsupportedOperationException {
        if (currentFiles.contains(file)) {
            throw new UnsupportedOperationException("The file was not executed due to recursion");
        } else {
            BufferedReader newReader = new BufferedReader(new FileReader(file));
            currentFiles.push(file);
            currentFilesReaders.push(newReader);
        }
    }

}
