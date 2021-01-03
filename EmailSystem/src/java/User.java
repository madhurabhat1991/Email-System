
import java.util.ArrayList;
import javax.faces.context.FacesContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author madhu
 */
public class User {

    private String email;
    private String name;
    private int replyFor;
    private EmailBody selectedEmail;
    private EmailBody originalEmail;
    private ReplyToEmail replyToEmailPage;
    DataStorage data = new SQL_Database();

    public User(String email) {
        this.email = email;
        getUser();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReplyFor() {
        return replyFor;
    }

    public void setReplyFor(int replyFor) {
        this.replyFor = replyFor;
    }

    public EmailBody getSelectedEmail() {
        return selectedEmail;
    }

    public void setSelectedEmail(EmailBody selectedEmail) {
        this.selectedEmail = selectedEmail;
    }

    public EmailBody getOriginalEmail() {
        return originalEmail;
    }

    public void setOriginalEmail(EmailBody originalEmail) {
        this.originalEmail = originalEmail;
    }

    public ReplyToEmail getReplyToEmailPage() {
        return replyToEmailPage;
    }

    public void setReplyToEmailPage(ReplyToEmail replyToEmailPage) {
        this.replyToEmailPage = replyToEmailPage;
    }

    public DataStorage getData() {
        return data;
    }

    public void setData(DataStorage data) {
        this.data = data;
    }

    public void getUser() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("internalError");
        }
        this.name = data.getUser(email);
    }

    public String newEmail() {
        replyFor = 0;
        return "newEmail";
    }

    public ArrayList<EmailBody> getInboxEmails() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("internalError");
        }

        return data.getInboxEmails(email);
    }

    public String selectEmail(EmailBody email) {
        selectedEmail = email;

        if (selectedEmail.getReplyFor() > 0) {
            this.originalEmail = data.getOriginalEmail(email.getReplyFor());
        }
        return "emailPage";
    }

    public ArrayList<EmailBody> getRecentEmails() {
        ArrayList<EmailBody> recentEmails = new ArrayList<>();

        for (int i = 0, j = 0; i < getInboxEmails().size(); i++, j++) {
            if (j <= 1) {
                recentEmails.add(getInboxEmails().get(i));
            } else {
                break;
            }
        }
        return recentEmails;
    }

    public String replyToEmail(EmailBody replyEmail) {
        this.replyToEmailPage = new ReplyToEmail();
        this.replyToEmailPage.setTo(replyEmail.getSender());
        this.replyToEmailPage.setTitle("RE: " + replyEmail.getTitle());
        this.replyFor = replyEmail.getId();
        return "replyToEmail";
    }

    public ArrayList<EmailBody> getSentEmails() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("internalError");
        }

        return data.getSentEmails(email);
    }

    public String deleteEmail(int id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("internalError");
        }

        return data.deleteEmail(id);
    }


    public ArrayList<EmailBody> getTrashEmails() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("internalError");
        }

        return data.getTrashEmails(email);
    }
    
    public String signOut() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index.xhtml";

    }

}
