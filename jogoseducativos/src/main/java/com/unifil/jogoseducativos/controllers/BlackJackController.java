package com.unifil.jogoseducativos.controllers;

import com.unifil.jogoseducativos.dtos.request.ActionDtoRequest;
import com.unifil.jogoseducativos.dtos.request.StartGameDtoRequest;
import com.unifil.jogoseducativos.dtos.response.GameDtoResponse;
import com.unifil.jogoseducativos.services.BlackJackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

    @RestController
    @RequestMapping("/blackjack")
    @RequiredArgsConstructor
    public class BlackJackController {

        private final BlackJackService blackjackService;

        @PostMapping("/start")
        public ResponseEntity<GameDtoResponse> startGame(@RequestBody StartGameDtoRequest request) {
            return ResponseEntity.ok(blackjackService.startGame(request.getPlayerId(), request.getBet()));
        }

        @PostMapping("/hit")
        public ResponseEntity<GameDtoResponse> hit(@RequestBody ActionDtoRequest request) {
            return ResponseEntity.ok(blackjackService.hit(request.getPlayerId()));
        }

        @PostMapping("/stand")
        public ResponseEntity<GameDtoResponse> stand(@RequestBody ActionDtoRequest request) {
            return ResponseEntity.ok(blackjackService.stand(request.getPlayerId()));
        }

        @PostMapping("/dealer/next")
        public ResponseEntity<GameDtoResponse> dealerNext(@RequestBody ActionDtoRequest request) {
            return ResponseEntity.ok(blackjackService.dealerNext(request.getPlayerId()));
        }
    }

