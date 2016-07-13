package com.gruuf.web.interceptors;

import com.gruuf.services.UserStore;

public interface UserStoreAware {

    void setUserStore(UserStore userStore);

}
