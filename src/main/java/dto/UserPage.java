package dto;

import lombok.Getter;
import lombok.Setter;
import model.Order;
import model.Service;
import model.User;

import java.util.List;

@Getter
@Setter
public class UserPage extends BasePage {
    private User user;
    private List<Service> services;
    private List<Order> orders;
}
