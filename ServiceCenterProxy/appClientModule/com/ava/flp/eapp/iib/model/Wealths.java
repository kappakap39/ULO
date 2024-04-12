package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import org.joda.time.DateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Wealths
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class Wealths   {
  @JsonProperty("postDate")
  private DateTime postDate = null;

  @JsonProperty("wealthValue")
  private BigDecimal wealthValue = null;

  @JsonProperty("cisNo")
  private String cisNo = null;

  public Wealths postDate(DateTime postDate) {
    this.postDate = postDate;
    return this;
  }

   /**
   * Get postDate
   * @return postDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getPostDate() {
    return postDate;
  }

  public void setPostDate(DateTime postDate) {
    this.postDate = postDate;
  }

  public Wealths wealthValue(BigDecimal wealthValue) {
    this.wealthValue = wealthValue;
    return this;
  }

   /**
   * Get wealthValue
   * @return wealthValue
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getWealthValue() {
    return wealthValue;
  }

  public void setWealthValue(BigDecimal wealthValue) {
    this.wealthValue = wealthValue;
  }

  public Wealths cisNo(String cisNo) {
    this.cisNo = cisNo;
    return this;
  }

   /**
   * Get cisNo
   * @return cisNo
  **/
  @ApiModelProperty(value = "")


  public String getCisNo() {
    return cisNo;
  }

  public void setCisNo(String cisNo) {
    this.cisNo = cisNo;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Wealths wealths = (Wealths) o;
    return Objects.equals(this.postDate, wealths.postDate) &&
        Objects.equals(this.wealthValue, wealths.wealthValue) &&
        Objects.equals(this.cisNo, wealths.cisNo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(postDate, wealthValue, cisNo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Wealths {\n");
    
    sb.append("    postDate: ").append(toIndentedString(postDate)).append("\n");
    sb.append("    wealthValue: ").append(toIndentedString(wealthValue)).append("\n");
    sb.append("    cisNo: ").append(toIndentedString(cisNo)).append("\n");
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

