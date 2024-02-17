package com.example.newMock1.Controller;

import com.example.newMock1.Model.RequestDTO;
import com.example.newMock1.Model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@RestController

public class MainController {

    private Logger log = LoggerFactory.getLogger(MainController.class);

    ObjectMapper mapper = new ObjectMapper();

    @PostMapping(
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE

    )
    public Object postBalances (@RequestBody RequestDTO requestDTO) {
        try{
            String clientId = requestDTO.getClientId();
            char firstDigit = clientId.charAt(0);
            String RqUID = requestDTO.getRqUID();
            BigDecimal maxLimit;
            BigDecimal balance;
            String currency;

            if(firstDigit == '8') {
                maxLimit = new BigDecimal("2000.00");
                balance = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(0.00, 2000.00))
                        .setScale(2, RoundingMode. HALF_UP);
                currency = "US";
            } else if (firstDigit == '9') {
                maxLimit = new BigDecimal("1000.00");
                balance = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(0.00, 1000.00))
                        .setScale(2, RoundingMode. HALF_UP);
                currency = "EU";
            } else {
                maxLimit = new BigDecimal("10000.00");
                balance = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(0.00, 10000))
                        .setScale(2, RoundingMode. HALF_UP);
                currency = "RUB";
            }

            ResponseDTO responseDTO = new ResponseDTO();

            responseDTO.setRqUID(RqUID);
            responseDTO.setClientId(clientId);
            responseDTO.setAccount(requestDTO.getAccount());
            responseDTO.setCurrency(currency);
            responseDTO.setBalance(balance);
            responseDTO.setMaxLimit(maxLimit);

            log.error("*********** RequestDTO **********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.error("*********** ResponseDTO **********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));

            return responseDTO;

        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
