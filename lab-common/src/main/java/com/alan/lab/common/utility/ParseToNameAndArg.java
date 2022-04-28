package com.alan.lab.common.utility;

public class ParseToNameAndArg {
    private String name;
    private Object arg;
    public ParseToNameAndArg(String line) {
        String[] lineSplit = line.split(" ", 2);
        this.name = lineSplit[0];
        switch (name) {
            case "update" :
                this.arg = Long.parseLong(lineSplit[1]);
                break;
            case"remove_by_id":
                this.arg = Long.parseLong(lineSplit[1]);
                break;
            case "filter_greater_than_height" :
                this.arg = Float.parseFloat(lineSplit[1]);
                break;
            default:
                this.arg = lineSplit.length == 2 ? lineSplit[1] : "";
                break;
        }
    }


    public Object getArg() {
        return arg;
    }

    public String getName() {
        return name;
    }
}
