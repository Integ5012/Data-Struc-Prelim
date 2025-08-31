package prelim;

public class User {
    private int userId;
    private String name;
    private String email;
    private String password;
    private LinkedList<Email> inbox;
    private LinkedList<Email> sent;

    public User(int var1, String var2, String var3, String var4) {
        if (var2 != null && !var2.trim().isEmpty()) {
            if (var3 != null && !var3.trim().isEmpty()) {
                if (!EmailValidator.isValidEmail(var3)) {
                    throw new IllegalArgumentException("Invalid email format. Please use a valid email address.");
                } else if (var4 != null && !var4.trim().isEmpty()) {
                    if (var4.length() < 3) {
                        throw new IllegalArgumentException("Password must be at least 3 characters long");
                    } else {
                        this.userId = var1;
                        this.name = var2.trim();
                        this.email = var3.trim().toLowerCase();
                        this.password = var4;
                        this.inbox = new LinkedList();
                        this.sent = new LinkedList();
                    }
                } else {
                    throw new IllegalArgumentException("Password cannot be null or empty");
                }
            } else {
                throw new IllegalArgumentException("Email cannot be null or empty");
            }
        } else {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int var1) {
        this.userId = var1;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String var1) {
        this.name = var1;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String var1) {
        this.email = var1;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String var1) {
        this.password = var1;
    }

    public LinkedList<Email> getInbox() {
        return this.inbox;
    }

    public void setInbox(LinkedList<Email> var1) {
        this.inbox = var1;
    }

    public LinkedList<Email> getSent() {
        return this.sent;
    }

    public void setSent(LinkedList<Email> var1) {
        this.sent = var1;
    }

    public boolean login(String var1) {
        return this.password.equals(var1);
    }

    public Email sendEmail(User var1, String var2, String var3) {
        int var4 = (int)(System.currentTimeMillis() % 1000000L);
        Email var5 = new Email(var4, this, var1, var2, var3);
        this.sent.add(var5);
        var1.getInbox().add(var5);
        return var5;
    }

    public String readInbox() {
        if (this.inbox.isEmpty()) {
            return "Inbox is empty.";
        } else {
            StringBuilder var1 = new StringBuilder();
            var1.append("Inbox (").append(this.inbox.size()).append(" emails):\n");

            for(int var2 = 0; var2 < this.inbox.size(); ++var2) {
                Email var3 = (Email)this.inbox.get(var2);
                var1.append(var2 + 1).append(". From: ").append(var3.getSender().getName()).append(" (").append(var3.getSender().getEmail()).append(")\n");
                var1.append("   Subject: ").append(var3.getSubject()).append("\n");
                var1.append("   Date: ").append(var3.getDate()).append("\n");
                var1.append("   Body: ").append(var3.getBody().substring(0, Math.min(50, var3.getBody().length()))).append(var3.getBody().length() > 50 ? "..." : "").append("\n\n");
            }

            return var1.toString();
        }
    }

    public void receiveEmail(Email var1) {
        this.inbox.add(var1);
    }

    public String toString() {
        int var10000 = this.userId;
        return "User{userId=" + var10000 + ", name='" + this.name + "', email='" + this.email + "', inboxSize=" + this.inbox.size() + ", sentSize=" + this.sent.size() + "}";
    }
}
