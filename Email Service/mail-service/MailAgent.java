import java.util.stream.Collectors;

public class MailAgent {
    private final User user;
    private static MailService ml = MailService.getObject();
    
    private MailAgent(User user) {
        this.user = user;
    }
    
    public static MailAgent createAgent(User user) {
        if(ml.checkUser(user)){
            return new MailAgent(user);
        }
        return null;
    }
    
    public boolean sendMail(String message, User receiver) {
        return ml.addMail(new Mail(this.user, receiver, message));
    }
    
    public String receiveMail() {
        String[] res;
        var lst = ml.getMails(this.user);

//        lst.stream().reduce((a,b) -> ("sender=" + a.getSender() + ", receiver=" + a.getReceiver() + ", content=" + a.getContent()) + ("sender=" + b.getSender() + ", receiver=" + b.getReceiver() + ", content=" + b.getContent()) );
        return lst.stream().map(x -> "sender=" + x.getSender().getName() + ", receiver=" + x.getReceiver().getName() + ", content="+ x.getContent()).collect(Collectors.joining("\n"));
    }
    //sender=Moein, receiver=Ali, content=Hello Ali
}
