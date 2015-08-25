package io.leopard.topnb.web.freemarker;

import io.leopard.topnb.web.freemarker.template.BodyTemplateDirective;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TemplateView extends TopnbView {

	public TemplateView(String folder, String templateName) {
		super("/topnb/ftl", "topnb");
		this.addObject("template_folder", folder);
		this.addObject("template_name", templateName);
	}

	public void render(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// model.put("request", request);
		BodyTemplateDirective.setData(model);
		// System.out.println("model:"+model.get("threadInfoList"));
		super.render(request, response);
	}
}
