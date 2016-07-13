package com.gruuf.web.interceptors;

import com.gruuf.model.User;

public interface CurrentUserAware {

    void setUser(User currentUser);

}
