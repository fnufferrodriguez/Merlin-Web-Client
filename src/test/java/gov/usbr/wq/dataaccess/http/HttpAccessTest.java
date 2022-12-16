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
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import static org.junit.jupiter.api.Assertions.*;

class HttpAccessTest
{
	private static final int SHASTA_PROFILE_ID = 35;  //This is associated with Shasta Lake - Modeling Flow and Elev
	private static final String SHASTA_SERIES_ID = "Shasta Lake-Pit R. Branch-Montgomery Creek Flow/Flow/INST-VAL/1440/0/35-230.6.125.1.1";

	@Test
	void testGetJsonProfiles() throws IOException, HttpAccessException
	{
		HttpAccess access = new HttpAccess();
		TokenContainer token = HttpAccessUtils.authenticate(ResourceAccess.getUsername(), ResourceAccess.getPassword());
		String jsonProfiles = access.getJsonProfiles(token);
		//No exceptions should be thrown, the token should be valid
		assertNotNull(jsonProfiles);
	}

	@Test
	void testGetJsonMeasurements() throws IOException, HttpAccessException
	{
		HttpAccess access = new HttpAccess();
		TokenContainer token = HttpAccessUtils.authenticate(ResourceAccess.getUsername(), ResourceAccess.getPassword());
		String measurements = access.getJsonMeasurementsByProfileId(token, SHASTA_PROFILE_ID);
		//No exceptions should be thrown, the token should be valid
		assertNotNull(measurements);
	}

	@Test
	void testGetJsonMeasurements_noData() throws IOException
	{
		HttpAccess access = new HttpAccess();
		TokenContainer token = HttpAccessUtils.authenticate(ResourceAccess.getUsername(), ResourceAccess.getPassword());
		assertThrowsExactly(HttpAccessException.class, () -> access.getJsonMeasurementsByProfileId(token, null));
	}

	@Test
	void testGetJsonEvents_NoStartEnd() throws IOException, HttpAccessException
	{
		HttpAccess access = new HttpAccess();
		TokenContainer token = HttpAccessUtils.authenticate(ResourceAccess.getUsername(), ResourceAccess.getPassword());
		String measurements = access.getJsonEventsBySeries(token, SHASTA_SERIES_ID, null, null);
		//No exceptions should be thrown, the token should be valid
		assertNotNull(measurements);
	}

	@Test
	void testGetJsonEvents_StartNoEnd() throws IOException, HttpAccessException
	{
		HttpAccess access = new HttpAccess();
		TokenContainer token = HttpAccessUtils.authenticate(ResourceAccess.getUsername(), ResourceAccess.getPassword());
		Instant start = Instant.now()
							   .minus(7, ChronoUnit.DAYS);
		Instant end = null;
		String measurements = access.getJsonEventsBySeries(token, SHASTA_SERIES_ID, start, end);
		//No exceptions should be thrown, the token should be valid
		assertNotNull(measurements);
	}

	@Test
	void testGetJsonEvents_EndNoStart() throws IOException, HttpAccessException
	{
		HttpAccess access = new HttpAccess();
		TokenContainer token = HttpAccessUtils.authenticate(ResourceAccess.getUsername(), ResourceAccess.getPassword());
		Instant start = null;
		Instant end = Instant.now();
		String measurements = access.getJsonEventsBySeries(token, SHASTA_SERIES_ID, start, end);
		//No exceptions should be thrown, the token should be valid
		assertNotNull(measurements);
	}

	@Test
	void testGetJsonEvents_StartEnd() throws IOException, HttpAccessException
	{
		HttpAccess access = new HttpAccess();
		TokenContainer token = HttpAccessUtils.authenticate(ResourceAccess.getUsername(), ResourceAccess.getPassword());
		Instant start = Instant.now()
							   .minus(7, ChronoUnit.DAYS);
		Instant end = Instant.now();
		String measurements = access.getJsonEventsBySeries(token, SHASTA_SERIES_ID, start, end);
		//No exceptions should be thrown, the token should be valid
		assertNotNull(measurements);
	}
}