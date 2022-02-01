package gov.usbr.wq.dataaccess;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.logging.Logger;

public class Iso8601Support
{
	private static final Logger LOGGER = Logger.getLogger(Iso8601Support.class.getName());

	public static void main(String[] args)
	{
		Instant newYears = ZonedDateTime.of(2022, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")).toInstant();
		ZoneId.getAvailableZoneIds()
			.stream()
			.map(ZoneId::of)
			.sorted((o1, o2) -> -1 * o1.getRules().getOffset(newYears).compareTo(o2.getRules().getOffset(newYears)))
			.map(id -> String.format("%s:%s,%s,%s", hourFormat(id.getRules().getOffset(newYears).getTotalSeconds() / 3600), minuteFormat(Math.abs(id.getRules().getOffset(newYears).getTotalSeconds() % 3600 / 60)),
				id.getId(), id.getRules().isDaylightSavings(newYears)))
			.forEach(s -> System.out.println(s));

	}

	private static String minuteFormat(int i)
	{
		if (i >= 0 && i < 10)
		{
			return "0"+String.format("%d", i);
		}
		else
		{
			return String.format("%2d", i);
		}
	}

	private static String hourFormat(int i)
	{
		if (i <= -10)
		{
			return "-"+String.format("%2d", Math.abs(i));
		}
		else if (i < 0 && i > -10)
		{
			return "-0"+String.format("%d", Math.abs(i));
		}
		else if (i >= 0 && i < 10)
		{
			return "+0"+String.format("%d", i);
		}
		else
		{
			return "+"+String.format("%2d", i);
		}
	}
}
