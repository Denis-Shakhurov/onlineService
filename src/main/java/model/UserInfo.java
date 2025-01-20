package model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserInfo {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String token;
}
