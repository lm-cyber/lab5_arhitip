package com.alan.lab.server.utility.usermanagers;


import com.alan.lab.common.users.AuthCredentials;

import java.util.HashMap;
import java.util.Map;

public class BasicUserManager implements UserManager {
    private long userCounter = 0;
    private final Map<String, String> usersPasswords = new HashMap<>();
    private final Map<Long, String> userIdMap = new HashMap<>();

    @Override
    public Long authenticate(AuthCredentials auth) {
        if (auth == null) {
            return null;
        }

        if (usersPasswords.get(auth.getLogin()).equals(auth.getPassword())) {
            for (Long id : userIdMap.keySet()) {
                if (userIdMap.get(id).equals(auth.getLogin())) {
                    return id;
                }
            }
        }
        return null;
    }

    @Override
    public Long register(AuthCredentials auth) {
        if (userIdMap.values().contains(auth.getLogin())) {
            return null;
        }

        usersPasswords.put(auth.getLogin(), auth.getPassword());
        userIdMap.put(++userCounter, auth.getLogin());
        return userCounter;
    }

    @Override
    public String getUsernameById(long userId) {
        return userIdMap.get(userId);
    }
}
