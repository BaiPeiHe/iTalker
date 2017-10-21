package com.baihe.italker.push;

/**
 * Created by baihe on 2017/10/21.
 */

public class UserService implements IUserService {
    @Override
    public String search(int hashCode) {
        return "User：" + hashCode;
    }
}
