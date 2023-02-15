/*
 * Copyright 2022 United States Bureau of Reclamation (USBR).
 * United States Department of the Interior
 * All Rights Reserved. USBR PROPRIETARY/CONFIDENTIAL.
 * Source may not be released without written approval
 * from USBR.
 */

package gov.usbr.wq.dataaccess;

import gov.usbr.wq.dataaccess.http.HttpAccessException;
import gov.usbr.wq.dataaccess.http.HttpAccessUtils;
import gov.usbr.wq.dataaccess.json.Data;
import gov.usbr.wq.dataaccess.http.TokenContainer;
import gov.usbr.wq.dataaccess.model.DataWrapper;
import gov.usbr.wq.dataaccess.model.MeasureWrapper;
import gov.usbr.wq.dataaccess.model.QualityVersionWrapper;
import gov.usbr.wq.dataaccess.model.TemplateWrapper;
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

import static org.junit.jupiter.api.Assertions.*;

class MerlinTimeSeriesDataAccessTest
{

	private static final String WEB_SERVICE_ROOT = "https://www.grabdata2.com";
	private static final Logger LOGGER = Logger.getLogger(MerlinTimeSeriesDataAccessTest.class.getName());
	private static final List<Integer> INTERNAL_SERVER_ERROR_DPR_LIST = Arrays.asList(54);

	@Test
	void getTemplates() throws IOException, HttpAccessException
	{
		String username = ResourceAccess.getUsername();
		char[] password = ResourceAccess.getPassword();
		TokenContainer token = HttpAccessUtils.authenticate(WEB_SERVICE_ROOT, username, password);
		MerlinTimeSeriesDataAccess dataAccess = new MerlinTimeSeriesDataAccess(WEB_SERVICE_ROOT);
		List<TemplateWrapper> templates = dataAccess.getTemplates(token);
		assertNotNull(templates);
		LOGGER.info(templates.toString());
	}

	@Test
	void getMeasurementsByTemplate() throws IOException, HttpAccessException
	{
		String username = ResourceAccess.getUsername();
		char[] password = ResourceAccess.getPassword();
		TokenContainer token = HttpAccessUtils.authenticate(WEB_SERVICE_ROOT, username, password);
		MerlinTimeSeriesDataAccess dataAccess = new MerlinTimeSeriesDataAccess(WEB_SERVICE_ROOT);
		List<TemplateWrapper> templates = dataAccess.getTemplates(token);
		Map<TemplateWrapper, List<MeasureWrapper>> measures = new HashMap<>();
		for (TemplateWrapper template : templates)
		{
			try
			{
				List<MeasureWrapper> measurementsByTemplate = dataAccess.getMeasurementsByTemplate(token, template);
				measures.put(template, measurementsByTemplate);
			}
			catch (HttpAccessException e)
			{
				LOGGER.log(Level.SEVERE, e, () -> "Error retrieving measurements for template: " + template);
			}
		}
		Assertions.assertTrue(!measures.isEmpty());
		measures.values().stream().forEach(e -> assertNotNull(e));
		LOGGER.info(measures.toString());
	}

	@Test
	void getEventsBySeries() throws IOException, HttpAccessException
	{
		String username = ResourceAccess.getUsername();
		char[] password = ResourceAccess.getPassword();
		TokenContainer token = HttpAccessUtils.authenticate(WEB_SERVICE_ROOT, username, password);
		MerlinTimeSeriesDataAccess dataAccess = new MerlinTimeSeriesDataAccess(WEB_SERVICE_ROOT);
		List<TemplateWrapper> templates = dataAccess.getTemplates(token);
		List<MeasureWrapper> measurementsByTemplate = dataAccess.getMeasurementsByTemplate(token, templates.get(2));
		MeasureWrapper measure = measurementsByTemplate.get(0);
		Instant start = Instant.parse("2016-01-01T08:00:00.00Z");
		Instant end = Instant.parse("2020-01-01T08:00:00.00Z");
		DataWrapper eventsBySeries = dataAccess.getEventsBySeries(token, measure, null, start, end);
		assertNotNull(eventsBySeries, "Failed to retrieve events by series");
		LOGGER.info(eventsBySeries.toString());
	}

