package gov.usbr.wq.dataaccess.model;


import gov.usbr.wq.dataaccess.json.Event;

import java.time.OffsetDateTime;

public final class EventWrapperBuilder
{
    private OffsetDateTime _date;
    private Double _value;
    private Integer _quality;

    public void withDate(OffsetDateTime date)
    {
        _date = date;
    }

    public void withValue(Double value)
    {
        _value = value;
    }

    public void withQuality(Integer quality)
    {
        _quality = quality;
    }

    public EventWrapper build()
    {
        Event event = new Event();
        event.setDate(_date);
        event.setQuality(_quality);
        event.setValue(_value);
        return new EventWrapper(event);
    }
}
