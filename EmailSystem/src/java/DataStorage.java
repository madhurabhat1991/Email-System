
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author madhu
 */
public interface DataStorage {

    void initializeDatabase();

    boolean uniqueEmail(String email);

    String createAccount(String email, String name, String password);

    String login(String email, String password);

    String getUser(String email);

    String sendEmail(String to, String title, String body, String from, int replyFor);

    ArrayList<EmailBody> getInboxEmails(String email);

    EmailBody getOriginalEmail(int replyFor);

    ArrayList<EmailBody> getSentEmails(String email);

    String deleteEmail(int id);

    ArrayList<EmailBody> getTrashEmails(String email);

}
