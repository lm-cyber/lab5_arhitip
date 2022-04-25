package com.alan.lab.server.utility;

import com.alan.lab.common.data.Color;
import com.alan.lab.common.exceptions.DoubleExecuteException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.Predicate;

/**
 * This class is used for all the user input: keyboard and script execution
 */
public class UserInputManager implements AutoCloseable {
    private final Scanner scanner = new Scanner(System.in);
    private final Stack<BufferedReader> currentFilesReaders = new Stack<>();
    private final Stack<File> currentFiles = new Stack<>();
    private boolean chekrek = false;

    public Long readLongValue(String message, OutputManager outputManager) {
        boolean shouldContinue = true;
        Long longResult = null;
        while (shouldContinue) {
            outputManager.println("enter" + message + ":");
            try {
                longResult = Long.parseLong(nextLine());
                shouldContinue = false;
            } catch (NumberFormatException e) {
                shouldContinue = true; // codestyle`
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
                doubleResult = Double.parseDouble(nextLine());
                shouldContinue = false;
            } catch (NumberFormatException e) {
                shouldContinue = true; // codestyle`
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
                integerResult = Integer.parseInt(nextLine());
                shouldContinue = false;
            } catch (NumberFormatException e) {
                shouldContinue = true; // codestyle`
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
                floatResult = Float.parseFloat(nextLine());
                shouldContinue = false;
            } catch (NumberFormatException e) {
                shouldContinue = true; // codestyle`
            }

        }
        return floatResult;
    }

    public Float readFloatValueH(String message, OutputManager outputManager, Predicate<Float> floatPredicate) {
        boolean shouldContinue = true;
        Float floatResult = null;
        while (shouldContinue) {
            outputManager.println("enter" + message + ":");
            try {
                String line = nextLine();

                floatResult = "".equals(line) ? null : Float.parseFloat(line);
                if (floatResult != null) {
                    shouldContinue = floatPredicate.test(floatResult);
                } else {
                    shouldContinue = false;
                }
            } catch (NumberFormatException e) {
                shouldContinue = true; // codestyle`
            }

        }
        return floatResult;
    }

    public Float readFloatValue(String message, OutputManager outputManager, Predicate<Float> floatPredicate) {
        boolean shouldContinue = true;
        Float floatResult = null;
        while (shouldContinue) {
            outputManager.println("enter" + message + ":");
            try {
                floatResult = Float.parseFloat(nextLine());
                shouldContinue = floatPredicate.test(floatResult);
            } catch (NumberFormatException e) {
                shouldContinue = true; // codestyle`
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
                String line = nextLine();
                colorResult = "".equals(line) ? null : Color.valueOf(line);
                shouldContinue = false;
            } catch (IllegalArgumentException e) {
                shouldContinue = true; // codestyle`
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
                time = LocalDateTime.parse(nextLine());
                shouldContinue = false;
            } catch (DateTimeParseException e) {
                shouldContinue = true; // codestyle`
            }
        }
        return time;
    }

    public String readStringWithPredicatValue(String message, OutputManager outputManager, Predicate<String> stringPredicate) {
        boolean shouldContinue = true;
        String passportId = null;
        while (shouldContinue) {
            outputManager.println("enter" + message + ":");
            try {
                passportId = nextLine();
                shouldContinue = stringPredicate.test(passportId);
            } catch (DateTimeParseException e) {
                shouldContinue = true; // codestyle`
            }
        }
        return passportId;
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
                throw new RuntimeException("????");
            }

        } else {
            chekrek = false;
            return scanner.nextLine();
        }
    }

    public void connectToFile(File file) throws IOException, UnsupportedOperationException {
        if (currentFiles.contains(file)) {
            scanner.close();
            throw new DoubleExecuteException();
        } else {
            chekrek = true;
            BufferedReader newReader = new BufferedReader(new FileReader(file));
            currentFiles.push(file);
            currentFilesReaders.push(newReader);
        }
    }

    private void closeBufferedReader() {
        if (!currentFilesReaders.isEmpty() && currentFilesReaders.peek() != null) {
            try {
                currentFilesReaders.pop().close();
            } catch (IOException e) {
                throw new RuntimeException("????");
            }
        }
    }

    public boolean getChekReg() {
        return chekrek;
    }

    public void close() {
        scanner.close();
        closeBufferedReader();
    }

}
