package com.alan.lab.client.utility;

public class ParseToNameAndArg {
    private String name;
    private String arg;
    public ParseToNameAndArg(String line)
    {
        String[] lineSplit = line.split(" ", 2);
        this.name = lineSplit[0];
        this.arg = lineSplit.length==2?lineSplit[1] : "";
    }

    public String[] getAll() {
        String[] all = new String[2];
        all[0] = name;
        all[1] = arg;
        return all;
    }

    public String getArg() {
        return arg;
    }

    public String getName() {
        return name;
    }
}
