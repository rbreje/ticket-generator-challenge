package dev.breje.bingo.controller;

import dev.breje.bingo.controller.dtos.Bingo90StripResponse;
import dev.breje.bingo.service.IBingoStripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bingo-90/strips")
public class BingoStripController {

    private final IBingoStripService bingoStripService;

    @Autowired
    public BingoStripController(IBingoStripService bingoStripService) {
        this.bingoStripService = bingoStripService;
    }

    @PostMapping
    public ResponseEntity<Bingo90StripResponse> generateBingo90Strip() {
        Bingo90StripResponse response = null;
        bingoStripService.generateBingo90Strip();
        return ResponseEntity.ok(response);
    }
}
