package me.rbw.web.interceptors;

import me.rbw.model.User;

public interface UserAware {

    void setUser(User currentUser);

}
