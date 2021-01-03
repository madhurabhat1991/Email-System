/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author madhu
 */
@Named(value = "signUp")
@RequestScoped
public class SignUp {

    private String name;
    private String email;
    private String password;
    private String confirm;
    DataStorage data = new SQL_Database();

    /**
     * Creates a new instance of SignUp
     */
    public SignUp() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String signup() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            return ("internalError");
        }

        if (!data.uniqueEmail(email)) {
            return ("This email id is already taken! Please enter another email");
        }
        
        if (!password.equals(confirm)){
            return ("The password entered does not match! Please confirm the same password");
        }

        if (!validatePassword(password)) {
            return ("The password must be 3-20 characters long and must contain at "
                    + "least one letter, one digit and one special character among (!@#$%^&*?)");
        }

        return data.createAccount(email, name, password);
    }

    public boolean validatePassword(String password) {
        //should contain atleast one digit, one letter and one special character among (!#$%^&*?), 3-20 characters        
        return password.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*?]).{3,20}$");
    }

}
