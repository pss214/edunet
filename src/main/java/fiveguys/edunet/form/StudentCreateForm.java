package fiveguys.edunet.form;

import lombok.Data;

@Data
public class StudentCreateForm {
    private String username;
    private String password;
    private String passwordck;
    private String studentName;
    private String email;
    private String phonenumber;
}
