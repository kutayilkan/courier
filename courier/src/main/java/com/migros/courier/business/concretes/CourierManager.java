package com.migros.courier.business.concretes;

import com.migros.courier.business.abstracts.CourierService;
import com.migros.courier.core.utilities.ProjectHelper;
import com.migros.courier.dataAccess.abstracts.CourierDao;
import com.migros.courier.entities.concretes.Courier;
import com.migros.courier.entities.concretes.Store;
import com.migros.courier.entities.dtos.CourierLocation;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;

@Service
public class CourierManager implements CourierService {

    private final CourierDao courierDao;

    public CourierManager(CourierDao courierDao) {
        this.courierDao = courierDao;
    }


    @Override
    public void processCourierLocation(List<Courier> courierList) throws IOException {
        Collections.sort(courierList, Comparator.comparing(Courier::getCourierId)
                                                .thenComparing(Courier::getTimestamp));

        List<Store> stores = ProjectHelper.storeList();
        for (Courier courier: courierList) {
            for (Store store : stores) {

                double distance = ProjectHelper.convertLatLonToMeters(store.getLat(), store.getLng(), courier.getLat(), courier.getLon());
                if (distance > 100)
                    continue;

                CourierLocation courierLocation = ProjectHelper.processCourierLocationModel(courier, store);

                List<CourierLocation> courierLocationList = courierDao.getByStoreNameAndCourierId(store.getName(), courier.getCourierId());
                if (CollectionUtils.isEmpty(courierLocationList)){
                    courierDao.save(courierLocation);
                    continue;
                }

                Collections.sort(courierLocationList, Comparator.comparing(CourierLocation::getTimestamp));

                CourierLocation courierLocationDB = courierLocationList.get(0);
                if (courierLocationDB.getTimestamp().isAfter(courier.getTimestamp())){
                    courierLocation.setId(courierLocationDB.getId());
                    courierDao.save(courierLocation);
                    continue;
                }

                long seconds = ProjectHelper.diffSec(courierLocationDB.getTimestamp(), courier.getTimestamp());
                if (seconds > 60)
                    continue;
                else if (seconds > 0)
                    courierDao.save(courierLocation);
            }
        }
    }



    @Override
    public double getTotalTravelDistance(Integer courierId) {
        double totalDistance = 0;

        List<CourierLocation> courierLocations = courierDao.getByCourierId(courierId);
        if (CollectionUtils.isEmpty(courierLocations))
            return totalDistance;

        Iterator<CourierLocation> iterator = courierLocations.iterator();
        double lastLat = 0;
        double lastLon = 0;
        while(iterator.hasNext()){
            CourierLocation loc = iterator.next();

            if (lastLat == 0 || lastLon == 0){
                lastLat = loc.getLat();
                lastLon = loc.getLon();
            } else {
                totalDistance = totalDistance + ProjectHelper.convertLatLonToMeters(lastLat, lastLon, loc.getLat(), loc.getLon());
            }

        }

        return totalDistance;
    }

    @Override
    public List<Courier> getAllCourierList() {
        List<CourierLocation> courierLocationList = courierDao.findAll();
        if (CollectionUtils.isEmpty(courierLocationList))
            return null;

        List<Courier> courierList = new ArrayList<>();
        for (CourierLocation loc: courierLocationList) {
            Courier courier = new Courier();
            courier.setCourierId(loc.getCourierId());
            courier.setLon(loc.getLon());
            courier.setLat(loc.getLat());
            courier.setTimestamp(loc.getTimestamp());
            courierList.add(courier);
        }
        return courierList;
    }
}
