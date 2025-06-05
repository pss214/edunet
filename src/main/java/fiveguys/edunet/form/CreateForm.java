package fiveguys.edunet.form;

import lombok.Data;

@Data
public class CreateForm {
    private String username;
    private String password;
    private String passwordck;
    private String name;
    private String email;
    private String phone;
    private String type;
}
