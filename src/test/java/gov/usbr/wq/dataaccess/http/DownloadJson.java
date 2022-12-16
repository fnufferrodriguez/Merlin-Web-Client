/*
 * Copyright 2022 United States Bureau of Reclamation (USBR).
 * United States Department of the Interior
 * All Rights Reserved. USBR PROPRIETARY/CONFIDENTIAL.
 * Source may not be released without written approval
 * from USBR.
 */

package gov.usbr.wq.dataaccess.http;

import gov.usbr.wq.dataaccess.ResourceAccess;
import gov.usbr.wq.dataaccess.jwt.TokenContainer;
import gov.usbr.wq.dataaccess.json.Measure;
import gov.usbr.wq.dataaccess.json.Profile;
import gov.usbr.wq.dataaccess.mapper.MerlinObjectMapper;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Ryan Miles
 */
class DownloadJson
{
	private static final Logger LOGGER = Logger.getLogger(HttpAccessTest.class.getName());
	private static final Path RESOURCES_PATH = Paths.get("").toAbsolutePath().resolve("src/test/resources");
	static final Path PROFILES_JSON_PATH = RESOURCES_PATH.resolve("profiles.json");
	static final Path MEASUREMENTS_JSON_PATH = RESOURCES_PATH.resolve("measurements");
	static final Path EVENTS_JSON_PATH = RESOURCES_PATH.resolve("events");

	public static void main(String[] args) throws Exception
	{
		HttpAccess access = new HttpAccess();
		TokenContainer token = HttpAccessUtils.authenticate(ResourceAccess.getUsername(), ResourceAccess.getPassword());
		String profiles = access.getJsonProfiles(token);
		Path profilesFile = PROFILES_JSON_PATH;
		Files.deleteIfExists(profilesFile);
		Files.write(profilesFile, Collections.singleton(profiles), StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);

		List<Profile> profileList = MerlinObjectMapper.mapJsonToListOfObjectsUsingClass(profiles, Profile.class);

		profileList.forEach(profile -> storeMeasurements(profile, token, access));
	}

	private static void storeMeasurements(Profile profile, TokenContainer token, Access access)
	{
		String measurementsJson;
		try
		{
			measurementsJson = access.getJsonMeasurementsByProfileId(token, profile.getDprID());
			Path measurementPath = MEASUREMENTS_JSON_PATH.resolve(profile.getDprName() + ".json");
			if (!Files.exists(measurementPath))
			{
				Files.createDirectories(MEASUREMENTS_JSON_PATH);
			}
			Files.deleteIfExists(measurementPath);
			Files.write(measurementPath, Collections.singleton(measurementsJson), StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);
		}
		catch(Exception ex)
		{
			LOGGER.log(Level.SEVERE, "Unable to retrieve measurements from profile " + profile.getDprName(), ex);
			return;
		}

		try
		{
			List<Measure> measures = MerlinObjectMapper.mapJsonToListOfObjectsUsingClass(measurementsJson, Measure.class);
			measures.forEach(measure -> storeEvents(measure, profile, token, access));
		}
		catch (Exception ex)
		{
			LOGGER.log(Level.SEVERE, "Unable to parse measurements from json.", ex);
			LOGGER.log(Level.SEVERE, measurementsJson);
		}
	}

	private static void storeEvents(Measure measure, Profile profile, TokenContainer token, Access access)
	{
		try
		{
			String eventsJson = access.getJsonEventsBySeries(token, measure.getSeriesString(), null, null);
			Path eventPath = EVENTS_JSON_PATH.resolve(profile.getDprName());
			if (!Files.exists(eventPath))
			{
				Files.createDirectories(eventPath);
			}
			eventPath = eventPath.resolve(measure.getSeriesString().replace("/", ".") + ".json");
			Files.deleteIfExists(eventPath);
			Files.write(eventPath, Collections.singleton(eventsJson), StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);
		}
		catch (Exception ex)
		{
			LOGGER.log(Level.SEVERE, "Unable to parse json for " + measure.getSeriesString(), ex);
		}
	}
}
