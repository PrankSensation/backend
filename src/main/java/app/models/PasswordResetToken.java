package app.models;

import jakarta.persistence.*;

import java.util.Calendar;
import java.util.Date;


@Entity
@Table( name = "`password_reset`")
public class PasswordResetToken {
//    Token is valid for 24 hours
    private static final int EXPIRATION = 24*60;


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    private String token;

    private String userUuid;

    private Date expirationDate;

    public PasswordResetToken(User user, String token) {
        this.userUuid = user.getUuid();
        this.token = token;
        this.expirationDate = this.createExpirationDate();
    }

    public PasswordResetToken() {

    }

    public Date createExpirationDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.SECOND, EXPIRATION);
        return new Date(cal.getTime().getTime());
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
