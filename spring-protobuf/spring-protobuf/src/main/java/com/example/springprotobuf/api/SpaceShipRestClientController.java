package com.example.springprotobuf.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SpaceShipRestClientController {
    private final RestTemplate restTemplate;

    @GetMapping("/api/client")
    public String ship() {
        SpacShipProtos.SpaceShip spaceShip = restTemplate
                .getForObject("http://localhost:8080/api/spaceship",SpacShipProtos.SpaceShip.class);
        log.info("spaceship: captain {}, fuel {}, model {}, cargo {}", spaceShip.getCaptain(),spaceShip.getFuel(),
                spaceShip.getModel(), spaceShip.getCargo());
        return "ship received...";
    }

    @GetMapping("/api/client/bytes")
    public byte[] shipBytes() {
        RequestEntity<byte[]> requestEntity=new RequestEntity<>(null, HttpMethod.GET,
                URI.create("http://localhost:8080/api/spaceship"),byte[].class);
        RestTemplate restTemplateThatDoesntKnowAboutProtobuf = new RestTemplate();
        ResponseEntity<byte[]> exchange=restTemplateThatDoesntKnowAboutProtobuf
                .exchange(requestEntity,byte[].class);
        log.info("spaceship: Number of bytes {}", exchange.getBody().length);
        for(byte b:exchange.getBody()) {
            log.info("Wow a byte :"+b);
        }
        return exchange.getBody();
    }
}
