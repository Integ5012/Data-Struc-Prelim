package prelim;

import java.util.Date;

public class Email {
    private int emailId;
    private User sender;
    private User recipient;
    private String subject;
    private String body;
    private Date date;
    private LinkedList<Attachment> attachments;

    public Email(int var1, User var2, User var3, String var4, String var5) {
        this.emailId = var1;
        this.sender = var2;
        this.recipient = var3;
        this.subject = var4;
        this.body = var5;
        this.date = new Date();
        this.attachments = new LinkedList();
    }

    public int getEmailId() {
        return this.emailId;
    }

    public void setEmailId(int var1) {
        this.emailId = var1;
    }

    public User getSender() {
        return this.sender;
    }

    public void setSender(User var1) {
        this.sender = var1;
    }

    public User getRecipient() {
        return this.recipient;
    }

    public void setRecipient(User var1) {
        this.recipient = var1;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String var1) {
        this.subject = var1;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String var1) {
        this.body = var1;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date var1) {
        this.date = var1;
    }

    public LinkedList<Attachment> getAttachments() {
        return this.attachments;
    }

    public void addAttachment(Attachment var1) {
        this.attachments.add(var1);
    }

    public void removeAttach(int var1) {
        this.attachments.remove(var1);
    }

    public String toString() {
        int var10000 = this.emailId;
        return "Email{emailId=" + var10000 + ", sender=" + this.sender.getName() + ", recipient=" + this.recipient.getName() + ", subject='" + this.subject + "', body='" + this.body + "', date=" + String.valueOf(this.date) + ", attachments=" + this.attachments.size() + "}";
    }
}
