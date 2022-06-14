package com.alan.lab.server.usermanagers;

import com.alan.lab.common.users.AuthCredentials;

public interface UserManager {
    Long authenticate(AuthCredentials auth);
    Long register(AuthCredentials auth);
    String getUsernameById(long userId);
}
