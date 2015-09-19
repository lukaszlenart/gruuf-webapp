package me.rbw.web.interceptors;

import me.rbw.model.User;

public interface CurrentUserAware {

    void setUser(User currentUser);

}
