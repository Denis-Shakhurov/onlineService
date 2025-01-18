package utils;

import lombok.Getter;

@Getter
public class NamedRoutes {

    public String getStartPath() {
        return  "/";
    }

    public String getUsersPath() {
        return  "/users";
    }

    public String getUserPath(String id) {
        return  "/users/" + id;
    }

    public String getUserPath(Integer id) {
        return  getUserPath(String.valueOf(id));
    }

    public String getRegistrationPath() {
        return  "/registration";
    }

    public String getRegistrationUserPath() {
        return  "/registration/user";
    }

    public String getRegistrationMasterPath() {
        return  "/registration/master";
    }

    public String getEditUserPath(String id) {
        return  "/users/" + id + "/edit";
    }

    public String getEditUserPath(Integer id) {
        return  getEditUserPath(String.valueOf(id));
    }

    public String getLoginUserPath() {
        return  "/login";
    }

    public String getLogoutUserPath() {
        return  "/logout";
    }
}
