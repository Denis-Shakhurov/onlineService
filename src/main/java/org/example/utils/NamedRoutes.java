package org.example.utils;

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

    public String getUserPath(int id) {
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

    public String getEditUserPath(int id) {
        return  getEditUserPath(String.valueOf(id));
    }

    public String getLoginUserPath() {
        return  "/login";
    }

    public String getLogoutUserPath() {
        return  "/logout";
    }

    public String getServicesByUserPath(String id) {
        return  "/users/" + id + "/services";
    }

    public String getServicesByUserPath(int id) {
        return getServicesByUserPath(String.valueOf(id));
    }

    public String getServicesPath() {
        return  "/services";
    }

    public String getEditServicesPath(String id) {
        return  "/services/" + id + "/edit";
    }

    public String getEditServicesPath(int id) {
        return  getEditServicesPath(String.valueOf(id));
    }

    public String getServicePath(String id) {
        return  "/services/" + id;
    }

    public String getServicePath(int id) {
        return  getServicePath(String.valueOf(id));
    }

    public String getOrdersByUserPath(String id) {
        return  "/users/" + id + "/orders";
    }

    public String getOrdersByUserPath(int id) {
        return getOrdersByUserPath(String.valueOf(id));
    }

    public String getOrderPath(String id) {
        return  "/orders/" + id;
    }

    public String getOrderPath(int id) {
        return getOrderPath(String.valueOf(id));
    }

    public String getOrdersPath() {
        return  "/orders";
    }

    public String getOrdersByServicePath(String id) {
        return  "/services/" + id + "/orders";
    }

    public String getOrdersByServicePath(int id) {
        return getOrdersByServicePath(String.valueOf(id));
    }
}
