package by.novik.securitybank.controller;


import by.novik.securitybank.dto.CardInformationResponse;
import by.novik.securitybank.dto.ClientInformationResponse;
import by.novik.securitybank.jwt.JwtTokenUtil;
import by.novik.securitybank.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Tag(name = "controller", description = "this is my controller with transfer object pattern")
@RequiredArgsConstructor
@RestController
@SecurityRequirement(name = "JWT")
@RequestMapping("card")
public class CardController {
    private final CardService cardService;
    private final JwtTokenUtil tokenUtil;

    @PostMapping("client")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find information about client", description = "This method returns name and " +
            "surname if client is present",
            responses = {@ApiResponse(responseCode = "200",
                    description = "client's information:",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientInformationResponse.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"name\": \"Ben\",\n" +
                                    "  \"surname\": \"Smith\"\n" +
                                    "}")
                    )}),
                    @ApiResponse(responseCode = "404",
                            description = "client not found", content = @Content)
            })
    public ClientInformationResponse findClientInformation(@Parameter(name = "cardNumber",
            description = "Enter card number", required = true) @RequestParam Long cardNumber, HttpSession session) {
        String login = tokenUtil.getLoginFromToken((String) session.getAttribute("token"));
        if (login.equals(cardService.findCard(cardNumber).getUser().getLogin())) {
            return cardService.findClientInformation(cardNumber);
        } else {
            return null;
        }
    }


    @PostMapping("information")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find information about card", description = "This method returns name, surname, " +
            "card number, card validity period, card balance if card is present",
            responses = {@ApiResponse(responseCode = "200",
                    description = "card information:",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CardInformationResponse.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"cardNumber\": 1111111111111111,\n" +
                                    "  \"name\": \"Ben\",\n" +
                                    "  \"surname\": \"Smith\",\n" +
                                    "  \"date\": \"02/23\",\n" +
                                    "  \"sum\": 1000\n" +
                                    "}")
                    )}),
                    @ApiResponse(responseCode = "404",
                            description = "card not found", content = @Content)
            })
    public CardInformationResponse findCardInformation(@Parameter(name = "cardNumber",
            description = "Enter card number", required = true) @RequestParam Long cardNumber, HttpSession session) {

        String login = tokenUtil.getLoginFromToken((String) session.getAttribute("token"));
        if (login.equals(cardService.findCard(cardNumber).getUser().getLogin())) {
            return cardService.findCardInformation(cardNumber);
        } else {
            return null;
        }

    }

    @PostMapping("pay")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Card payment", description = "This is a payment method if card is present",
            responses = {@ApiResponse(responseCode = "200",
                    description = "card information:",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CardInformationResponse.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"cardNumber\": 1111111111111111,\n" +
                                    "  \"name\": \"Ben\",\n" +
                                    "  \"surname\": \"Smith\",\n" +
                                    "  \"date\": \"02/23\",\n" +
                                    "  \"sum\": 1000\n" +
                                    "}")
                    )}),
                    @ApiResponse(responseCode = "404",
                            description = "card not found", content = @Content)
            })
    public CardInformationResponse payByCard(@Parameter(name = "cardNumber",
            description = "Enter card number", required = true) @RequestParam Long cardNumber,
                                             @Parameter(name = "price",
                                                     description = "Payment amount", required = true) @RequestParam int price, HttpSession session) {
        String login = tokenUtil.getLoginFromToken((String) session.getAttribute("token"));
        if (login.equals(cardService.findCard(cardNumber).getUser().getLogin())) {
            return cardService.payByCard(cardNumber, price);
        } else {
            return null;
        }

    }

    @PostMapping("remittance")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Remittance", description = "This method is needed to transfer money from " +
            "one card to another if card is present",
            responses = {@ApiResponse(responseCode = "200",
                    description = "card information:",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CardInformationResponse.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"cardNumber\": 1111111111111111,\n" +
                                    "  \"name\": \"Ben\",\n" +
                                    "  \"surname\": \"Smith\",\n" +
                                    "  \"date\": \"02/23\",\n" +
                                    "  \"sum\": 1000\n" +
                                    "}")
                    )}),
                    @ApiResponse(responseCode = "404",
                            description = "card not found", content = @Content)
            })
    public CardInformationResponse remittance(@Parameter(name = "cardNumber1",
            description = "Enter your card number", required = true)
                                              @RequestParam Long cardNumber1,
                                              @Parameter(name = "price",
                                                      description = "Transfer amount", required = true)
                                              @RequestParam int price, @Parameter(name = "cardNumber2",
            description = "Enter the card number to which you want to transfer money", required = true)
                                              @RequestParam Long cardNumber2, HttpSession session) {
        String login = tokenUtil.getLoginFromToken((String) session.getAttribute("token"));
        if (login.equals(cardService.findCard(cardNumber1).getUser().getLogin())) {
            return cardService.remittance(cardNumber1, price, cardNumber2);
        } else {
            return null;
        }

    }

}
