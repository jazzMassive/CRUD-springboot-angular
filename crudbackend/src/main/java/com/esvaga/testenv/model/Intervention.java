package com.esvaga.testenv.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Intervention {

    private String ucInterventionId;
    private String createdOn;
    private String createdBy;
    private String interventionStatus;
    private String vehicleRegNumber;
    private String chassisNumber;
    private String cbkVehicleMakeId;
    private String model;
    private String geoLocationInput;
    private String productServiceName;
    private String serviceStatus;
    private String cbkStatusId;
    private String associateName;
    private String associateType;
    private String associateCategories;
    private String executorFullName;
    private String contactPersonName;
    private String contactPersonPhoneNumber;
    private String identifier;
    private String productName;
    private String salesPartnerName;
    private String callCenterCbkCountryId;

}
