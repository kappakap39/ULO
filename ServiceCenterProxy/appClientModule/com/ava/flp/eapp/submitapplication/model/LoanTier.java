package com.ava.flp.eapp.submitapplication.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * LoanTier
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-08T13:08:00.000+07:00")

public class LoanTier
{
	@JsonProperty("rateType")
	private String rateType = null;

	@JsonProperty("intRateAmount")
	private BigDecimal intRateAmount = null;
	
	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public BigDecimal getIntRateAmount() {
		return intRateAmount;
	}

	public void setIntRateAmount(BigDecimal intRateAmount) {
		this.intRateAmount = intRateAmount;
	}

@Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LoanTier loanTier = (LoanTier) o;
    return Objects.equals(this.rateType, loanTier.rateType) &&
        Objects.equals(this.intRateAmount, loanTier.intRateAmount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(rateType, intRateAmount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LoanTier {\n");
    
    sb.append("    rateType: ").append(toIndentedString(rateType)).append("\n");
    sb.append("    intRateAmount: ").append(toIndentedString(intRateAmount)).append("\n");
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

