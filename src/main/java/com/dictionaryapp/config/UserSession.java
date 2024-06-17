package com.dictionaryapp.config;

import com.dictionaryapp.model.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class UserSession {
    private long id;

    private String username;


    public void login(User user) {
        this.username = user.getUsername();
        this.id = user.getId();
    }

    public boolean isLoggedIn() {
        return this.id != 0;
    }


    public void logout() {
        id = 0;
        username = "";
    }

    public String getUsername() {
        return username;
    }
}
