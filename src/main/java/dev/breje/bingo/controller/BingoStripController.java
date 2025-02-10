package dev.breje.bingo.controller;

import dev.breje.bingo.service.BingoStripGeneratorService;
import dev.breje.bingo.service.BingoStripService;
import dev.breje.bingo.service.BingoStripValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bingo-strips")
public class BingoStripController {

    @Autowired
    private BingoStripService bingoStripService;
    
    
    
}
