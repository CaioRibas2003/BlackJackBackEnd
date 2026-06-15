package com.unifil.jogoseducativos.services;

import com.unifil.jogoseducativos.dtos.response.GameDtoResponse;
import com.unifil.jogoseducativos.models.Cards;
import com.unifil.jogoseducativos.models.Enums.CardValue;
import com.unifil.jogoseducativos.models.Enums.GameStatus;
import com.unifil.jogoseducativos.models.Enums.Suit;
import com.unifil.jogoseducativos.models.Player;
import com.unifil.jogoseducativos.repositories.PlayerRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
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

    private List<Cards> generateShuffledDeck() {
        List<Cards> deck = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (CardValue value : CardValue.values()) {
                deck.add(Cards.builder()
                        .suit(suit)
                        .value(value)
                        .build());
            }
        }
        Collections.shuffle(deck);
        return deck;
    }

    private Cards drawCard(List<Cards> deck) {
        if (deck.isEmpty()) throw new RuntimeException("Deck is empty");
        return deck.removeFirst();
    }

    private int calculateScore(List<Cards> cards) {
        int total = 0;
        int aces = 0;

        for (Cards card : cards) {
            total += card.getValue().getMaxValue();
            if (card.getValue() == CardValue.ACE) aces++;
        }

        while (total > 21 && aces > 0) {
            total -= 10;
            aces--;
        }

        return total;
    }

    public GameDtoResponse hit(Long playerId) {
        GameSession session = getActiveSession(playerId);
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        session.getPlayerCards().add(drawCard(session.getDeck()));
        int score = calculateScore(session.getPlayerCards());

        if (score > 21) {
            session.setStatus(GameStatus.DEALER_WIN);
            playerService.updateAmount(playerId, -session.getBet());
        } else if (score == 21) {
            return stand(playerId);
        }

        return buildResponse(session, player);
    }

    private GameSession getActiveSession(Long playerId) {
        GameSession session = sessions.get(playerId);
        if (session == null) throw new RuntimeException("No active session for this player");
        if (session.getStatus() != GameStatus.IN_PROGRESS) throw new RuntimeException("Game already finished, start a new one");
        return session;
    }

    public GameDtoResponse stand(Long playerId) {
        GameSession session = getActiveSession(playerId);
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        session.setStatus(GameStatus.DEALER_TURN);

        return buildResponse(session, player);
    }

    private GameDtoResponse buildResponse(GameSession session, Player player) {
        List<Cards> dealerVisible = switch (session.status) {
            case IN_PROGRESS -> session.getDealerCards().subList(0, 1);
            default -> session.getDealerCards();
        };

        String message = switch (session.status) {
            case PLAYER_WIN -> "You win! +" + session.getBet();
            case DEALER_WIN -> "Dealer wins! -" + session.getBet();
            case DRAW -> "Draw! Bet returned.";
            case IN_PROGRESS -> "Your turn: Hit or Stand?";
            case DEALER_TURN -> "Dealer's turn...";
        };

        return GameDtoResponse.builder()
                .playerCards(session.getPlayerCards())
                .dealerCards(dealerVisible)
                .playerScore(calculateScore(session.getPlayerCards()))
                .dealerScore(calculateScore(dealerVisible))
                .status(session.getStatus())
                .playerAmount(player.getAmount())
                .bet(session.getBet())
                .message(message)
                .build();
    }

    public GameDtoResponse dealerNext(Long playerId) {
        GameSession session = sessions.get(playerId);
        if (session == null) throw new RuntimeException("No active session for this player");
        if (session.getStatus() != GameStatus.DEALER_TURN) throw new RuntimeException("It's not dealer's turn");

        int dealerScore = calculateScore(session.getDealerCards());
        int playerScore = calculateScore(session.getPlayerCards());

        if (dealerScore < 17) {
            session.getDealerCards().add(drawCard(session.getDeck()));
            dealerScore = calculateScore(session.getDealerCards());
        }

        if (dealerScore >= 17) {
            if (dealerScore > 21 || playerScore > dealerScore) {
                session.setStatus(GameStatus.PLAYER_WIN);
                playerService.updateAmount(playerId, session.getBet());
            } else if (playerScore == dealerScore) {
                session.setStatus(GameStatus.DRAW);
            } else {
                session.setStatus(GameStatus.DEALER_WIN);
                playerService.updateAmount(playerId, -session.getBet());
            }
        }

        Player updatedPlayer = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        return buildResponse(session, updatedPlayer);
    }

    @Data
    private static class GameSession {
        List<Cards> deck;
        List<Cards> playerCards;
        List<Cards> dealerCards;
        Double bet;
        GameStatus status;
    }
}
