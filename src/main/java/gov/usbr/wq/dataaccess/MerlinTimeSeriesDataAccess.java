/*
 * Copyright 2022 United States Bureau of Reclamation (USBR).
 * United States Department of the Interior
 * All Rights Reserved. USBR PROPRIETARY/CONFIDENTIAL.
 * Source may not be released without written approval
 * from USBR.
 */

package gov.usbr.wq.dataaccess;

import gov.usbr.wq.dataaccess.http.Access;
import gov.usbr.wq.dataaccess.http.HttpAccessException;
import gov.usbr.wq.dataaccess.http.HttpAccessUtils;
import gov.usbr.wq.dataaccess.json.QualityVersions;
import gov.usbr.wq.dataaccess.json.Template;
import gov.usbr.wq.dataaccess.http.TokenContainer;
import gov.usbr.wq.dataaccess.mapper.MerlinObjectMapper;
import gov.usbr.wq.dataaccess.json.Data;
import gov.usbr.wq.dataaccess.json.Measure;
import gov.usbr.wq.dataaccess.model.DataWrapper;
import gov.usbr.wq.dataaccess.model.MeasureWrapper;
import gov.usbr.wq.dataaccess.model.QualityVersionWrapper;
import gov.usbr.wq.dataaccess.model.TemplateWrapper;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

public final class MerlinTimeSeriesDataAccess
{

	private final String _rootUrl;

	public MerlinTimeSeriesDataAccess(String rootUrl)
	{
		_rootUrl = rootUrl;
	}
	public List<TemplateWrapper> getTemplates(TokenContainer token) throws IOException, HttpAccessException
	{
		Access httpAccess = HttpAccessUtils.buildHttpAccess();
		String json = httpAccess.getJsonTemplates(_rootUrl, token);
		return MerlinObjectMapper.mapJsonToListOfObjectsUsingClass(json, Template.class).stream()
								 .map(TemplateWrapper::new)
								 .collect(toList());
	}

	public List<MeasureWrapper> getMeasurementsByTemplate(TokenContainer token, TemplateWrapper template) throws IOException, HttpAccessException
	{
		Access httpAccess = HttpAccessUtils.buildHttpAccess();
		String json = httpAccess.getJsonMeasurementsByTemplateId(_rootUrl, token, template.getDprId());
		return MerlinObjectMapper.mapJsonToListOfObjectsUsingClass(json, Measure.class).stream()
								 .map(MeasureWrapper::new)
								 .collect(toList());
	}

	public DataWrapper getEventsBySeries(TokenContainer token, MeasureWrapper measure, Integer qualityVersionID, Instant start, Instant end) throws IOException, HttpAccessException
	{
		Access httpAccess = HttpAccessUtils.buildHttpAccess();
		String json = httpAccess.getJsonEventsBySeries(_rootUrl, token, measure.getSeriesString(), qualityVersionID, start, end);
		return new DataWrapper(MerlinObjectMapper.mapJsonToObjectUsingClass(json,Data.class));
	}

	public List<QualityVersionWrapper> getQualityVersions(TokenContainer token) throws HttpAccessException, IOException
	{
		Access httpAccess = HttpAccessUtils.buildHttpAccess();
		String json = httpAccess.getJsonQualityVersions(_rootUrl, token);
		return MerlinObjectMapper.mapJsonToListOfObjectsUsingClass(json, QualityVersions.class).stream()
				.map(QualityVersionWrapper::new)
				.collect(toList());
	}
}
