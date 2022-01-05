package gov.usbr.wq.dataaccess.json;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Public class for events
 */
@ApiModel(description = "Public class for events")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2022-01-05T14:41:42.670-08:00")


public class Event   {
  @JsonProperty("Date")
  private String date = null;

  @JsonProperty("Value")
  private Double value = null;

  @JsonProperty("Quality")
  private Integer quality = null;

  public Event date(String date) {
    this.date = date;
    return this;
  }

  /**
   * Event Date
   * @return date
  **/
  @ApiModelProperty(value = "Event Date")


  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public Event value(Double value) {
    this.value = value;
    return this;
  }

  /**
   * Value
   * @return value
  **/
  @ApiModelProperty(value = "Value")


  public Double getValue() {
    return value;
  }

  public void setValue(Double value) {
    this.value = value;
  }

  public Event quality(Integer quality) {
    this.quality = quality;
    return this;
  }

  /**
   * Event Fail
   * @return quality
  **/
  @ApiModelProperty(value = "Event Fail")


  public Integer getQuality() {
    return quality;
  }

  public void setQuality(Integer quality) {
    this.quality = quality;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Event event = (Event) o;
    return Objects.equals(this.date, event.date) &&
        Objects.equals(this.value, event.value) &&
        Objects.equals(this.quality, event.quality);
  }

  @Override
  public int hashCode() {
    return Objects.hash(date, value, quality);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Event {\n");
    
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    quality: ").append(toIndentedString(quality)).append("\n");
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

