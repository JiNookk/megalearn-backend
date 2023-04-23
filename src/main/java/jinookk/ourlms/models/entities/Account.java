package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.RegisterRequestDto;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.PhoneNumber;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Account {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "name"))
    private Name name;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "user_name"))
    private Name userName;

    private String password;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "phone_number"))
    private PhoneNumber phoneNumber;

    public Account() {
    }

    public Account(Name name, Name userName) {
        this.name = name;
        this.userName = userName;
    }

    public Account(Name name, Name userName, String phoneNumber) {
        this.name = name;
        this.userName = userName;
        this.phoneNumber = new PhoneNumber(phoneNumber);
    }

    public Account(Long id, Name name, Name userName) {
        this.id = id;
        this.name = name;
        this.userName = userName;
    }

    public static Account fake(String name) {
        return fake(new Name(name));
    }

    private static Account fake(Name name) {
        Name userName = new Name("ojw0828@naver.com");
        Long id = 1L;

        return new Account(id, name, userName);
    }

    public static Account of(RegisterRequestDto registerRequestDto) {
        Name name = new Name(registerRequestDto.getName());
        Name userName = new Name(registerRequestDto.getUserName());
        String phoneNumber = registerRequestDto.getPhoneNumber();

        return new Account(name, userName, phoneNumber);
    }

    public Long id() {
        return id;
    }

    public Name name() {
        return name;
    }

    public String phoneNumber() {
        return phoneNumber.value();
    }

    public Name userName() {
        return userName;
    }

    public boolean authenticate(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.password);
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }
}
