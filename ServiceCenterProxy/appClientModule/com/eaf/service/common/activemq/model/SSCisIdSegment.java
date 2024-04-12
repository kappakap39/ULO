package com.eaf.service.common.activemq.model;

import java.io.Serializable;

public class SSCisIdSegment implements Serializable {

	private static final long serialVersionUID = -7262471919783699147L;

	private String CisId;

	public String getCisId() {
		return CisId;
	}

	public void setCisId(String cisId) {
		CisId = cisId;
	}

}
