package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.CISProdDomain;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ProdDomainsVect
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class ProdDomainsVect   {
  @JsonProperty("CISProdDomain")
  private List<CISProdDomain> ciSProdDomain = null;

  public ProdDomainsVect ciSProdDomain(List<CISProdDomain> ciSProdDomain) {
    this.ciSProdDomain = ciSProdDomain;
    return this;
  }

  public ProdDomainsVect addCiSProdDomainItem(CISProdDomain ciSProdDomainItem) {
    if (this.ciSProdDomain == null) {
      this.ciSProdDomain = new ArrayList<CISProdDomain>();
    }
    this.ciSProdDomain.add(ciSProdDomainItem);
    return this;
  }

   /**
   * Get ciSProdDomain
   * @return ciSProdDomain
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<CISProdDomain> getCiSProdDomain() {
    return ciSProdDomain;
  }

  public void setCiSProdDomain(List<CISProdDomain> ciSProdDomain) {
    this.ciSProdDomain = ciSProdDomain;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProdDomainsVect prodDomainsVect = (ProdDomainsVect) o;
    return Objects.equals(this.ciSProdDomain, prodDomainsVect.ciSProdDomain);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ciSProdDomain);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProdDomainsVect {\n");
    
    sb.append("    ciSProdDomain: ").append(toIndentedString(ciSProdDomain)).append("\n");
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

