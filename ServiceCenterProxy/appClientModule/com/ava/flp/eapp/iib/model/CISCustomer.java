package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.ProdDomainObj;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CISCustomer
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class CISCustomer   {
  @JsonProperty("prodDomainObj")
  private ProdDomainObj prodDomainObj = null;

  public CISCustomer prodDomainObj(ProdDomainObj prodDomainObj) {
    this.prodDomainObj = prodDomainObj;
    return this;
  }

   /**
   * Get prodDomainObj
   * @return prodDomainObj
  **/
  @ApiModelProperty(value = "")

  @Valid

  public ProdDomainObj getProdDomainObj() {
    return prodDomainObj;
  }

  public void setProdDomainObj(ProdDomainObj prodDomainObj) {
    this.prodDomainObj = prodDomainObj;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CISCustomer ciSCustomer = (CISCustomer) o;
    return Objects.equals(this.prodDomainObj, ciSCustomer.prodDomainObj);
  }

  @Override
  public int hashCode() {
    return Objects.hash(prodDomainObj);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CISCustomer {\n");
    
    sb.append("    prodDomainObj: ").append(toIndentedString(prodDomainObj)).append("\n");
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

