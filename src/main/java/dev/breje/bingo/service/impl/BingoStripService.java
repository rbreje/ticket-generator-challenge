package dev.breje.bingo.service.impl;

import dev.breje.bingo.model.Bingo90Strip;
import dev.breje.bingo.service.IBingoStripGeneratorService;
import dev.breje.bingo.service.IBingoStripService;
import dev.breje.bingo.service.IBingoStripValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BingoStripService implements IBingoStripService {

    private final IBingoStripGeneratorService bingoStripGeneratorService;
    private final IBingoStripValidationService bingoStripValidationService;

    @Autowired
    public BingoStripService(IBingoStripGeneratorService bingoStripGeneratorService, IBingoStripValidationService bingoStripValidationService) {
        this.bingoStripGeneratorService = bingoStripGeneratorService;
        this.bingoStripValidationService = bingoStripValidationService;
    }

    @Override
    public Bingo90Strip generateBingo90Strip() {
        Bingo90Strip bingo90Strip = bingoStripGeneratorService.generateBingo90Strip();
        bingoStripValidationService.validateBingo90Strip(bingo90Strip);
        return bingo90Strip;
    }
}
