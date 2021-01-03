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
@Named(value = "newEmail")
@RequestScoped
public class NewEmail {

    private String to;
    private String title;
    private String body;
    DataStorage data = new SQL_Database();

    /**
     * Creates a new instance of NewEmail
     */
    public NewEmail() {
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String sendEmail(String from, int replyFor) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            return ("internalError");
        }

        return data.sendEmail(to, title, body, from, replyFor);
    }

}
