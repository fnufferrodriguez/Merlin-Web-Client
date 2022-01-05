package gov.usbr.wq.dataaccess;

import gov.usbr.wq.dataaccess.http.HttpAccess;
import gov.usbr.wq.dataaccess.http.HttpAccessException;
import gov.usbr.wq.dataaccess.jwt.JwtContainer;
import gov.usbr.wq.dataaccess.model.Data;
import gov.usbr.wq.dataaccess.model.Measure;
import gov.usbr.wq.dataaccess.model.Profile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MerlinTimeSeriesDataAccessTest
{

	private static final Logger LOGGER = Logger.getLogger(MerlinTimeSeriesDataAccessTest.class.getName());

	@Test
	void getProfiles() throws IOException
	{
		String username = ResourceAccess.getUsername();
		String password = ResourceAccess.getPassword();
		JwtContainer token = new HttpAccess(HttpAccess.getDefaultWebServiceRoot()).authenticate(username, password);
		MerlinTimeSeriesDataAccess dataAccess = new MerlinTimeSeriesDataAccess();
		List<Profile> profiles = dataAccess.getProfiles(token);
		Assertions.assertNotNull(profiles);
		LOGGER.info(profiles.toString());
	}

	@Test
	void getMeasurementsByProfile() throws IOException
	{
		String username = ResourceAccess.getUsername();
		String password = ResourceAccess.getPassword();
		JwtContainer token = new HttpAccess(HttpAccess.getDefaultWebServiceRoot()).authenticate(username, password);
		MerlinTimeSeriesDataAccess dataAccess = new MerlinTimeSeriesDataAccess();
		List<Profile> profiles = dataAccess.getProfiles(token);
		Map<Profile, List<Measure>> measures = new HashMap<>();
		for (Profile profile : profiles)
		{
			try
			{
				List<Measure> measurementsByProfile = dataAccess.getMeasurementsByProfile(token, profile);
				measures.put(profile, measurementsByProfile);
			}
			catch (HttpAccessException e)
			{
				LOGGER.log(Level.SEVERE, e, () -> "Error retrieving measurements for profile: " + profile);
			}
		}
		Assertions.assertTrue(!measures.isEmpty());
		measures.values().stream().forEach(e -> Assertions.assertNotNull(e));
		LOGGER.info(measures.toString());
	}

	@Test
	void getEventsBySeries() throws IOException, HttpAccessException
	{
		String username = ResourceAccess.getUsername();
		String password = ResourceAccess.getPassword();
		JwtContainer token = new HttpAccess(HttpAccess.getDefaultWebServiceRoot()).authenticate(username, password);
		MerlinTimeSeriesDataAccess dataAccess = new MerlinTimeSeriesDataAccess();
		List<Profile> profiles = dataAccess.getProfiles(token);
		List<Measure> measurementsByProfile = dataAccess.getMeasurementsByProfile(token, profiles.get(0));
		Measure measure = measurementsByProfile.get(0);
		//		String seriesString = "";
		//		measure.setSeriesString(seriesString);
		Instant start = Instant.parse("2019-01-01T08:00:00.00Z");
		Instant end = Instant.parse("2020-01-01T08:00:00.00Z");
		Data eventsBySeries = dataAccess.getEventsBySeries(token, measure, start, end);
		LOGGER.info(eventsBySeries.toString());
	}

	@Test
	void getEventsBySeriesMultiThread() throws IOException
	{
		String username = ResourceAccess.getUsername();
		String password = ResourceAccess.getPassword();
		JwtContainer token = new HttpAccess(HttpAccess.getDefaultWebServiceRoot()).authenticate(username, password);
		MerlinTimeSeriesDataAccess dataAccess = new MerlinTimeSeriesDataAccess();
		List<Profile> profiles = dataAccess.getProfiles(token);

		Instant start = ZonedDateTime.now().withYear(2019).withDayOfYear(1).withHour(0).withMinute(0).withSecond(0).withNano(0).toInstant();
		Instant end = ZonedDateTime.now().withYear(2019).withDayOfYear(30).withHour(0).withMinute(0).withSecond(0).withNano(0).toInstant();
		List<Pair<Profile, Map<Measure, Data>>> collect = profiles.stream()
			.map((p) -> CompletableFuture.supplyAsync(() -> asyncMeasuresByProfile(dataAccess, token, p)))
			.map((f) -> f.thenApplyAsync((p) -> asyncMeasureListToData(dataAccess, token, p, start, end)))
			.map(f -> f.join())
			.collect(Collectors.toList());
		Assertions.assertTrue(!collect.isEmpty());

		long profileCount = collect.stream()
			.map( Pair::a)
			.filter(Objects::nonNull)
			.count();

		LOGGER.info(() -> "Profile count: "+profileCount);

		long measureCount = collect.stream()
			.map( Pair::b)
			.map(Map::keySet)
			.flatMap(Set::stream)
			.count();
		LOGGER.info(() -> "Measure count: "+measureCount);

		long pointsOfData = collect.stream()
			.map( Pair::b)
			.map(Map::values)
			.flatMap(Collection::stream)
			.filter(Objects::nonNull)
			.map(Data::getEvents)
			.filter(Objects::nonNull)
			.mapToLong(List::size)
			.sum();
		LOGGER.info(() -> "Points of data retrieved: "+pointsOfData);

	}

	private Pair<Profile, List<Measure>> asyncMeasuresByProfile(MerlinTimeSeriesDataAccess dataAccess, JwtContainer token, Profile p)
	{
		List<Measure> retval;
		try
		{
			retval = dataAccess.getMeasurementsByProfile(token, p);
		}
		catch (IOException | HttpAccessException e)
		{
			LOGGER.log(Level.WARNING, e, () -> "Error retrieving measures for profile: " + p);
			retval = Collections.emptyList();
		}
		return new Pair<>(p, retval);
	}

	private Pair<Profile, Map<Measure, Data>> asyncMeasureListToData(MerlinTimeSeriesDataAccess dataAccess, JwtContainer token, Pair<Profile, List<Measure>> p, Instant start, Instant end)
	{
		Map<Measure, Data> collect = p.b()
			.stream()
			.map(m -> CompletableFuture.supplyAsync(() -> asyncDataByMeasure(token, dataAccess, m, start, end)))
			.map(f -> f.join())
			.collect(Collectors.toMap(Pair::a, Pair::b));
		return new Pair<>(p.a(), collect);
	}

	private Pair<Measure, Data> asyncDataByMeasure(JwtContainer token, MerlinTimeSeriesDataAccess dataAccess, Measure measure, Instant start, Instant end)
	{
		Data eventsBySeries;
		try
		{
			eventsBySeries = dataAccess.getEventsBySeries(token, measure, start, end);
		}
		catch (IOException | HttpAccessException e)
		{
			LOGGER.log(Level.WARNING, e, () -> "Error retrieving data for measure: " + measure);
			eventsBySeries = null;
		}
		return new Pair<>(measure, eventsBySeries);
	}

}