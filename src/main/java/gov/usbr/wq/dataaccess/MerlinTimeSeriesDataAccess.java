package gov.usbr.wq.dataaccess;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.type.TypeReference;
import gov.usbr.wq.dataaccess.model.Measures;
import gov.usbr.wq.dataaccess.model.Profiles;
import jdk.nashorn.internal.runtime.JSONFunctions;
import okhttp3.*;

import java.io.IOException;
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
		List<Profiles> retval = MerlinObjectMapper.mapJsonToObjectUsingTypeReference(json, new TypeReference<List<Profiles>>() { });
		return retval;
	}


	public List<Measures> getMeasurementsByProfile(JwtContainer token, Profiles profile) throws IOException
	{
		HttpAccess httpAccess = new HttpAccess(HttpAccess.getDefaultWebServiceRoot());
		String api = "/MerlinWebService/GetMeasurementsByProfile";
		Integer dprID = profile.getDprID();
		Map<String,String> queryParams = new HashMap<>();
		queryParams.put("profileID", String.valueOf(dprID));
		String json = httpAccess.get(api, token,queryParams);
		Measures[] measures = MerlinObjectMapper.mapJsonToObjectUsingClass(json, Measures[].class);
		List<Measures> retval = MerlinObjectMapper.mapJsonToObjectUsingTypeReference(json, new TypeReference<List<Measures>>(){});
		return null;
	}
}