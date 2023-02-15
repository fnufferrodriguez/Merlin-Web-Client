/*
 * Copyright 2022 United States Bureau of Reclamation (USBR).
 * United States Department of the Interior
 * All Rights Reserved. USBR PROPRIETARY/CONFIDENTIAL.
 * Source may not be released without written approval
 * from USBR.
 */

package gov.usbr.wq.dataaccess.http;

import gov.usbr.wq.dataaccess.ResourceAccess;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class HttpAccessTest
{
	private static final int SHASTA_PROFILE_ID = 35;  //This is associated with Shasta Lake - Modeling Flow and Elev
	private static final String SHASTA_SERIES_ID = "Shasta Lake-Pit R. Branch-Montgomery Creek Flow/Flow/INST-VAL/1440/0/35-230.6.125.1.1";

	@Test
	void testGetJsonProfiles() throws HttpAccessException
	{
		HttpAccess access = new HttpAccess();
		TokenContainer token = HttpAccessUtils.authenticate(ResourceAccess.getUsername(), ResourceAccess.getPassword());
		String jsonProfiles = access.getJsonTemplates(token);
		//No exceptions should be thrown, the token should be valid
		assertNotNull(jsonProfiles);
	}

	@Test
	void testGetJsonMeasurements() throws HttpAccessException
	{
		HttpAccess access = new HttpAccess();
		TokenContainer token = HttpAccessUtils.authenticate(ResourceAccess.getUsername(), ResourceAccess.getPassword());
		String measurements = access.getJsonMeasurementsByTemplateId(token, SHASTA_PROFILE_ID);
		//No exceptions should be thrown, the token should be valid
		assertNotNull(measurements);
	}

	@Test
	void testGetJsonMeasurements_noData() throws HttpAccessException
	{
		HttpAccess access = new HttpAccess();
		TokenContainer token = HttpAccessUtils.authenticate(ResourceAccess.getUsername(), ResourceAccess.getPassword());
		assertThrowsExactly(HttpAccessException.class, () -> access.getJsonMeasurementsByTemplateId(token, null));
	}

	@Test
	void testGetJsonEvents_NoStartEnd() throws HttpAccessException
	{
		HttpAccess access = new HttpAccess();
		TokenContainer token = HttpAccessUtils.authenticate(ResourceAccess.getUsername(), ResourceAccess.getPassword());
		String measurements = access.getJsonEventsBySeries(token, SHASTA_SERIES_ID, null, null, null);
		//No exceptions should be thrown, the token should be valid
		assertNotNull(measurements);
	}

	@Test
	void testGetJsonEvents_StartNoEnd() throws HttpAccessException
	{
		HttpAccess access = new HttpAccess();
		TokenContainer token = HttpAccessUtils.authenticate(ResourceAccess.getUsername(), ResourceAccess.getPassword());
		Instant start = Instant.now()
							   .minus(7, ChronoUnit.DAYS);
		Instant end = null;
		String measurements = access.getJsonEventsBySeries(token, SHASTA_SERIES_ID, null, start, end);
		//No exceptions should be thrown, the token should be valid
		assertNotNull(measurements);
	}

	@Test
	void testGetJsonEvents_EndNoStart() throws HttpAccessException
	{
		HttpAccess access = new HttpAccess();
		TokenContainer token = HttpAccessUtils.authenticate(ResourceAccess.getUsername(), ResourceAccess.getPassword());
		Instant start = null;
		Instant end = Instant.now();
		String measurements = access.getJsonEventsBySeries(token, SHASTA_SERIES_ID, null, start, end);
		//No exceptions should be thrown, the token should be valid
		assertNotNull(measurements);
	}

	@Test
	void testGetJsonEvents_StartEnd() throws HttpAccessException
	{
		HttpAccess access = new HttpAccess();
		TokenContainer token = HttpAccessUtils.authenticate(ResourceAccess.getUsername(), ResourceAccess.getPassword());
		Instant start = Instant.now()
							   .minus(7, ChronoUnit.DAYS);
		Instant end = Instant.now();
		String measurements = access.getJsonEventsBySeries(token, SHASTA_SERIES_ID, null, start, end);
		//No exceptions should be thrown, the token should be valid
		assertNotNull(measurements);
	}
}