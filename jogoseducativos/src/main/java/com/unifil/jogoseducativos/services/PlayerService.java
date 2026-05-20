package com.unifil.jogoseducativos.services;

import com.unifil.jogoseducativos.dtos.request.PlayerDtoRequest;
import com.unifil.jogoseducativos.dtos.response.PlayerDtoResponse;
import com.unifil.jogoseducativos.models.Player;
import com.unifil.jogoseducativos.repositories.PlayerRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public PlayerDtoResponse createPlayer(PlayerDtoRequest dto) {

        Player player = Player.builder()
                .name(dto.getName())
                .amount(dto.getAmount())
                .build();

        return toResponse(playerRepository.save(player));
    }

    public PlayerDtoResponse findById(Long id) {
        return toResponse(playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found")));
    }

    public PlayerDtoResponse updateAmount(Long id, Double amount) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found"));
        player.setAmount(player.getAmount() + amount);
        return toResponse(playerRepository.save(player));
    }

    private PlayerDtoResponse toResponse(Player player) {
        return PlayerDtoResponse.builder()
                .id(player.getId())
                .name(player.getName())
                .amount(player.getAmount())
                .build();
    }
}
