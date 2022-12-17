package jinookk.ourlms.models.entities;

import jinookk.ourlms.models.vos.Name;

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

//    UserName userName;
//    Password password;

    public Account() {
    }

    public Account(Long id, Name name) {
        this.id = id;
        this.name = name;
    }

    public static Account fake(String name) {
        return fake(new Name(name));
    }

    private static Account fake(Name name) {
        Long id = 1L;
        return new Account(id, name);
    }

    public Long id() {
        return id;
    }

    public Name name() {
        return name;
    }
}
