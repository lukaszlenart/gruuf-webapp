package me.rbw.web.interceptors;

import me.rbw.services.UserStore;

public interface UserStoreAware {

    void setUserStore(UserStore userStore);

}
