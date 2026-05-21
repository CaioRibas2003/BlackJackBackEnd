package com.unifil.jogoseducativos.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PlayerDtoResponse {

    private Long id;
    private String name;
    private double amount;
}
