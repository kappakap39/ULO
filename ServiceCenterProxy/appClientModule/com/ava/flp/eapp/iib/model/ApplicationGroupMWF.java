package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.ApplicationsWF;
import com.ava.flp.eapp.iib.model.PersonalInfosWF;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ApplicationGroupMWF
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class ApplicationGroupMWF   {
  @JsonProperty("appNo")
  private String appNo = null;

  @JsonProperty("appDate")
  private DateTime appDate = null;

  @JsonProperty("personalInfos")
  private List<PersonalInfosWF> personalInfos = null;

  @JsonProperty("applications")
  private List<ApplicationsWF> applications = null;

  public ApplicationGroupMWF appNo(String appNo) {
    this.appNo = appNo;
    return this;
  }

   /**
   * Get appNo
   * @return appNo
  **/
  @ApiModelProperty(value = "")


  public String getAppNo() {
    return appNo;
  }

  public void setAppNo(String appNo) {
    this.appNo = appNo;
  }

  public ApplicationGroupMWF appDate(DateTime appDate) {
    this.appDate = appDate;
    return this;
  }

   /**
   * Get appDate
   * @return appDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getAppDate() {
    return appDate;
  }

  public void setAppDate(DateTime appDate) {
    this.appDate = appDate;
  }

  public ApplicationGroupMWF personalInfos(List<PersonalInfosWF> personalInfos) {
    this.personalInfos = personalInfos;
    return this;
  }

  public ApplicationGroupMWF addPersonalInfosItem(PersonalInfosWF personalInfosItem) {
    if (this.personalInfos == null) {
      this.personalInfos = new ArrayList<PersonalInfosWF>();
    }
    this.personalInfos.add(personalInfosItem);
    return this;
  }

   /**
   * Get personalInfos
   * @return personalInfos
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<PersonalInfosWF> getPersonalInfos() {
    return personalInfos;
  }

  public void setPersonalInfos(List<PersonalInfosWF> personalInfos) {
    this.personalInfos = personalInfos;
  }

  public ApplicationGroupMWF applications(List<ApplicationsWF> applications) {
    this.applications = applications;
    return this;
  }

  public ApplicationGroupMWF addApplicationsItem(ApplicationsWF applicationsItem) {
    if (this.applications == null) {
      this.applications = new ArrayList<ApplicationsWF>();
    }
    this.applications.add(applicationsItem);
    return this;
  }

   /**
   * Get applications
   * @return applications
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<ApplicationsWF> getApplications() {
    return applications;
  }

  public void setApplications(List<ApplicationsWF> applications) {
    this.applications = applications;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApplicationGroupMWF applicationGroupMWF = (ApplicationGroupMWF) o;
    return Objects.equals(this.appNo, applicationGroupMWF.appNo) &&
        Objects.equals(this.appDate, applicationGroupMWF.appDate) &&
        Objects.equals(this.personalInfos, applicationGroupMWF.personalInfos) &&
        Objects.equals(this.applications, applicationGroupMWF.applications);
  }

  @Override
  public int hashCode() {
    return Objects.hash(appNo, appDate, personalInfos, applications);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApplicationGroupMWF {\n");
    
    sb.append("    appNo: ").append(toIndentedString(appNo)).append("\n");
    sb.append("    appDate: ").append(toIndentedString(appDate)).append("\n");
    sb.append("    personalInfos: ").append(toIndentedString(personalInfos)).append("\n");
    sb.append("    applications: ").append(toIndentedString(applications)).append("\n");
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

