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
 * Public class to define Data Profiles
 */
@ApiModel(description = "Public class to define Data Profiles")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2022-01-05T14:41:42.670-08:00")


public class Profile   {
  @JsonProperty("dprID")
  private Integer dprID = null;

  @JsonProperty("dprName")
  private String dprName = null;

  public Profile dprID(Integer dprID) {
    this.dprID = dprID;
    return this;
  }

  /**
   * Profile ID
   * @return dprID
  **/
  @ApiModelProperty(value = "Profile ID")


  public Integer getDprID() {
    return dprID;
  }

  public void setDprID(Integer dprID) {
    this.dprID = dprID;
  }

  public Profile dprName(String dprName) {
    this.dprName = dprName;
    return this;
  }

  /**
   * Profile Name
   * @return dprName
  **/
  @ApiModelProperty(value = "Profile Name")


  public String getDprName() {
    return dprName;
  }

  public void setDprName(String dprName) {
    this.dprName = dprName;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Profile profile = (Profile) o;
    return Objects.equals(this.dprID, profile.dprID) &&
        Objects.equals(this.dprName, profile.dprName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dprID, dprName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Profile {\n");
    
    sb.append("    dprID: ").append(toIndentedString(dprID)).append("\n");
    sb.append("    dprName: ").append(toIndentedString(dprName)).append("\n");
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

