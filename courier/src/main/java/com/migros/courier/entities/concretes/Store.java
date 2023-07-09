package com.migros.courier.entities.concretes;

import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Store {
    private String name;
    private double lat;
    private double lng;

}
