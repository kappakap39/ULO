package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.CISCustomer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Cis0368i01Response
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class Cis0368i01Response   {
  @JsonProperty("CISCustomer")
  private CISCustomer ciSCustomer = null;

  public Cis0368i01Response ciSCustomer(CISCustomer ciSCustomer) {
    this.ciSCustomer = ciSCustomer;
    return this;
  }

   /**
   * Get ciSCustomer
   * @return ciSCustomer
  **/
  @ApiModelProperty(value = "")

  @Valid

  public CISCustomer getCiSCustomer() {
    return ciSCustomer;
  }

  public void setCiSCustomer(CISCustomer ciSCustomer) {
    this.ciSCustomer = ciSCustomer;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Cis0368i01Response cis0368i01Response = (Cis0368i01Response) o;
    return Objects.equals(this.ciSCustomer, cis0368i01Response.ciSCustomer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ciSCustomer);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Cis0368i01Response {\n");
    
    sb.append("    ciSCustomer: ").append(toIndentedString(ciSCustomer)).append("\n");
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

