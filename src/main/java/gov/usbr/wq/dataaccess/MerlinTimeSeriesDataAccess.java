package gov.usbr.wq.dataaccess;

import gov.usbr.wq.dataaccess.http.Access;
import gov.usbr.wq.dataaccess.http.HttpAccessException;
import gov.usbr.wq.dataaccess.jwt.TokenContainer;
import gov.usbr.wq.dataaccess.mapper.MerlinObjectMapper;
import gov.usbr.wq.dataaccess.http.HttpAccess;
import gov.usbr.wq.dataaccess.json.Data;
import gov.usbr.wq.dataaccess.json.Measure;
import gov.usbr.wq.dataaccess.json.Profile;
import gov.usbr.wq.dataaccess.model.DataWrapper;
import gov.usbr.wq.dataaccess.model.MeasureWrapper;
import gov.usbr.wq.dataaccess.model.ProfileWrapper;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toList;

public final class MerlinTimeSeriesDataAccess
{
	private static final Logger LOGGER = Logger.getLogger(MerlinTimeSeriesDataAccess.class.getName());

	private final Function<String, Access> _accessBuilder;

	public MerlinTimeSeriesDataAccess()
	{
		this(HttpAccess::new);
	}

	public MerlinTimeSeriesDataAccess(Function<String, Access> accessBuilder)
	{
		_accessBuilder = accessBuilder;
	}

	public List<ProfileWrapper> getProfiles(TokenContainer token) throws IOException
	{
		Access httpAccess = _accessBuilder.apply(HttpAccess.getDefaultWebServiceRoot());
		String api = "/MerlinWebService/GetProfiles";
		String json = httpAccess.get(api, token);
		LOGGER.log(Level.FINEST, "getProfiles() JSON:" + System.lineSeparator() + json);
		return MerlinObjectMapper.mapJsonToListOfObjectsUsingClass(json, Profile.class).stream()
								 .map(ProfileWrapper::new)
								 .collect(toList());
	}

	public List<MeasureWrapper> getMeasurementsByProfile(TokenContainer token, ProfileWrapper profile) throws IOException, HttpAccessException
	{
		Access httpAccess = _accessBuilder.apply(HttpAccess.getDefaultWebServiceRoot());
		String api = "/MerlinWebService/GetMeasurementsByProfile";
		Integer dprID = profile.getDprId();
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put("profileID", String.valueOf(dprID));
		String json = httpAccess.get(api, token, queryParams);
		LOGGER.log(Level.FINEST, "getMeasurementsByProfile(" + profile + ") JSON:" + System.lineSeparator() + json);
		return MerlinObjectMapper.mapJsonToListOfObjectsUsingClass(json, Measure.class).stream()
								 .map(MeasureWrapper::new)
								 .collect(toList());
	}

	public DataWrapper getEventsBySeries(TokenContainer token, MeasureWrapper measure, Instant start, Instant end) throws IOException, HttpAccessException
	{
		Access httpAccess = _accessBuilder.apply(HttpAccess.getDefaultWebServiceRoot());
		String api = "/MerlinWebService/GetEventsBySeriesString";
		String seriesString = measure.getSeriesString();
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put("seriesString", seriesString);
		if (start != null)
		{
			queryParams.put("startDate", start.toString());
		}
		if (end != null)
		{
			queryParams.put("endDate", end.toString());
		}
		String json = httpAccess.get(api, token, queryParams);

		LOGGER.log(Level.FINEST, "getEventsBySeries(" + measure + ", " + "[" + start + ", " + end + "]) JSON:" + System.lineSeparator() + json);

		return new DataWrapper(MerlinObjectMapper.mapJsonToObjectUsingClass(json,Data.class));
	}
}