package dev.breje.bingo;

import dev.breje.bingo.service.impl.BingoStripGeneratorService;
import dev.breje.bingo.service.impl.BingoStripService;
import dev.breje.bingo.service.impl.BingoStripValidationService;
import dev.breje.bingo.service.IBingoStripGeneratorService;
import dev.breje.bingo.service.IBingoStripService;
import dev.breje.bingo.service.IBingoStripValidationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BingoConfig {

    @Bean
    public IBingoStripService bingoStripService() {
        return new BingoStripService(bingoStripGeneratorService(), bingoStripValidationService());
    }

    @Bean
    public IBingoStripGeneratorService bingoStripGeneratorService() {
        return new BingoStripGeneratorService();
    }

    @Bean
    public IBingoStripValidationService bingoStripValidationService() {
        return new BingoStripValidationService();
    }

}
