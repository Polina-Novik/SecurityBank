package by.novik.securitybank.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users_security_bank")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;

    @OneToMany
    @JoinColumn(name = "card_number")
    private List<Card> cards;
}
