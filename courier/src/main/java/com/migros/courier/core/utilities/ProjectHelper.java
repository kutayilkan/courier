package com.migros.courier.core.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.migros.courier.entities.concretes.Courier;
import com.migros.courier.entities.concretes.Store;
import com.migros.courier.entities.dtos.CourierLocation;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;

public class ProjectHelper {

    public static List<Store> storeList() throws IOException {
        String storeJson = "[{\"name\":\"Ataşehir MMM Migros\",\"lat\":40.9923307,\"lng\":29.1244229},{\"name\":\"Novada MMM Migros\",\"lat\":40.986106,\"lng\":29.1161293},{\"name\":\"Beylikdüzü 5M Migros\",\"lat\":41.0066851,\"lng\":28.6552262},{\"name\":\"Ortaköy MMM Migros\",\"lat\":41.055783,\"lng\":29.0210292},{\"name\":\"Caddebostan MMM Migros\",\"lat\":40.9632463,\"lng\":29.0630908}]";
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(storeJson, new TypeReference<List<Store>>() {});
    }

    public static Double convertLatLonToMeters(double lat1, double lon1, double lat2, double lon2){
        int r = 6371; //  Earth radius --> km

        double dLat = Math.toRadians(lat2 - lat1); // lat diff
        double dLon = Math.toRadians(lon2 - lon1); // lon diff
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = r * c * 1000; //  to Meters

        return distance;
    }

    public static long diffSec(LocalDateTime time1, LocalDateTime time2) throws IOException {
        return ChronoUnit.SECONDS.between(time1, time2);
    }

    public static CourierLocation processCourierLocationModel(Courier courier, Store store) {
        CourierLocation courierLocation = new CourierLocation();

        courierLocation.setCourierId(courier.getCourierId());
        courierLocation.setLat(courier.getLat());
        courierLocation.setLon(courier.getLon());
        courierLocation.setTimestamp(courier.getTimestamp());
        courierLocation.setStoreName(store.getName());

        return courierLocation;
    }
}
