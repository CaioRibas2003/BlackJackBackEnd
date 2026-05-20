package com.unifil.jogoseducativos.dtos.response;

import com.unifil.jogoseducativos.models.Cards;
import com.unifil.jogoseducativos.models.Enums.GameStatus;

import java.util.List;

public class GameDtoResponse {

    private List<Cards> playerCards;
    private List<Cards> dealerCards;
    private int playerScore;
    private int dealerScore;
    private GameStatus status;
    private Double playerAmount;
    private String message;
}
