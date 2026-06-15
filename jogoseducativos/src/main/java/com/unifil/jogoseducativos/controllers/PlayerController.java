package com.unifil.jogoseducativos.controllers;

import com.unifil.jogoseducativos.dtos.request.PlayerDtoRequest;
import com.unifil.jogoseducativos.dtos.response.PlayerDtoResponse;
import com.unifil.jogoseducativos.services.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(("/players"))
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping
    public ResponseEntity<PlayerDtoResponse> createPlayer(@RequestBody PlayerDtoRequest playerDtoRequest){

        return ResponseEntity.ok(playerService.createPlayer(playerDtoRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDtoResponse> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(playerService.findById(id));
    }
}