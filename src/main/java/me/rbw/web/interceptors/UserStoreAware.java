package me.rbw.web.interceptors;

import me.rbw.services.UserRegister;

public interface UserStoreAware {

    void setUserService(UserRegister userRegister);

}
