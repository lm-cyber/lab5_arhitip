package com.alan.lab.client;

import com.alan.lab.common.data.Person;
import com.alan.lab.common.network.Request;
import com.alan.lab.common.network.RequestWithPerson;
import com.alan.lab.common.network.RequestWithPersonType;
import com.alan.lab.common.users.AuthCredentials;
import com.alan.lab.common.utility.OutputManager;
import com.alan.lab.common.utility.ParseToNameAndArg;
import com.alan.lab.common.utility.UserInputManager;

import java.io.IOException;

public class RequestCreator {
    private final ObjectSocketChannelWrapper remote;
    private final UserInputManager userInputManager;
    private final OutputManager outputManager;
    private RequestWithPersonType type;

    public RequestCreator(ObjectSocketChannelWrapper remote, UserInputManager userInputManager, OutputManager outputManager) {
        this.remote = remote;
        this.userInputManager = userInputManager;
        this.outputManager = outputManager;
    }

    public void requestCreate(String input, boolean[] addCommandAndAuth, AuthCredentials authCredentials) throws IOException {
        ParseToNameAndArg parseToNameAndArg = new ParseToNameAndArg(input);
        if (addCommandAndAuth[0]) {
            sendRequestWithPerson(type, authCredentials);
        } else {
            sendRequest(parseToNameAndArg, authCredentials);
            type = RequestWithPersonType.getEnum(parseToNameAndArg.getName().toUpperCase());
        }

    }

    public void auth(AuthCredentials authCredentials) throws IOException {
        Request request = new Request(authCredentials.isNewUser() ? "reg" : "log", authCredentials, authCredentials);
        remote.sendMessage(request);
    }

    private void sendRequestWithPerson(RequestWithPersonType rType, AuthCredentials authCredentials) throws IOException {
        RequestWithPerson requestWithPerson;
        Person person = AddElem.add(userInputManager, outputManager);
        requestWithPerson = new RequestWithPerson(person, rType, authCredentials);
        remote.sendMessage(requestWithPerson);
    }

    private void sendRequest(ParseToNameAndArg parseToNameAndArg, AuthCredentials authCredentials) throws IOException {
        Request request = new Request(parseToNameAndArg.getName(), parseToNameAndArg.getArg(), authCredentials);
        remote.sendMessage(request);
    }

}
