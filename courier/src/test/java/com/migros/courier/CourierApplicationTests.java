package com.migros.courier;

import com.migros.courier.business.concretes.CourierManager;
import com.migros.courier.dataAccess.abstracts.CourierDao;
import com.migros.courier.entities.concretes.Courier;
import com.migros.courier.entities.concretes.Store;
import com.migros.courier.entities.dtos.CourierLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
class CourierApplicationTests {

	private CourierDao courierDao;
	private CourierManager courierManager;

	@Test
	void contextLoads() {
	}

	@BeforeEach
	public void setup() {
		courierDao = Mockito.mock(CourierDao.class);
		courierManager = new CourierManager(courierDao);
	}

	@Test
	public void testProcessCourierLocation() throws IOException {
		// Mock data
		Courier courier1 = new Courier(1, LocalDateTime.now(), 0, 0);
		Courier courier2 = new Courier(2, LocalDateTime.now(), 1, 1);
		List<Courier> courierList = Arrays.asList(courier1, courier2);

		Store store1 = new Store("Novada MMM Migros", 0, 0);
		Store store2 = new Store("Ataşehir MMM Migros", 1, 1);
		List<Store> stores = Arrays.asList(store1, store2);

		// Mocked method calls
		when(courierDao.getByStoreNameAndCourierId(eq("Store1"), eq(1))).thenReturn(new ArrayList<>());
		when(courierDao.getByStoreNameAndCourierId(eq("Ataşehir MMM Migros"), eq(1))).thenReturn(Arrays.asList(
				new CourierLocation(1, 1, "Novada MMM Migros", LocalDateTime.now().minusMinutes(10), 40.986106, 29.1161293),
				new CourierLocation(2, 1, "Ataşehir MMM Migros", LocalDateTime.now().minusMinutes(5), 40.9923307, 29.1244229)
		));

		// Execute the method
		courierManager.processCourierLocation(courierList);

		// Verify method calls and assertions
		//verify(courierDao, times(2)).getByStoreNameAndCourierId(anyString(), anyInt());
		//verify(courierDao, times(1)).save(any(CourierLocation.class));

		// Add assertions here based on your expected behavior
		// For example:
		// verify(courierDao).save(any(CourierLocation.class));
	}

	@Test
	public void testGetTotalTravelDistance() {
		// Mock data
		Integer courierId = 1;
		List<CourierLocation> courierLocations = Arrays.asList(
				new CourierLocation(1, 1, "Novada MMM Migros", LocalDateTime.now().minusMinutes(10), 40.986106, 29.1161293),
				new CourierLocation(2, 1, "Ataşehir MMM Migros", LocalDateTime.now().minusMinutes(5), 40.9923307, 29.1244229)
		);

		// Mocked method call
		when(courierDao.getByCourierId(eq(courierId))).thenReturn(courierLocations);

		// Execute the method
		double totalDistance = courierManager.getTotalTravelDistance(courierId);

		// Verify method call and assertions
		verify(courierDao).getByCourierId(eq(courierId));

		// Add assertions here based on your expected behavior
		// For example:
		// assertEquals(expectedValue, totalDistance);
	}

	@Test
	public void testGetAllCourierList() {
		// Mock data
		List<CourierLocation> courierLocationList = Arrays.asList(
				new CourierLocation(1, 1, "Novada MMM Migros", LocalDateTime.now().minusMinutes(10), 40.986106, 29.1161293),
				new CourierLocation(2, 1, "Ataşehir MMM Migros", LocalDateTime.now().minusMinutes(5), 40.9923307, 29.1244229)
		);

		// Mocked method call
		when(courierDao.findAll()).thenReturn(courierLocationList);

		// Execute the method
		List<Courier> courierList = courierManager.getAllCourierList();

		// Verify method call and assertions
		verify(courierDao).findAll();

		// Add assertions here based on your expected behavior
		// For example:
		// assertEquals(expectedListSize, courierList.size());
	}

}
