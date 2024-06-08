import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MailService {
    private final List<Mail> mails = new ArrayList<>();
    private final List<User> registerUsers = new ArrayList<>();

    private static MailService ml;

    private MailService(){

    }


    
    public boolean checkUser(User user) {
        return registerUsers.contains(user);
    }

    public List<Mail> getMails(User user) {
        Stream<Mail> stMails = mails.stream().filter(x -> x.getReceiver().equals(user));
        mails.stream().filter(x -> x.getReceiver().equals(user)).forEach(x -> x.setRead(true));
        return stMails.collect(Collectors.toList());
    }



    public boolean addMail(Mail mail) {
        if(mail.getSender().allowedEmails > 0 & mail.getContent().length() <= 255){
            mail.getSender().allowedEmails--;
            return mails.add(mail);
        }
        return false;
    }
    
    public List<User> getRegisterUsers() {
        return registerUsers;
    }
    
    public List<Mail> getMails() {
        return mails;
    }
    
    public static MailService getObject() {
        if(ml == null){
            ml = new MailService();
        }
        return ml;
    }
}
