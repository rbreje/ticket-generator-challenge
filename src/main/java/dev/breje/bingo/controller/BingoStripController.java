package dev.breje.bingo.controller;

import dev.breje.bingo.controller.dtos.Bingo90StripResponse;
import dev.breje.bingo.model.Bingo90Strip;
import dev.breje.bingo.service.IBingoStripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/bingo-90/strips")
public class BingoStripController {

    private final IBingoStripService bingoStripService;
    private final BingoStripConverter converter;

    @Autowired
    public BingoStripController(IBingoStripService bingoStripService, BingoStripConverter bingoStripConverter) {
        this.bingoStripService = bingoStripService;
        this.converter = bingoStripConverter;
    }

    @PostMapping
    public ResponseEntity<Bingo90StripResponse> generateBingo90Strip() {
        Bingo90Strip bingo90Strip = bingoStripService.generateBingo90Strip();
        Bingo90StripResponse response = converter.convertToBingo90StripResponse(bingo90Strip);
        return ResponseEntity.ok(response);
    }
}
