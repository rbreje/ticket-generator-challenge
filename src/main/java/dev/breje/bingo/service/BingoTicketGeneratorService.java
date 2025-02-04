package dev.breje.bingo.service;

import dev.breje.bingo.model.BingoTicket;
import org.springframework.stereotype.Service;

@Service
public class BingoTicketGeneratorService {
    
    public BingoTicket generateTicket() {
        BingoTicket result = new BingoTicket();
        
        return result;
    }
    
}
