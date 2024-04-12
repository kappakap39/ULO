package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Taweesaps
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class Taweesaps   {
  @JsonProperty("aum")
  private BigDecimal aum = null;

  @JsonProperty("tweesapId")
  private String tweesapId = null;

  public Taweesaps aum(BigDecimal aum) {
    this.aum = aum;
    return this;
  }

   /**
   * Get aum
   * @return aum
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getAum() {
    return aum;
  }

  public void setAum(BigDecimal aum) {
    this.aum = aum;
  }

  public Taweesaps tweesapId(String tweesapId) {
    this.tweesapId = tweesapId;
    return this;
  }

   /**
   * Get tweesapId
   * @return tweesapId
  **/
  @ApiModelProperty(value = "")


  public String getTweesapId() {
    return tweesapId;
  }

  public void setTweesapId(String tweesapId) {
    this.tweesapId = tweesapId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Taweesaps taweesaps = (Taweesaps) o;
    return Objects.equals(this.aum, taweesaps.aum) &&
        Objects.equals(this.tweesapId, taweesaps.tweesapId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(aum, tweesapId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Taweesaps {\n");
    
    sb.append("    aum: ").append(toIndentedString(aum)).append("\n");
    sb.append("    tweesapId: ").append(toIndentedString(tweesapId)).append("\n");
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

