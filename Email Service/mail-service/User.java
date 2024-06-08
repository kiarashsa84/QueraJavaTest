public class User {
    private final String name;
    public int allowedEmails;
    
    public User(String name, int allowedEmails) {
        this.name = name;
        this.allowedEmails = allowedEmails;
    }
    
    public String getName() {
        return name;
    }
}
