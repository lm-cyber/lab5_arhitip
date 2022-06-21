package com.alan.lab.common.users;

import java.io.Serializable;

public class AuthCredentials implements Serializable {
    private static final long serialVersionUID = 9130887391730028055L;
    private final String login;
    private final String password;
    private boolean newUser;

    public AuthCredentials(String login, String password) {
        this.login = login;
        this.password = password;
        this.newUser = false;
    }

    public AuthCredentials(String login, String password, boolean newUser) {
        this.login = login;
        this.password = password;
        this.newUser = newUser;
    }

    public void setNewUser(boolean newUser) {
        this.newUser = newUser;
    }

    public boolean isNewUser() {
        return newUser;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
