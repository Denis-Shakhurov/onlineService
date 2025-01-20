package dto;

import lombok.Getter;
import lombok.Setter;
import model.Service;
import model.User;
import model.UserInfo;
import utils.NamedRoutes;

import java.util.List;

@Getter
@Setter
public class BasePage extends NamedRoutes {
    private UserInfo userInfo;
    private String flash;

    private List<User> masters;
    private Service service;
}
