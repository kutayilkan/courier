package com.migros.courier.controller;

import com.migros.courier.business.abstracts.CourierService;
import com.migros.courier.entities.concretes.Courier;
import com.migros.courier.entities.concretes.CourierRequestModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/courier")
public class CourierController {

    private final CourierService courierService;

    @GetMapping
    public ResponseEntity<List<Courier>> getAll(){
        return ResponseEntity.ok(courierService.getAllCourierList());
    }

    @GetMapping("/{courierId}")
    public ResponseEntity<?> getById(@PathVariable("courierId") Integer courierId){
        List<Courier> courierList = courierService.getAllCourierList();
        if (ObjectUtils.isEmpty(courierList))
            return ResponseEntity.noContent().build();

           return ResponseEntity.ok(courierList.stream()
                .filter(p -> p.getCourierId().equals(courierId)));
    }

    @PostMapping
    public ResponseEntity<String> processCourierLocation(@RequestBody @Valid CourierRequestModel req) throws IOException {
        courierService.processCourierLocation(req.getCourierList());
        return ResponseEntity.ok("Couriers' Locations are Saved!");
    }

    @GetMapping("{courierId}/total-travel-distance")
    public double getTotalTravelDistance(@PathVariable("courierId") Integer courierId){
        return courierService.getTotalTravelDistance(courierId);
    }


    private CourierController(CourierService courierService) {
        super();

        this.courierService = courierService;
    }

}