	@Test
	void getEventsBySeriesWithQualityVersion() throws IOException, HttpAccessException
	{
		String username = ResourceAccess.getUsername();
		char[] password = ResourceAccess.getPassword();
		TokenContainer token = HttpAccessUtils.authenticate(WEB_SERVICE_ROOT, username, password);
		MerlinTimeSeriesDataAccess dataAccess = new MerlinTimeSeriesDataAccess(WEB_SERVICE_ROOT);
		List<TemplateWrapper> templates = dataAccess.getTemplates(token);
		List<MeasureWrapper> measurementsByTemplate = dataAccess.getMeasurementsByTemplate(token, templates.get(2));
		MeasureWrapper measure = measurementsByTemplate.get(0);
		Instant start = Instant.parse("2016-01-01T08:00:00.00Z");
		Instant end = Instant.parse("2020-01-01T08:00:00.00Z");
		List<QualityVersionWrapper> qvs = dataAccess.getQualityVersions(token);
		for(QualityVersionWrapper qv : qvs)
		{
			DataWrapper eventsBySeries = dataAccess.getEventsBySeries(token, measure, qv.getQualityVersionID(), start, end);
			assertNotNull(eventsBySeries, "Failed to retrieve events by series with QualityVersion: " + qv.getQualityVersionID());
			LOGGER.info(eventsBySeries.toString());
		}
	}

	@Test
	void getQualityVersions() throws HttpAccessException
	{
		String username = ResourceAccess.getUsername();
		char[] password = ResourceAccess.getPassword();
		TokenContainer token = HttpAccessUtils.authenticate(WEB_SERVICE_ROOT, username, password);
		MerlinTimeSeriesDataAccess dataAccess = new MerlinTimeSeriesDataAccess(WEB_SERVICE_ROOT);
		List<QualityVersionWrapper> qualityVersions = assertDoesNotThrow(() -> dataAccess.getQualityVersions(token), "Failed to retrieve quality versions");
		assertNotNull(qualityVersions, "Failed to retrieve Quality Versions");
		LOGGER.info(qualityVersions.toString());
	}

	@Test
	void getEventsBySeriesMultiThread() throws IOException, HttpAccessException
	{
		String username = ResourceAccess.getUsername();
		char[] password = ResourceAccess.getPassword();
		TokenContainer token = HttpAccessUtils.authenticate(WEB_SERVICE_ROOT, username, password);
		MerlinTimeSeriesDataAccess dataAccess = new MerlinTimeSeriesDataAccess(WEB_SERVICE_ROOT);
		List<TemplateWrapper> templates = dataAccess.getTemplates(token);

		Instant start = ZonedDateTime.now().withYear(2016).withMonth(1).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0).toInstant();
		Instant end = ZonedDateTime.now().withYear(2017).withMonth(12).withDayOfMonth(30).withHour(0).withMinute(0).withSecond(0).withNano(0).toInstant();
		List<Pair<TemplateWrapper, Map<MeasureWrapper, DataWrapper>>> collect = templates.stream()
			.map((p) -> CompletableFuture.supplyAsync(() -> asyncMeasuresByTemplate(dataAccess, token, p)))
			.map((f) -> f.thenApplyAsync((p) -> asyncMeasureListToData(dataAccess, token, p, start, end)))
			.map(f -> f.join())
			.collect(Collectors.toList());
		Assertions.assertTrue(!collect.isEmpty());

		long templateCount = collect.stream()
			.map( Pair::a)
			.filter(Objects::nonNull)
			.count();

