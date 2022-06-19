package com.alan.lab.server.utility;

import java.util.StringJoiner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class HistoryManager {
    private final int capacity = 13;
    private final ConcurrentLinkedQueue<String> history = new ConcurrentLinkedQueue<>();

    public void addNote(String note) {
        if (history.size() == capacity) {
            history.remove();
        }
        history.add(note);
    }

    public String niceToString() {
        StringJoiner stringJoiner = new StringJoiner("\n");
        stringJoiner.add("The last 13 commands were:");
        for (String commandName : history) {
            stringJoiner.add(commandName);
        }
        return stringJoiner.toString();
    }
}
