package gov.usbr.wq.dataaccess;

import com.fasterxml.jackson.core.type.TypeReference;
import gov.usbr.wq.dataaccess.model.Data;
import gov.usbr.wq.dataaccess.model.Measures;
import gov.usbr.wq.dataaccess.model.Profiles;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public final class MerlinTimeSeriesDataAccess
{
	private static final Logger LOGGER = Logger.getLogger(MerlinTimeSeriesDataAccess.class.getName());

	public MerlinTimeSeriesDataAccess()
	{
	}

	public List<Profiles> getProfiles(JwtContainer token) throws IOException
	{
		HttpAccess httpAccess = new HttpAccess(HttpAccess.getDefaultWebServiceRoot());
		String api = "/MerlinWebService/GetProfiles";
		String json = httpAccess.get(api, token);
		List<Profiles> retval = MerlinObjectMapper.mapJsonToObjectUsingTypeReference(json, new TypeReference<List<Profiles>>()
		{
		});
		return retval;
	}

	public List<Measures> getMeasurementsByProfile(JwtContainer token, Profiles profile) throws IOException
	{
		HttpAccess httpAccess = new HttpAccess(HttpAccess.getDefaultWebServiceRoot());
		String api = "/MerlinWebService/GetMeasurementsByProfile";
		Integer dprID = profile.getDprID();
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put("profileID", String.valueOf(dprID));
		String json = httpAccess.get(api, token, queryParams);
		return MerlinObjectMapper.mapJsonToObjectUsingTypeReference(json, new TypeReference<List<Measures>>()
		{
		});
	}

	public Data getEventsBySeries(JwtContainer token, Measures measure, Instant start, Instant end) throws IOException
	{
		HttpAccess httpAccess = new HttpAccess(HttpAccess.getDefaultWebServiceRoot());
		String api = "/MerlinWebService/GetEventsBySeriesString";
		String seriesString = measure.getSeriesString();
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put("seriesString", seriesString);
		queryParams.put("startDate", start.toString());
		queryParams.put("endDate", end.toString());
		String json = httpAccess.get(api, token, queryParams);
		return MerlinObjectMapper.mapJsonToObjectUsingTypeReference(json, new TypeReference<Data>()
		{
		});
	}
}