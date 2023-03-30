package gov.usbr.wq.dataaccess.model;

import gov.usbr.wq.dataaccess.json.Measure;

import java.time.ZonedDateTime;

public final class MeasureWrapperBuilder
{
    private String _seriesString;
    private boolean _isProcessed;
    private String _typeId;
    private ZonedDateTime _start;
    private ZonedDateTime _end;

    public MeasureWrapperBuilder withSeriesString(String seriesString)
    {
        _seriesString = seriesString;
        return this;
    }

    public MeasureWrapperBuilder withIsProcessed(boolean isProcessed)
    {
        _isProcessed = isProcessed;
        return this;
    }

    public MeasureWrapperBuilder withTypeId(String typeId)
    {
        _typeId = typeId;
        return this;
    }

    public MeasureWrapperBuilder withStart(ZonedDateTime start)
    {
        _start = start;
        return this;
    }

    public MeasureWrapperBuilder withEnd(ZonedDateTime end)
    {
        _end = end;
        return this;
    }

    public MeasureWrapper build()
    {
        Measure measure = new Measure();
        measure.setSeriesString(_seriesString);
        measure.setTypID(_typeId);
        measure.setIsProcessed(_isProcessed);
        if(_start != null)
        {
            measure.setStart(_start.toOffsetDateTime());
        }
        if(_end != null)
        {
            measure.setEnd(_end.toOffsetDateTime());
        }
        return new MeasureWrapper(measure);
    }
}
