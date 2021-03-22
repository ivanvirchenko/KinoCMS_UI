package com.avada.kino.models;

import com.avada.kino.enums.Gender;
import com.avada.kino.enums.Language;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String lastName;
    private String login;
    private String email;
    private String password;
    private String address;
    private String cardNumber;
    @Enumerated(EnumType.STRING)
    private Language language;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Embedded
    private Phone phone;
    private LocalDate birthDay;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    public User(String name, String lastName, String login, String email, String password, String address, String cardNumber, Language language, Gender gender, Phone phone, LocalDate birthDay, City city) {
        this.name = name;
        this.lastName = lastName;
        this.login = login;
        this.email = email;
        this.password = password;
        this.address = address;
        this.cardNumber = cardNumber;
        this.language = language;
        this.gender = gender;
        this.phone = phone;
        this.birthDay = birthDay;
        this.city = city;
    }
}
