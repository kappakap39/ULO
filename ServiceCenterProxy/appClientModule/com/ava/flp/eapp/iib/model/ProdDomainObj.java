package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.ProdDomainsVect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ProdDomainObj
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class ProdDomainObj   {
  @JsonProperty("prodDomainsVect")
  private ProdDomainsVect prodDomainsVect = null;

  public ProdDomainObj prodDomainsVect(ProdDomainsVect prodDomainsVect) {
    this.prodDomainsVect = prodDomainsVect;
    return this;
  }

   /**
   * Get prodDomainsVect
   * @return prodDomainsVect
  **/
  @ApiModelProperty(value = "")

  @Valid

  public ProdDomainsVect getProdDomainsVect() {
    return prodDomainsVect;
  }

  public void setProdDomainsVect(ProdDomainsVect prodDomainsVect) {
    this.prodDomainsVect = prodDomainsVect;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProdDomainObj prodDomainObj = (ProdDomainObj) o;
    return Objects.equals(this.prodDomainsVect, prodDomainObj.prodDomainsVect);
  }

  @Override
  public int hashCode() {
    return Objects.hash(prodDomainsVect);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProdDomainObj {\n");
    
    sb.append("    prodDomainsVect: ").append(toIndentedString(prodDomainsVect)).append("\n");
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

