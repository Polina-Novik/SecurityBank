package by.novik.securitybank.controller;


import by.novik.securitybank.dto.AuthRequest;
import by.novik.securitybank.dto.AuthResponse;

import by.novik.securitybank.entity.User;
import by.novik.securitybank.jwt.JwtTokenUtil;
import by.novik.securitybank.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final CardService cardService;
    private final JwtTokenUtil tokenUtil;


    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Authorization", description = "this method is for authorization",
            responses = {@ApiResponse(responseCode = "200",
                    description = "you are logged in"),
                    @ApiResponse(responseCode = "404",
                            description = "account not found", content = @Content)
            })

    @PostMapping("login")
    public AuthResponse login(@Parameter(name = "login and password",
            description = "Enter your login and password", required = true) @RequestBody AuthRequest authRequest, HttpSession session) {
        User user = cardService.getTokenForUserIfExists(authRequest);
        session.setAttribute("token", tokenUtil.generateToken(user.getLogin()));
        return new AuthResponse(tokenUtil.generateToken(user.getLogin()));
    }
}
