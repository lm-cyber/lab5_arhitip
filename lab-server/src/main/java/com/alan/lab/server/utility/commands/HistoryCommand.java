package com.alan.lab.server.utility.commands;

import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.HistoryManager;

public class HistoryCommand extends Command{
    private final HistoryManager historyManager;

    public HistoryCommand( HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    @Override
    public Response execute(Object argOrData) {
        return new Response(historyManager.niceToString(),false);
    }
}
