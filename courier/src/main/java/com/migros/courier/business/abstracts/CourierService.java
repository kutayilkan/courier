package com.migros.courier.business.abstracts;

import com.migros.courier.entities.concretes.Courier;

import java.io.IOException;
import java.util.List;

public interface CourierService {

    void processCourierLocation(List<Courier> courier) throws IOException;

    double getTotalTravelDistance(Integer courierId);

    List<Courier> getAllCourierList();

}
