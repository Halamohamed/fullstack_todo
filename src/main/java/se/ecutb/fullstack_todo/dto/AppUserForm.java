package se.ecutb.fullstack_todo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AppUserForm {
    @NotBlank(message = "Username is required")
    @Size(min = 2, max = 255, message = "Need to have between 2 and 255 letters")
    private String username;
    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[0-9]+.*)(?=.*[a-zA-Z]+.*)[0-9a-zA-Z]{6,}$", message = "Must contain at least one letter, one number, and be longer than six characters.")
    private String password;
    @NotBlank(message = "You need to confirm your password")
    private String passwordConfirm;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}
