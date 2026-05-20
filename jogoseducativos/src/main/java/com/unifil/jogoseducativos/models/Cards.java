package com.unifil.jogoseducativos.models;

import com.unifil.jogoseducativos.models.Enums.CardValue;
import com.unifil.jogoseducativos.models.Enums.Suit;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Cards {
    private CardValue value;
    private Suit suit;
}