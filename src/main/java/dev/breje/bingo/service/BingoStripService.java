package dev.breje.bingo.service;

import dev.breje.bingo.model.Bingo90Strip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BingoStripService {

    @Autowired
    private BingoStripGeneratorService bingoStripGeneratorService;

    @Autowired
    private BingoStripValidationService bingoStripValidationService;
    
    public Bingo90Strip generate() {
        Bingo90Strip bingo90Strip = bingoStripGeneratorService.generateBingo90Strip();
        bingoStripValidationService.validateBingo90Strip(bingo90Strip);
        return bingo90Strip;
    }
}
