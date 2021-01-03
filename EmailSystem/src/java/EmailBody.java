/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author madhu
 */
public class EmailBody {

    private int id;
    private String title;
    private String body;
    private String sender;
    private String receiver;
    private String date;
    private int replyFor;
    private String status;

    public EmailBody(int id, String title, String body, String sender, String receiver,
            String date, int replyFor, String status) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.sender = sender;
        this.receiver = receiver;
        this.date = date;
        this.replyFor = replyFor;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getReplyFor() {
        return replyFor;
    }

    public void setReplyFor(int replyFor) {
        this.replyFor = replyFor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
