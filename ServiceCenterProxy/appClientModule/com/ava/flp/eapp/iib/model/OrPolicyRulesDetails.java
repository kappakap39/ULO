package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.Guidelines;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * OrPolicyRulesDetails
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class OrPolicyRulesDetails   {
  @JsonProperty("guidelineCode")
  private String guidelineCode = null;

  @JsonProperty("rank")
  private BigDecimal rank = null;

  @JsonProperty("result")
  private String result = null;

  @JsonProperty("guidelines")
  private List<Guidelines> guidelines = null;

  public OrPolicyRulesDetails guidelineCode(String guidelineCode) {
    this.guidelineCode = guidelineCode;
    return this;
  }

   /**
   * Get guidelineCode
   * @return guidelineCode
  **/
  @ApiModelProperty(value = "")


  public String getGuidelineCode() {
    return guidelineCode;
  }

  public void setGuidelineCode(String guidelineCode) {
    this.guidelineCode = guidelineCode;
  }

  public OrPolicyRulesDetails rank(BigDecimal rank) {
    this.rank = rank;
    return this;
  }

   /**
   * Get rank
   * @return rank
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getRank() {
    return rank;
  }

  public void setRank(BigDecimal rank) {
    this.rank = rank;
  }

  public OrPolicyRulesDetails result(String result) {
    this.result = result;
    return this;
  }

   /**
   * Get result
   * @return result
  **/
  @ApiModelProperty(value = "")


  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public OrPolicyRulesDetails guidelines(List<Guidelines> guidelines) {
    this.guidelines = guidelines;
    return this;
  }

  public OrPolicyRulesDetails addGuidelinesItem(Guidelines guidelinesItem) {
    if (this.guidelines == null) {
      this.guidelines = new ArrayList<Guidelines>();
    }
    this.guidelines.add(guidelinesItem);
    return this;
  }

   /**
   * Get guidelines
   * @return guidelines
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<Guidelines> getGuidelines() {
    return guidelines;
  }

  public void setGuidelines(List<Guidelines> guidelines) {
    this.guidelines = guidelines;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrPolicyRulesDetails orPolicyRulesDetails = (OrPolicyRulesDetails) o;
    return Objects.equals(this.guidelineCode, orPolicyRulesDetails.guidelineCode) &&
        Objects.equals(this.rank, orPolicyRulesDetails.rank) &&
        Objects.equals(this.result, orPolicyRulesDetails.result) &&
        Objects.equals(this.guidelines, orPolicyRulesDetails.guidelines);
  }

  @Override
  public int hashCode() {
    return Objects.hash(guidelineCode, rank, result, guidelines);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrPolicyRulesDetails {\n");
    
    sb.append("    guidelineCode: ").append(toIndentedString(guidelineCode)).append("\n");
    sb.append("    rank: ").append(toIndentedString(rank)).append("\n");
    sb.append("    result: ").append(toIndentedString(result)).append("\n");
    sb.append("    guidelines: ").append(toIndentedString(guidelines)).append("\n");
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

