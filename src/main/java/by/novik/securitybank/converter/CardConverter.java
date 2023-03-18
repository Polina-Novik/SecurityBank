package by.novik.securitybank.converter;


import by.novik.securitybank.dto.CardInformationResponse;
import by.novik.securitybank.dto.ClientInformationResponse;
import by.novik.securitybank.entity.Card;
import org.springframework.stereotype.Component;

@Component
public class CardConverter {

    public CardInformationResponse convert(Card card) {
        CardInformationResponse response = new CardInformationResponse();
        response.setCardNumber(card.getCardNumber());
        response.setName(card.getName());
        response.setSurname(card.getSurname());
        response.setDate(card.getDate());
        response.setSum(card.getSum());
        return response;
    }

    public ClientInformationResponse convertClient(Card card) {
        ClientInformationResponse response = new ClientInformationResponse();

        response.setName(card.getName());
        response.setSurname(card.getSurname());

        return response;
    }
}
