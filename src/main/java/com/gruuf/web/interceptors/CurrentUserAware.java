package com.gruuf.web.interceptors;

import com.gruuf.model.User;

public interface CurrentUserAware {

    void withUser(User currentUser);

}
