package by.novik.securitybank.dto;

import lombok.Data;

@Data
public class CardInformationResponse {


    Long cardNumber;
    private String name;
    private String surname;
    private String date;
    private int sum;

}
