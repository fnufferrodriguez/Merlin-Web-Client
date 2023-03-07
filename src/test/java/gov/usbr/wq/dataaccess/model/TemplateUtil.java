package gov.usbr.wq.dataaccess.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import gov.usbr.wq.dataaccess.MerlinTimeSeriesDataAccess;
import gov.usbr.wq.dataaccess.TokenUtil;
import gov.usbr.wq.dataaccess.http.ApiConnectionInfo;
import gov.usbr.wq.dataaccess.http.HttpAccessException;
import gov.usbr.wq.dataaccess.http.TokenContainer;

public class TemplateUtil
{
	private static final Logger LOGGER = Logger.getLogger(TemplateUtil.class.getName());

	public static void main(String[] args)
	{
		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 4);
		Runnable r = new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					List<String> rows = logParameters(executorService);
					for(String row : rows)
					{
						System.out.println(row);
					}
					executorService.shutdown();
				}
				catch(Throwable t)
				{
					LOGGER.log(Level.SEVERE, "unexpected error", t);
				}

			}
		};
		executorService.submit(r);
	}

	private static List<String> logParameters(final ExecutorService executorService)
			throws HttpAccessException, ExecutionException, InterruptedException
	{
		TokenContainer token = TokenUtil.getToken();
		List<TemplateWrapper> templateWrappers = retrieveTemplates(token);
		final List<String> rows = new ArrayList<>();
		rows.add("Template ID|Template|Measure|Parameter|Interval|Processed");
		List<Future> futures = new ArrayList<>();
		for(TemplateWrapper template : templateWrappers)
		{
			Runnable runnable = new Runnable()
			{
				@Override
				public void run()
				{
					List<MeasureWrapper> measureWrappers = retrieveMeasurements(token, template);
					for(MeasureWrapper measure : measureWrappers)
					{
						rows.add(createCsvRow(template, measure));
					}
				}
			};
			Future<?> future = executorService.submit(runnable);
			futures.add(future);
		}
		for(Future future : futures)
		{
			future.get();
		}
		return rows;
	}

	private static List<TemplateWrapper> retrieveTemplates(final TokenContainer token)
	{
		try
		{
			ApiConnectionInfo connectionInfo = new ApiConnectionInfo("https://www.grabdata2.com");
			return new MerlinTimeSeriesDataAccess().getTemplates(connectionInfo, token);
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

	private static List<MeasureWrapper> retrieveMeasurements(TokenContainer token, TemplateWrapper template)
	{
		try
		{
			ApiConnectionInfo connectionInfo = new ApiConnectionInfo("https://www.grabdata2.com");
			return new MerlinTimeSeriesDataAccess().getMeasurementsByTemplate(connectionInfo, token, template);
		}
		catch(IOException e)
		{
			throw new RuntimeException(e);
		}
		catch(HttpAccessException e)
		{
			//ignore bad 500 error code on server end until they fix it
			if(!(e instanceof HttpAccessException) && ((HttpAccessException) e).getResponseCode() != 500)
			{
				throw new RuntimeException(e);
			}
		}
		return Collections.emptyList();
	}

	private static String createCsvRow(TemplateWrapper template, MeasureWrapper measure)
	{
		String row = template.getDprId() + "|" + template.getName() + "|" + measure.getSeriesString() + "|"
				+ measure.getParameter() + "|" + measure.getTimeStep() + "|" + measure.isProcessed() + "|"
				+ measure.getStart() + "|" + measure.getEnd();
		return row;
	}

}
