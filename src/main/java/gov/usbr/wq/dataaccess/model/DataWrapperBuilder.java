package gov.usbr.wq.dataaccess.model;

import gov.usbr.wq.dataaccess.json.Data;
import gov.usbr.wq.dataaccess.json.Event;

import java.util.List;

public final class DataWrapperBuilder
{
    private String _project;
    private String _station;
    private String _latitude;
    private String _longitude;
    private String _sensor;
    private String _measurement;
    private String _timestep;
    private String _dataType;
    private String _duration;
    private String _parameter;
    private String _units;
    private String _timeZone;
    private String _seriesString;
    private List<Event> _events;

    public DataWrapperBuilder withProject(String project)
    {
        _project = project;
        return this;
    }

    public DataWrapperBuilder withStation(String station)
    {
        _station = station;
        return this;
    }

    public DataWrapperBuilder withLatitude(String latitude)
    {
        _latitude = latitude;
        return this;
    }

    public DataWrapperBuilder withLongitude(String longitude)
    {
        _longitude = longitude;
        return this;
    }

    public DataWrapperBuilder withSensor(String sensor)
    {
        _sensor = sensor;
        return this;
    }

    public DataWrapperBuilder withMeasurement(String measurement)
    {
        _measurement = measurement;
        return this;
    }

    public DataWrapperBuilder withTimestep(String timestep)
    {
        _timestep = timestep;
        return this;
    }

    public DataWrapperBuilder withDataType(String dataType)
    {
        _dataType = dataType;
        return this;
    }

    public DataWrapperBuilder withDuration(String duration)
    {
        _duration = duration;
        return this;
    }

    public DataWrapperBuilder withParameter(String parameter)
    {
        _parameter = parameter;
        return this;
    }

    public DataWrapperBuilder withUnits(String units)
    {
        _units = units;
        return this;
    }

    public DataWrapperBuilder withTimeZone(String timeZone)
    {
        _timeZone = timeZone;
        return this;
    }

    public DataWrapperBuilder withSeriesString(String seriesString)
    {
        _seriesString = seriesString;
        return this;
    }

    public DataWrapperBuilder withEvents(List<Event> events)
    {
        _events = events;
        return this;
    }

    public DataWrapper build()
    {
        Data data = new Data();
        data.setParameter(_parameter);
        data.setDataType(_dataType);
        data.setUnits(_units);
        data.setDuration(_duration);
        data.setEvents(_events);
        data.setLatitude(_latitude);
        data.setLongitude(_longitude);
        data.setMeasurement(_measurement);
        data.setSensor(_sensor);
        data.setProject(_project);
        data.setStation(_station);
        data.setSeriesString(_seriesString);
        data.setTimeZone(_timeZone);
        data.setTimestep(_timestep);
        return new DataWrapper(data);
    }

}
