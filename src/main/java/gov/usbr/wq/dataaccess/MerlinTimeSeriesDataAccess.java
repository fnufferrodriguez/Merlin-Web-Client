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
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toList;

public final class MerlinTimeSeriesDataAccess
{
	private static final Logger LOGGER = Logger.getLogger(MerlinTimeSeriesDataAccess.class.getName());

	private final Supplier<Access> _accessBuilder;

	public MerlinTimeSeriesDataAccess()
	{
		this(HttpAccess::new);
	}

	public MerlinTimeSeriesDataAccess(Supplier<Access> accessBuilder)
	{
		_accessBuilder = accessBuilder;
	}

	public List<ProfileWrapper> getProfiles(TokenContainer token) throws IOException, HttpAccessException
	{
		Access httpAccess = _accessBuilder.get();

		String json = httpAccess.getJsonProfiles(token);
		LOGGER.log(Level.FINEST, () -> "getProfiles() JSON:" + System.lineSeparator() + json);
		return MerlinObjectMapper.mapJsonToListOfObjectsUsingClass(json, Profile.class).stream()
								 .map(ProfileWrapper::new)
								 .collect(toList());
	}

	public List<MeasureWrapper> getMeasurementsByProfile(TokenContainer token, ProfileWrapper profile) throws IOException, HttpAccessException
	{
		Access httpAccess = _accessBuilder.get();
		String json = httpAccess.getJsonMeasurementsByProfileId(token, profile.getDprId());
		LOGGER.log(Level.FINEST, () -> "getMeasurementsByProfile(" + profile + ") JSON:" + System.lineSeparator() + json);
		return MerlinObjectMapper.mapJsonToListOfObjectsUsingClass(json, Measure.class).stream()
								 .map(MeasureWrapper::new)
								 .collect(toList());
	}

	public DataWrapper getEventsBySeries(TokenContainer token, MeasureWrapper measure, Instant start, Instant end) throws IOException, HttpAccessException
	{
		Access httpAccess = _accessBuilder.get();
		String json = httpAccess.getJsonEventsBySeries(token, measure.getSeriesString(), start, end);

		LOGGER.log(Level.FINEST, () -> "getEventsBySeries(" + measure + ", " + "[" + start + ", " + end + "]) JSON:" + System.lineSeparator() + json);

		return new DataWrapper(MerlinObjectMapper.mapJsonToObjectUsingClass(json,Data.class));
	}
}