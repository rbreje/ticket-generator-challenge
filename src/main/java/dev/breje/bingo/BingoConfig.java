package dev.breje.bingo;

import dev.breje.bingo.controller.BingoStripConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BingoConfig {

    @Bean
    @Scope("singleton")
    public BingoStripConverter bingoStripConverter() {
        return new BingoStripConverter();
    }

}
