package com.alan.lab.common.utility.nonstandardcommand;

import java.io.IOException;

public interface NonStandardCommand {
    boolean execute(String input) throws IOException;
}
