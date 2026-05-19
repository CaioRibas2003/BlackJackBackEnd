package com.unifil.jogoseducativos.models.Enums;

import lombok.Getter;

@Getter
public enum CardValue {
    AS(1, 11),
    TWO(2, 2),
    THREE(3, 3),
    FOUR(4, 4),
    FIVE(5, 5),
    SIX(6, 6),
    SEVEN(7, 7),
    EIGHT(8, 8),
    NINE(9, 9),
    TEN(10, 10),
    JACK(10, 10),
    QUEEN(10, 10),
    KING(10, 10);

    private final int valorMinimo;
    private final int valorMaximo;

    CardValue(int valorMinimo, int valorMaximo) {
        this.valorMinimo = valorMinimo;
        this.valorMaximo = valorMaximo;
    }
}