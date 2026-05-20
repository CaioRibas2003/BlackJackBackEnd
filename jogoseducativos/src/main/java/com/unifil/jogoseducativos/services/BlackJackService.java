package com.unifil.jogoseducativos.services;

import com.unifil.jogoseducativos.dtos.response.GameDtoResponse;
import com.unifil.jogoseducativos.models.Cards;
import com.unifil.jogoseducativos.models.Enums.GameStatus;
import com.unifil.jogoseducativos.models.Player;
import com.unifil.jogoseducativos.repositories.PlayerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BlackJackService {

    private final PlayerRepository playerRepository;
    private final PlayerService playerService;

    private final Map<Long, GameSession> sessions = new ConcurrentHashMap<>();

    public BlackJackService(PlayerRepository playerRepository, PlayerService playerService) {
        this.playerRepository = playerRepository;
        this.playerService = playerService;
    }

    public GameDtoResponse startGame(Long playerId, Double bet) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        if (bet <= 0) throw new IllegalArgumentException("Bet must be greater than zero");
        if (bet > player.getAmount()) throw new IllegalArgumentException("Insufficient balance");

        GameSession session = new GameSession();
        session.deck = generateShuffledDeck();
        session.bet = bet;
        session.status = GameStatus.IN_PROGRESS;
        session.playerCards = new ArrayList<>();
        session.dealerCards = new ArrayList<>();

        session.playerCards.add(drawCard(session.deck));
        session.dealerCards.add(drawCard(session.deck));
        session.playerCards.add(drawCard(session.deck));
        session.dealerCards.add(drawCard(session.deck));

        if (calculateScore(session.playerCards) == 21) {
            session.status = GameStatus.PLAYER_WIN;
            playerService.updateAmount(playerId, bet);
        }

        sessions.put(playerId, session);
        return buildResponse(session, player);
    }
    
    private static class GameSession {
        List<Cards> deck;
        List<Cards> playerCards;
        List<Cards> dealerCards;
        Double bet;
        GameStatus status;
    }
}
