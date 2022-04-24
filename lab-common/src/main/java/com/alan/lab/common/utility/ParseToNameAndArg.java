package com.alan.lab.common.utility;

public class ParseToNameAndArg {
    private String name;
    private String arg;

    public ParseToNameAndArg(String line) {
        String[] lineSplit = line.split(" ", 2);
        this.name = lineSplit[0];
        this.arg = lineSplit.length == 2 ? lineSplit[1] : "";
    }

    public String getArg() {
        return arg;
    }

    public String getName() {
        return name;
    }
}
