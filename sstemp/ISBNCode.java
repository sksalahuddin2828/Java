import java.util.Scanner;
import java.util.regex.*;

class User {
    private String name;
    private String mobile;
    private String username;
    private String password;

    public User(String name, String mobile, String username, String password) {
        this.name = name;
        this.mobile = mobile;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

public class UserBO {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        String name=sc.nextLine();
        String mobile =sc.nextLine();
        String username =sc.nextLine();
        String password=sc.nextLine();
        User user=new User(name,mobile,username,password);
        validate(user);

    }
    static void validate(User u){
    	
    	String ps1 = ".*[0-9].*";
    	String ps2 = ".*[!@#&()–[{}]:;',?/*~$^+=<>].*";
    	Pattern pattern1 = Pattern.compile(ps1);
    	Pattern pattern2 = Pattern.compile(ps2);
    	
        try{
            String password=u.getPassword();
            Matcher m1 = pattern1.matcher(password);
            Matcher m2 = pattern2.matcher(password);
            
            if(password.length()<10 || password.length()>20){
                throw new Exception("Should be minimum of 10 characters and maximum of 20 characters");
            }
            if(!m1.matches()){
                throw new Exception("Should contain as least one digit");
            }
            if(!m2.matches()){
                throw new Exception("It should contains as least one special character");
            }
            System.out.println("Valid Password");
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
