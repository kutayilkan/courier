package com.migros.courier.dataAccess.abstracts;

import com.migros.courier.entities.dtos.CourierLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourierDao extends JpaRepository<CourierLocation, Integer> {

    List<CourierLocation> getByStoreNameAndCourierId(String name, Integer courierId);

    List<CourierLocation> getByCourierId(Integer courierId);
}
