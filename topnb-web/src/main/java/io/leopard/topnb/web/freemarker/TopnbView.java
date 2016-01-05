package io.leopard.topnb.web.freemarker;

import java.util.List;

import io.leopard.web.freemarker.TemplateVariable;
import io.leopard.web.freemarker.template.AbstractTemplateView;

public class TopnbView extends AbstractTemplateView {

	public TopnbView(String templateName) {
		super("/topnb/ftl/", templateName);
	}

	@Override
	public List<TemplateVariable> getVariables() {
		return null;
	}
}
