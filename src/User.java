import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by 63160 on 6/5/2017.
 */
public class User {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    public String username;
    private String password;
    private String userType;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String username, String pwd, String UserType){
        this.username = username;
        this.setPassword(pwd);
        this.setUserType(UserType);
        this.setId(UUID.randomUUID().toString());
    }



}
