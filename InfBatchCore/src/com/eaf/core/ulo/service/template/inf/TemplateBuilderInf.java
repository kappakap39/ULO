package com.eaf.core.ulo.service.template.inf;

import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateResultDataM;
import com.eaf.core.ulo.service.template.model.TemplateMasterDataM;
import com.eaf.core.ulo.service.template.model.TemplateVariableDataM;

public interface TemplateBuilderInf {
	public void init(TemplateBuilderRequest templateBuilderRequest,TemplateMasterDataM templateMaster);
	public TemplateBuilderRequest getTemplateBuilderRequest();
	public TemplateMasterDataM getTemplateMaster();
	public TemplateVariableDataM getTemplateVariable() throws Exception;
	public TemplateResultDataM bulidTemplateResult(TemplateVariableDataM templateVariable);
}
