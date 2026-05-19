package com.unifil.jogoseducativos.models;
import com.unifil.jogoseducativos.models.Enums.Suit;
import com.unifil.jogoseducativos.models.Enums.CardValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cards {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardValue value;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Suit suit;

    @ManyToOne
    @JoinColumn(name = "deck_id")
    @JsonIgnore
    private Deck deck;
}

