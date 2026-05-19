package com.unifil.jogoseducativos.models;

import com.unifil.jogoseducativos.models.Enums.GameStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "games")
@Data
@NoArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "deck_id")
    private Deck deck;

    @Enumerated(EnumType.STRING)
    private GameStatus status;

    @Column(nullable = false)
    private int aposta;
}
