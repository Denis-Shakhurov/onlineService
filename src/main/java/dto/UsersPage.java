package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import model.User;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsersPage extends BasePage {
    private List<User> users;
}
