package com.unifil.jogoseducativos.dtos.request;

import lombok.Data;

@Data
public class StartGameDtoRequest {

    private Long playerId;
    private Double bet;
}
