package gov.usbr.wq.dataaccess.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import gov.usbr.wq.dataaccess.model.Events;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Data
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-11-15T12:34:03.688-08:00")


public class Data   {
  @JsonProperty("Project")
  private String project = null;

  @JsonProperty("Station")
  private String station = null;

  @JsonProperty("Latitude")
  private String latitude = null;

  @JsonProperty("Longitude")
  private String longitude = null;

  @JsonProperty("Sensor")
  private String sensor = null;

  @JsonProperty("Measurement")
  private String measurement = null;

  @JsonProperty("Timestep")
  private String timestep = null;

  @JsonProperty("DataType")
  private String dataType = null;

  @JsonProperty("Duration")
  private String duration = null;

  @JsonProperty("Parameter")
  private String parameter = null;

  @JsonProperty("Units")
  private String units = null;

  @JsonProperty("TimeZone")
  private String timeZone = null;

  @JsonProperty("SeriesString")
  private String seriesString = null;

  @JsonProperty("Events")
  @Valid
  private List<Events> events = null;

  public Data project(String project) {
    this.project = project;
    return this;
  }

  /**
   * Get project
   * @return project
  **/
  @ApiModelProperty(value = "")


  public String getProject() {
    return project;
  }

  public void setProject(String project) {
    this.project = project;
  }

  public Data station(String station) {
    this.station = station;
    return this;
  }

  /**
   * Get station
   * @return station
  **/
  @ApiModelProperty(value = "")


  public String getStation() {
    return station;
  }

  public void setStation(String station) {
    this.station = station;
  }

  public Data latitude(String latitude) {
    this.latitude = latitude;
    return this;
  }

  /**
   * Get latitude
   * @return latitude
  **/
  @ApiModelProperty(value = "")


  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public Data longitude(String longitude) {
    this.longitude = longitude;
    return this;
  }

  /**
   * Get longitude
   * @return longitude
  **/
  @ApiModelProperty(value = "")


  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public Data sensor(String sensor) {
    this.sensor = sensor;
    return this;
  }

  /**
   * Get sensor
   * @return sensor
  **/
  @ApiModelProperty(value = "")


  public String getSensor() {
    return sensor;
  }

  public void setSensor(String sensor) {
    this.sensor = sensor;
  }

  public Data measurement(String measurement) {
    this.measurement = measurement;
    return this;
  }

  /**
   * Get measurement
   * @return measurement
  **/
  @ApiModelProperty(value = "")


  public String getMeasurement() {
    return measurement;
  }

  public void setMeasurement(String measurement) {
    this.measurement = measurement;
  }

  public Data timestep(String timestep) {
    this.timestep = timestep;
    return this;
  }

  /**
   * Get timestep
   * @return timestep
  **/
  @ApiModelProperty(value = "")


  public String getTimestep() {
    return timestep;
  }

  public void setTimestep(String timestep) {
    this.timestep = timestep;
  }

  public Data dataType(String dataType) {
    this.dataType = dataType;
    return this;
  }

  /**
   * Get dataType
   * @return dataType
  **/
  @ApiModelProperty(value = "")


  public String getDataType() {
    return dataType;
  }

  public void setDataType(String dataType) {
    this.dataType = dataType;
  }

  public Data duration(String duration) {
    this.duration = duration;
    return this;
  }

  /**
   * Get duration
   * @return duration
  **/
  @ApiModelProperty(value = "")


  public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  public Data parameter(String parameter) {
    this.parameter = parameter;
    return this;
  }

  /**
   * Get parameter
   * @return parameter
  **/
  @ApiModelProperty(value = "")


  public String getParameter() {
    return parameter;
  }

  public void setParameter(String parameter) {
    this.parameter = parameter;
  }

  public Data units(String units) {
    this.units = units;
    return this;
  }

  /**
   * Get units
   * @return units
  **/
  @ApiModelProperty(value = "")


  public String getUnits() {
    return units;
  }

  public void setUnits(String units) {
    this.units = units;
  }

  public Data timeZone(String timeZone) {
    this.timeZone = timeZone;
    return this;
  }

  /**
   * Get timeZone
   * @return timeZone
  **/
  @ApiModelProperty(value = "")


  public String getTimeZone() {
    return timeZone;
  }

  public void setTimeZone(String timeZone) {
    this.timeZone = timeZone;
  }

  public Data seriesString(String seriesString) {
    this.seriesString = seriesString;
    return this;
  }

  /**
   * Get seriesString
   * @return seriesString
  **/
  @ApiModelProperty(value = "")


  public String getSeriesString() {
    return seriesString;
  }

  public void setSeriesString(String seriesString) {
    this.seriesString = seriesString;
  }

  public Data events(List<Events> events) {
    this.events = events;
    return this;
  }

  public Data addEventsItem(Events eventsItem) {
    if (this.events == null) {
      this.events = new ArrayList<>();
    }
    this.events.add(eventsItem);
    return this;
  }

  /**
   * Get events
   * @return events
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<Events> getEvents() {
    return events;
  }

  public void setEvents(List<Events> events) {
    this.events = events;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Data data = (Data) o;
    return Objects.equals(this.project, data.project) &&
        Objects.equals(this.station, data.station) &&
        Objects.equals(this.latitude, data.latitude) &&
        Objects.equals(this.longitude, data.longitude) &&
        Objects.equals(this.sensor, data.sensor) &&
        Objects.equals(this.measurement, data.measurement) &&
        Objects.equals(this.timestep, data.timestep) &&
        Objects.equals(this.dataType, data.dataType) &&
        Objects.equals(this.duration, data.duration) &&
        Objects.equals(this.parameter, data.parameter) &&
        Objects.equals(this.units, data.units) &&
        Objects.equals(this.timeZone, data.timeZone) &&
        Objects.equals(this.seriesString, data.seriesString) &&
        Objects.equals(this.events, data.events);
  }

  @Override
  public int hashCode() {
    return Objects.hash(project, station, latitude, longitude, sensor, measurement, timestep, dataType, duration, parameter, units, timeZone, seriesString, events);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Data {\n");
    
    sb.append("    project: ").append(toIndentedString(project)).append("\n");
    sb.append("    station: ").append(toIndentedString(station)).append("\n");
    sb.append("    latitude: ").append(toIndentedString(latitude)).append("\n");
    sb.append("    longitude: ").append(toIndentedString(longitude)).append("\n");
    sb.append("    sensor: ").append(toIndentedString(sensor)).append("\n");
    sb.append("    measurement: ").append(toIndentedString(measurement)).append("\n");
    sb.append("    timestep: ").append(toIndentedString(timestep)).append("\n");
    sb.append("    dataType: ").append(toIndentedString(dataType)).append("\n");
    sb.append("    duration: ").append(toIndentedString(duration)).append("\n");
    sb.append("    parameter: ").append(toIndentedString(parameter)).append("\n");
    sb.append("    units: ").append(toIndentedString(units)).append("\n");
    sb.append("    timeZone: ").append(toIndentedString(timeZone)).append("\n");
    sb.append("    seriesString: ").append(toIndentedString(seriesString)).append("\n");
    sb.append("    events: ").append(toIndentedString(events)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

