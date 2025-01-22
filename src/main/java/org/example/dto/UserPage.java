package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.model.Order;
import org.example.model.Service;
import org.example.model.User;

import java.util.List;

@Getter
@Setter
public class UserPage extends BasePage {
    private User user;
    private List<Service> services;
    private List<Order> orders;
}
