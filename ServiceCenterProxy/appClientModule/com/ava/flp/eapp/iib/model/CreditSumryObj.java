package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.joda.time.DateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CreditSumryObj
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class CreditSumryObj   {
  @JsonProperty("lastRstruDate")
  private DateTime lastRstruDate = null;

  public CreditSumryObj lastRstruDate(DateTime lastRstruDate) {
    this.lastRstruDate = lastRstruDate;
    return this;
  }

   /**
   * Get lastRstruDate
   * @return lastRstruDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getLastRstruDate() {
    return lastRstruDate;
  }

  public void setLastRstruDate(DateTime lastRstruDate) {
    this.lastRstruDate = lastRstruDate;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreditSumryObj creditSumryObj = (CreditSumryObj) o;
    return Objects.equals(this.lastRstruDate, creditSumryObj.lastRstruDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lastRstruDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreditSumryObj {\n");
    
    sb.append("    lastRstruDate: ").append(toIndentedString(lastRstruDate)).append("\n");
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