		LOGGER.info(() -> "Template count: "+templateCount);

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
			.map(DataWrapper::getEvents)
			.filter(Objects::nonNull)
			.mapToLong(Collection::size)
			.sum();
		LOGGER.info(() -> "Points of data retrieved: "+pointsOfData);

	}

	@Test
	void getEventsBySeriesWithQualityVersionMultiThread() throws IOException, HttpAccessException
	{
		String username = ResourceAccess.getUsername();
		char[] password = ResourceAccess.getPassword();
		TokenContainer token = HttpAccessUtils.authenticate(WEB_SERVICE_ROOT, username, password);
		MerlinTimeSeriesDataAccess dataAccess = new MerlinTimeSeriesDataAccess(WEB_SERVICE_ROOT);
		List<TemplateWrapper> templates = dataAccess.getTemplates(token);
		int qvID = 0;
		Instant start = ZonedDateTime.now().withYear(2016).withMonth(1).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0).toInstant();
		Instant end = ZonedDateTime.now().withYear(2017).withMonth(12).withDayOfMonth(30).withHour(0).withMinute(0).withSecond(0).withNano(0).toInstant();
		List<Pair<TemplateWrapper, Map<MeasureWrapper, DataWrapper>>> collect = templates.stream()
				.map((p) -> CompletableFuture.supplyAsync(() -> asyncMeasuresByTemplate(dataAccess, token, p)))
				.map((f) -> f.thenApplyAsync((p) -> asyncMeasureListToDataWithQualityVersion(dataAccess, token, p, qvID, start, end)))
				.map(f -> f.join())
				.collect(Collectors.toList());
		Assertions.assertTrue(!collect.isEmpty());

		long templateCount = collect.stream()
				.map( Pair::a)
				.filter(Objects::nonNull)
				.count();

		LOGGER.info(() -> "Template count: "+templateCount);

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
				.map(DataWrapper::getEvents)
				.filter(Objects::nonNull)
				.mapToLong(Collection::size)
				.sum();
		LOGGER.info(() -> "Points of data retrieved: "+pointsOfData);

	}

	private Pair<TemplateWrapper, List<MeasureWrapper>> asyncMeasuresByTemplate(MerlinTimeSeriesDataAccess dataAccess, TokenContainer token, TemplateWrapper p)
	{
		List<MeasureWrapper> retval = Collections.emptyList();
		try
		{
			if(!INTERNAL_SERVER_ERROR_DPR_LIST.contains(p.getDprId())) //temp until issue can be resolved with the dpr id, appears to be issue on server end, but isn't important for purposes of this unit test
			{
				retval = dataAccess.getMeasurementsByTemplate(token, p);
			}
		}
		catch (IOException | HttpAccessException e)
		{
			LOGGER.log(Level.WARNING, e, () -> "Error retrieving measures for template: " + p);
		}
		return new Pair<>(p, retval);
	}

	private Pair<TemplateWrapper, Map<MeasureWrapper, DataWrapper>> asyncMeasureListToData(MerlinTimeSeriesDataAccess dataAccess, TokenContainer token, Pair<TemplateWrapper, List<MeasureWrapper>> p, Instant start, Instant end)
	{
		Map<MeasureWrapper, DataWrapper> collect = p.b()
			.stream()
			.map(m -> CompletableFuture.supplyAsync(() -> asyncDataByMeasure(token, dataAccess, m, start, end)))
			.map(CompletableFuture::join)
			.collect(Collectors.toMap(Pair::a, Pair::b));
		return new Pair<>(p.a(), collect);
	}

	private Pair<TemplateWrapper, Map<MeasureWrapper, DataWrapper>> asyncMeasureListToDataWithQualityVersion(MerlinTimeSeriesDataAccess dataAccess, TokenContainer token, Pair<TemplateWrapper, List<MeasureWrapper>> p, Integer qvID, Instant start, Instant end)
	{
		Map<MeasureWrapper, DataWrapper> collect = p.b()
				.stream()
				.map(m -> CompletableFuture.supplyAsync(() -> asyncDataByMeasureWithQualityVersion(token, dataAccess, m, qvID, start, end)))
				.map(f -> f.join())
				.collect(Collectors.toMap(Pair::a, Pair::b));
		return new Pair<>(p.a(), collect);
	}

	private Pair<MeasureWrapper, DataWrapper> asyncDataByMeasure(TokenContainer token, MerlinTimeSeriesDataAccess dataAccess, MeasureWrapper measure, Instant start, Instant end)
	{
		DataWrapper eventsBySeries;
		try
		{
			eventsBySeries = dataAccess.getEventsBySeries(token, measure, null, start, end);
		}
		catch (IOException | HttpAccessException e)
		{
			eventsBySeries =  new DataWrapper(new Data());
			//ignore internal server error 500 for now in case server is failing
			if(!(e instanceof HttpAccessException) || (((HttpAccessException)e).getCode() != 403 && ((HttpAccessException)e).getCode() != 500)) //ignore 403 error which seems to be a special case where test user doesn't have permissions, as that is out of scope of this unit test
			{
				eventsBySeries = null;
				LOGGER.log(Level.WARNING, e, () -> "Error retrieving data for measure: " + measure);
			}
		}
		return new Pair<>(measure, eventsBySeries);
	}

	private Pair<MeasureWrapper, DataWrapper> asyncDataByMeasureWithQualityVersion(TokenContainer token, MerlinTimeSeriesDataAccess dataAccess, MeasureWrapper measure, Integer qvID, Instant start, Instant end)
	{
		DataWrapper eventsBySeries;
		try
		{
			eventsBySeries = dataAccess.getEventsBySeries(token, measure, qvID, start, end);
		}
		catch (IOException | HttpAccessException e)
		{
			eventsBySeries =  new DataWrapper(new Data());
			if(!(e instanceof HttpAccessException) || (((HttpAccessException)e).getCode() != 403 && ((HttpAccessException)e).getCode() != 500)) //ignore 403 error which seems to be a special case where test user doesn't have permissions in some case
			{
				eventsBySeries = null;
				LOGGER.log(Level.WARNING, e, () -> "Error retrieving data for measure: " + measure);
			}
		}
		return new Pair<>(measure, eventsBySeries);
	}

}