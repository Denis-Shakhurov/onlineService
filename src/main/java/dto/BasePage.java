package dto;

import lombok.Getter;
import lombok.Setter;
import model.UserInfo;
import utils.NamedRoutes;

@Getter
@Setter
public class BasePage extends NamedRoutes {
    private UserInfo userInfo;
    private String flash;
}
