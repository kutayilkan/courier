package com.migros.courier.controller;

import com.migros.courier.business.abstracts.CourierService;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("stat")
public class StatusController {
    @GetMapping
    public ResponseEntity<String> status(){
        return ResponseEntity.ok("OK");
    }


}
