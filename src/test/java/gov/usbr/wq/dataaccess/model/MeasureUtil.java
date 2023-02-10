package gov.usbr.wq.dataaccess.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import gov.usbr.wq.dataaccess.MerlinTimeSeriesDataAccess;
import gov.usbr.wq.dataaccess.TokenUtil;
import gov.usbr.wq.dataaccess.http.HttpAccessException;
import gov.usbr.wq.dataaccess.json.Measure;
import gov.usbr.wq.dataaccess.jwt.TokenContainer;

public class MeasureUtil
{
	private static final Logger LOGGER = Logger.getLogger(MeasureUtil.class.getName());

	public static void main(String[] args)
	{
		InputStream is = null;
		try
		{
			if(args.length > 0)
			{
				is = new FileInputStream(args[0]);
			}
			else
			{
				is = getResourceInputStream();
			}
			List<MeasureIdWithQuality> measureIdWithQualities = readMeasureIdWithQualities(is);
			TokenContainer token = TokenUtil.getToken();
			ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 4);
			List<Future<String>> futures = new ArrayList<>();
			for(MeasureIdWithQuality measureIdWithQuality : measureIdWithQualities)
			{
				Callable<String> c = new Callable<String>()
				{
					@Override
					public String call() throws Exception
					{
						String retval = "";
						try
						{
							Measure measure = new Measure();
							measure.setSeriesString(measureIdWithQuality.getMeasureId());
							MeasureWrapper measureWrapper = new MeasureWrapper(measure);
							int qualityValueId = measureIdWithQuality.getQualityValueId();
							Instant start = ZonedDateTime.of(2010, 1, 1, 12, 0, 0, 0, ZoneId.of("Etc/GMT+8")).toInstant();
							Instant end = ZonedDateTime.of(2020, 1, 1, 12, 0, 0, 0, ZoneId.of("Etc/GMT+8")).toInstant();
							int maxEventCount = 5;
							boolean eventsFromStartVsEnd = false;
							retval = retrieveJsonSnippetEventsBySeries(token, measureWrapper, qualityValueId, start, end, maxEventCount,
									eventsFromStartVsEnd);
						}
						catch(Throwable t)
						{
							LOGGER.log(Level.SEVERE, "unexpected error", t);
						}
						return retval;
					}
				};
				Future<String> future = executorService.submit(c);
				futures.add(future);
			}

			for (Future<String> future : futures)
			{
				String json = null;
				try
				{
					json = future.get();
					System.out.println(json);
				}
				catch(Exception e)
				{
					LOGGER.log(Level.SEVERE,"Error retrieving events",e);
				}
			}
			executorService.shutdown();
		}
		catch(Throwable t)
		{
			LOGGER.log(Level.SEVERE,"Error",t);
		}

	}

	private static List<MeasureIdWithQuality> readMeasureIdWithQualities(final InputStream inputStream)
	{
		List<String> lines = new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.toList());
		List<MeasureIdWithQuality> measureIdWithQualities = lines.stream()
																 .map(s -> s.trim().split("\\|"))
																 .map(a -> new MeasureIdWithQuality(a[0], a.length > 1 ? Integer.valueOf(a[1]):0))
																 .collect(Collectors.toList());
		return measureIdWithQualities;
	}

	private static InputStream getResourceInputStream() throws IOException
	{
		String filepath = MeasureUtil.class.getPackage().getName().replace(".", "/").concat("/measures.txt");
		URL resource = MeasureUtil.class.getClassLoader().getResource(filepath);
		return resource.openStream();
	}

	private static String retrieveJsonSnippetEventsBySeries(final TokenContainer token, final MeasureWrapper measure, final Integer qualityVersionId,
															final Instant start, final Instant end, int maxEventCount, boolean eventsFromStartVsEnd)
	{
		try
		{
			DataWrapper dataWrapper = new MerlinTimeSeriesDataAccess().getEventsBySeries(token, measure, qualityVersionId, start, end);
			NavigableSet<EventWrapper> events = dataWrapper.getEvents();
			if(!eventsFromStartVsEnd)
			{
				events = events.descendingSet();
			}
			Set<EventWrapper> limitedSet = events.stream().limit(maxEventCount).collect(Collectors.toSet());
			events.clear();
			events.addAll(limitedSet);
			DataWrapper limitedDataWrapper = new DataWrapperBuilder().withDataWrapper(dataWrapper).withEvents(events).build();

			return limitedDataWrapper.toString();
		}
		catch(IOException e)
		{
			throw new RuntimeException(e);
		}
		catch(HttpAccessException e)
		{
			throw new RuntimeException(e);
		}
	}

	private static class MeasureIdWithQuality
	{
		String _measureId;
		int _qualityValueId;

		MeasureIdWithQuality(String measureId, int qualityValueId)
		{
			_measureId = measureId;
			_qualityValueId = qualityValueId;
		}

		public String getMeasureId()
		{
			return _measureId;
		}

		public int getQualityValueId()
		{
			return _qualityValueId;
		}
	}
}
