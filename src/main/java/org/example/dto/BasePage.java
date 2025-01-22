package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.model.Order;
import org.example.model.Service;
import org.example.model.User;
import org.example.model.UserInfo;
import org.example.utils.NamedRoutes;

import java.util.List;

@Getter
@Setter
public class BasePage extends NamedRoutes {
    private UserInfo userInfo;
    private String flash;
    private List<User> masters;
    private List<Order> orders;
    private Service service;
}
