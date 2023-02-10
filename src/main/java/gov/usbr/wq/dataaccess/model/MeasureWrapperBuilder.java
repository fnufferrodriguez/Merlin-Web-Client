package gov.usbr.wq.dataaccess.model;

import gov.usbr.wq.dataaccess.json.Measure;

public final class MeasureWrapperBuilder
{
    private String _seriesString;
    private boolean _isProcessed;
    private String _typeId;

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

    public MeasureWrapper build()
    {
        Measure measure = new Measure();
        measure.setSeriesString(_seriesString);
        measure.setTypID(_typeId);
        measure.setIsProcessed(_isProcessed);
        return new MeasureWrapper(measure);
    }
}
