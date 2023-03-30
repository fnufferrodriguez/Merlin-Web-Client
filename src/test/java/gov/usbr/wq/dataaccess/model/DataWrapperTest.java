package gov.usbr.wq.dataaccess.model;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import gov.usbr.wq.dataaccess.MerlinTimeSeriesDataAccess;
import gov.usbr.wq.dataaccess.http.HttpAccessException;
import gov.usbr.wq.dataaccess.http.TokenContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataWrapperTest extends ModelTest
{

	@Test
	void testValidity() throws HttpAccessException, IOException
	{
		TokenContainer token = getToken();
		ZonedDateTime start = ZonedDateTime.parse("2000-01-01T00:00:00-08:00");
		ZonedDateTime end = ZonedDateTime.parse("2020-01-02T00:00:00-08:00");
		MeasureWrapper measureWrapper = new MeasureWrapperBuilder()
				.withSeriesString("MR Sac.-Shasta Lake-Pit R. Branch-Montgomery Creek Flow/Flow/INST-VAL/1440/0/124-230.6.125.1.1")
				.withStart(start)
				.withEnd(end)
				.build();
		Integer qvId = 1;
		MerlinTimeSeriesDataAccess dataAccess = new MerlinTimeSeriesDataAccess();
		DataWrapper dataWrapper = dataAccess.getEventsBySeries(MERLIN_TEST_WEB_CONNECTION_INFO, token, measureWrapper, qvId, start.toInstant(), end.toInstant());
		String json = dataWrapper.toJsonString();
		String expectedJson = readFileAsString("events/Shasta Lake - Modeling Flow and Elev/MR Sac.-Shasta Lake-Pit R. Branch-Montgomery Creek Flow.Flow.INST-VAL.1440.0.124-230.6.125.1.1.json");
		for(int i=0; i< expectedJson.length(); i++)
		{
			assertEquals(json.charAt(i), expectedJson.charAt(i), "Failed at index " + i);
		}
	}
	@Test
	void goodTimeZones()
	{
		String[] timeZones = {
				"GMT",
				"UTC",
				"UTC-08:00",
				"Etc/GMT+8",
				"America/Los_Angeles"
		};

		DataWrapperBuilder builder = new DataWrapperBuilder();
		for (String tz : timeZones)
		{
			DataWrapper dataWrapper = builder.withTimeZone(tz).build();
			Assertions.assertEquals(ZoneId.of(tz),dataWrapper.getTimeZone());
		}
	}

	@Test
	void timeZonesWithSpaces()
	{
		String[] timeZones = {
				"GMT ",
				"UTC -08:00",
				" Etc/GMT+8",
				" America / Los_Angeles "
		};

		DataWrapperBuilder builder = new DataWrapperBuilder();
		for (String tz : timeZones)
		{
			DataWrapper dataWrapper = builder.withTimeZone(tz).build();
			tz = tz.replace(" ", "");
			Assertions.assertEquals(ZoneId.of(tz),dataWrapper.getTimeZone());
		}

	}

	@Test
	void badTimeZones()
	{
		String[] timeZones = {
				"Bert",
				" Ernie ",
				"Cookie Monster",
				"Oscar ",
				" Grover"
		};

		DataWrapperBuilder builder = new DataWrapperBuilder();
		for (String tz : timeZones)
		{
			DataWrapper dataWrapper = builder.withTimeZone(tz).build();
			tz = tz.replace(" ", " ");
			Assertions.assertEquals(ZoneId.of("GMT"),dataWrapper.getTimeZone());
		}

	}
}