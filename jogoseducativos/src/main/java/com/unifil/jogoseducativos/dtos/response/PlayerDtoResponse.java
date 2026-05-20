package com.unifil.jogoseducativos.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class PlayerDtoResponse {

    private Long id;
    private String name;
    private double amount;
}
