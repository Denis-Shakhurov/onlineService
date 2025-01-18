package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPage extends BasePage {
    private User user;
}
