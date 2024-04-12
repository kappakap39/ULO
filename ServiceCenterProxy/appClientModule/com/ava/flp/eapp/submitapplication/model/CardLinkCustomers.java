package com.ava.flp.eapp.submitapplication.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * CardLinkCustomers
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-21T17:45:58.926+07:00")

public class CardLinkCustomers   {
  @JsonProperty("cardLinkCustomerId")
  private String cardLinkCustomerId = null;

  @JsonProperty("cardLinkCustNumber")
  private String cardLinkCustNumber = null;

  public String getCardLinkCustomerId() {
	return cardLinkCustomerId;
}

public void setCardLinkCustomerId(String cardLinkCustomerId) {
	this.cardLinkCustomerId = cardLinkCustomerId;
}

public String getCardLinkCustNumber() {
	return cardLinkCustNumber;
}

public void setCardLinkCustNumber(String cardLinkCustNumber) {
	this.cardLinkCustNumber = cardLinkCustNumber;
}

@Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CardLinkCustomers cardLinkCustomers = (CardLinkCustomers) o;
    return Objects.equals(this.cardLinkCustomerId, cardLinkCustomers.cardLinkCustomerId) &&
        Objects.equals(this.cardLinkCustNumber, cardLinkCustomers.cardLinkCustNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cardLinkCustomerId, cardLinkCustNumber);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CardLinkCustomers {\n");
    
    sb.append("    cardLinkCustomerId: ").append(toIndentedString(cardLinkCustomerId)).append("\n");
    sb.append("    cardLinkCustNumber: ").append(toIndentedString(cardLinkCustNumber)).append("\n");
    
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

