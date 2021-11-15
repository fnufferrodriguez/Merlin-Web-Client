package gov.usbr.wq.dataaccess.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Public class for profile measurement series
 */
@ApiModel(description = "Public class for profile measurement series")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-11-15T12:34:03.688-08:00")


public class Measures   {
  @JsonProperty("typID")
  private String typID = null;

  @JsonProperty("seriesString")
  private String seriesString = null;

  public Measures typID(String typID) {
    this.typID = typID;
    return this;
  }

  /**
   * Type ID
   * @return typID
  **/
  @ApiModelProperty(value = "Type ID")


  public String getTypID() {
    return typID;
  }

  public void setTypID(String typID) {
    this.typID = typID;
  }

  public Measures seriesString(String seriesString) {
    this.seriesString = seriesString;
    return this;
  }

  /**
   * Series name
   * @return seriesString
  **/
  @ApiModelProperty(value = "Series name")


  public String getSeriesString() {
    return seriesString;
  }

  public void setSeriesString(String seriesString) {
    this.seriesString = seriesString;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Measures measures = (Measures) o;
    return Objects.equals(this.typID, measures.typID) &&
        Objects.equals(this.seriesString, measures.seriesString);
  }

  @Override
  public int hashCode() {
    return Objects.hash(typID, seriesString);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Measures {\n");
    
    sb.append("    typID: ").append(toIndentedString(typID)).append("\n");
    sb.append("    seriesString: ").append(toIndentedString(seriesString)).append("\n");
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

