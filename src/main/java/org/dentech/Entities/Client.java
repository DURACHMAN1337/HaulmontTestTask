package org.dentech.Entities;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "clients")
public class Client extends AbstractEntityClass implements Comparable<Client> {

    @NotNull
    @Column(name = "firstname")
    private String firstname;

    @NotNull
    @Column(name = "surname")
    private String surname;

    @NotNull
    @Column(name = "patronymic")
    private String patronymic;

    @NotNull
    @Column(name = "phoneNumber", unique = true)
    private Long phoneNumber;

    @NotNull
    @Column(name = "email", unique = true)
    private String email;

    @NotNull
    @Column(name = "passport", unique = true)
    private Long passport;

    public Client() {
    }

    public Client(String firstname, String surname, String patronymic, Long phoneNumber, String email, Long passport) {
        this.firstname = firstname;
        this.surname = surname;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.passport = passport;
    }

    @Override
    public String toString() {
        return "Client{" +
                "firstname='" + this.firstname + '\'' +
                ", surname='" + this.surname + '\'' +
                ", patronymic='" + this.patronymic + '\'' +
                ", phoneNumber=" + this.phoneNumber +
                ", email='" + this.email + '\'' +
                ", passport=" + this.passport +
                '}';
    }

    @Override
    public int compareTo(Client client) {
        return this.getSurname().toLowerCase().compareTo(client.getSurname().toLowerCase());
    }
}
