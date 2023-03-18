package by.novik.securitybank.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "cards_of_users")

public class Card {
    @Id
    private Long cardNumber;
    private String name;
    private String surname;
    private int cvv;
    private String date;
    private int sum;
    private int pin;
    private String password;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
