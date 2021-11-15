package gov.usbr.wq.dataaccess;

import com.auth0.jwt.interfaces.DecodedJWT;
import gov.usbr.wq.dataaccess.model.Data;
import gov.usbr.wq.dataaccess.model.Measures;
import gov.usbr.wq.dataaccess.model.Profiles;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class MerlinTimeSeriesDataAccessTest
{

	private static final Logger LOGGER = Logger.getLogger(MerlinTimeSeriesDataAccessTest.class.getName());


	@Test
	void getProfiles() throws IOException
	{
		String username="webserviceuser";
		String password= "T3stUser!";
		JwtContainer token = new HttpAccess(HttpAccess.getDefaultWebServiceRoot()).authenticate(username,password);
		MerlinTimeSeriesDataAccess dataAccess = new MerlinTimeSeriesDataAccess();
		List<Profiles> profiles = dataAccess.getProfiles(token);
		Assertions.assertNotNull(profiles);
		LOGGER.info(profiles.toString());
	}

	@Test
	void getMeasurementsByProfile() throws IOException
	{
		String username="webserviceuser";
		String password= "T3stUser!";
		JwtContainer token = new HttpAccess(HttpAccess.getDefaultWebServiceRoot()).authenticate(username,password);
		MerlinTimeSeriesDataAccess dataAccess = new MerlinTimeSeriesDataAccess();
		List<Profiles> profiles = dataAccess.getProfiles(token);
		Map<Profiles,List<Measures>> measures = new HashMap<>();
		for (Profiles profile : profiles)
		{
			List<Measures> measurementsByProfile = dataAccess.getMeasurementsByProfile(token, profile);
			measures.put(profile,measurementsByProfile);
		}
		Assertions.assertTrue(!measures.isEmpty());
		measures.values().stream().forEach(e -> Assertions.assertNotNull(e));
		LOGGER.info(measures.toString());
	}

	@Test
	void getEventsBySeries() throws IOException
	{
		String username="webserviceuser";
		String password= "T3stUser!";
		JwtContainer token = new HttpAccess(HttpAccess.getDefaultWebServiceRoot()).authenticate(username,password);
		MerlinTimeSeriesDataAccess dataAccess = new MerlinTimeSeriesDataAccess();
		List<Profiles> profiles = dataAccess.getProfiles(token);
		List<Measures> measurementsByProfile = dataAccess.getMeasurementsByProfile(token, profiles.get(0));
		Measures measure = measurementsByProfile.get(0);
		Instant start = Instant.parse("2000-01-01T08:00:00.00Z");
		Instant end = Instant.parse("2021-01-01T08:00:00.00Z");
		Data eventsBySeries = dataAccess.getEventsBySeries(token, measure, start, end);
		LOGGER.info(eventsBySeries.toString());
	}
}