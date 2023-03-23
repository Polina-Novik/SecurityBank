package by.novik.securitybank.service;


import by.novik.securitybank.converter.CardConverter;
import by.novik.securitybank.dto.AuthRequest;
import by.novik.securitybank.dto.CardInformationResponse;
import by.novik.securitybank.dto.ClientInformationResponse;
import by.novik.securitybank.entity.Card;
import by.novik.securitybank.entity.User;
import by.novik.securitybank.repository.CardRepository;
import by.novik.securitybank.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor

public class CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final CardConverter converter;

    public Card findCard(Long cardNumber) {
        return cardRepository.findByCardNumber(cardNumber);
    }

    public ClientInformationResponse findClientInformation(Long cardNumber) {
        Card card = findCard(cardNumber);
        return converter.convertClient(card);
    }

    public CardInformationResponse findCardInformation(Long cardNumber) {
        Card card = cardRepository.findById(cardNumber).orElseThrow(() -> new RuntimeException("entity not found"));
        return converter.convert(card);

    }

    public CardInformationResponse payByCard(Long cardNumber, int price) {
        Card card = cardRepository.findById(cardNumber).orElseThrow(() -> new RuntimeException("entity not found"));
        if (price < card.getSum()) {
            card.setSum(card.getSum() - price);
            return converter.convert(cardRepository.save(card));
        } else {
            throw new RuntimeException("not enough money");
        }

    }

    public CardInformationResponse remittance(Long cardNumber1, int price, Long cardNumber2) {
        Card card1 = cardRepository.findById(cardNumber1).orElseThrow(() -> new RuntimeException("entity not found"));
        Card card2 = cardRepository.findById(cardNumber2).orElseThrow(() -> new RuntimeException("entity not found"));
        if (price < card1.getSum()) {
            card1.setSum(card1.getSum() - price);
            card2.setSum(card2.getSum() + price);
            converter.convert(cardRepository.save(card2));
            return converter.convert(cardRepository.save(card1));
        } else {
            throw new RuntimeException("not enough money");
        }
    }


    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User getTokenForUserIfExists(AuthRequest authRequest) {
        return findByLoginAndPassword(authRequest.getLogin(), authRequest.getPassword()).orElseThrow();

    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        User user = findByLogin(login).orElseThrow();
        if (password.equals(user.getPassword())) {
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
