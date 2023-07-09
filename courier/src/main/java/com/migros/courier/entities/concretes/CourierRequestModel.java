package com.migros.courier.entities.concretes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class CourierRequestModel {

    @NotNull
    private List<Courier> courierList;
}
