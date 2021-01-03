/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author madhu
 */
@Named(value = "login")
@SessionScoped
public class Login implements Serializable {

    private String email;
    private String password;
    private User user;
    DataStorage data = new SQL_Database();

    /**
     * Creates a new instance of Login
     */
    public Login() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DataStorage getData() {
        return data;
    }

    public void setData(DataStorage data) {
        this.data = data;
    }

    public String login() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            return ("internalError");
        }

        String result = data.login(email, password);
        //outcomes - inbox, loginFailed, internalError

        if (result.equals("inbox")) {
            user = new User(email);
        }

        return result;
        //outcomes - inbox, loginFailed, internalError
    }

}
