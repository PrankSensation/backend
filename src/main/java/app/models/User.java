package app.models;

import app.security.PasswordEncoder;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "`user`")

public class User {

    //  Variables
    @Id
//  @GeneratedValue(strategy = IDENTITY)
    @JsonView(view.Id.class)
    private String uuid;

    @JsonView(view.User.class)
    private String firstName;

    @JsonView(view.User.class)
    private String lastName;

    @Column(unique = true)
    @JsonView(view.User.class)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    @JsonView(view.User.class)
    private Roles role;


    @JsonView(view.User.class)
    private String income;

    @JsonView(view.User.class)
    private String companySize;
    @JsonView(view.User.class)
    private String companySizeMarketing;
    @JsonView(view.User.class)
    private String companyName;

    @JsonView(view.User.class)
    private String purchaseQuotient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonView(view.User.class)
    @JsonBackReference(value = "users")
    private Sector sector;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "user")
    private List<Attempt> attempts = new ArrayList<>();

    //  Constructor
    public User(String firstName, String lastName, String email, String password, Roles role, String income, Sector sector, String companySize, String companySizeMarketing, String companyName,String purchaseQuotient) {
        this.uuid = generateUuid();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = hashPassword(password);
        this.role = role;
        this.income = income;
        this.sector = sector;
        this.companySize = companySize;
        this.companySizeMarketing = companySizeMarketing;
        this.companyName = companyName;
        this.purchaseQuotient=purchaseQuotient;
    }


    public User() {
        this.uuid = generateUuid();
    }

    // Methods
    private String generateUuid() {
        return UUID.randomUUID().toString();
    }

    public String hashPassword(String password) {

        PasswordEncoder passwordEncoder = new PasswordEncoder();
        return passwordEncoder.encode(password);
    }
    public String getCompanySizeMarketing(){
        return companySizeMarketing;
    }
    public String getCompanyName(){
        return this.companyName;
    }
    //  Getters and Setters
    public String getUuid() {
        return uuid;
    }

    public String getPurchaseQuotient() {
        return purchaseQuotient;
    }

    public void setRandomUuid() {
        this.uuid = generateUuid();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getCompanySize() {
        return companySize;
    }

    public String getIncome() {
        return income;
    }

    public Sector getSector() {
        return sector;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public void setCompanySizeMarketing(String companySizeMarketing) {
        this.companySizeMarketing = companySizeMarketing;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setPurchaseQuotient(String purchaseQuotient) {
        this.purchaseQuotient = purchaseQuotient;
    }

    public List<Attempt> getAttempts() {
        return attempts;
    }

    public void setAttempts(List<Attempt> attempts) {
        this.attempts = attempts;
    }
}
