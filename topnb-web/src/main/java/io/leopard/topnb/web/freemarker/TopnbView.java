package io.leopard.topnb.web.freemarker;

import io.leopard.topnb.web.freemarker.template.AvgTimeTemplateMethod;
import io.leopard.topnb.web.freemarker.template.BodyTemplateDirective;
import io.leopard.topnb.web.freemarker.template.MenuTemplateDirective;
import io.leopard.topnb.web.freemarker.template.ReplaceParamTemplateMethod;
import io.leopard.topnb.web.freemarker.template.ServerInfoTemplateDirective;
import io.leopard.topnb.web.freemarker.template.TimeTemplateMethod;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TopnbView {
	private static ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<HttpServletRequest>();

	public static HttpServletRequest getRequest() {
		return requestHolder.get();
	}

	public static void setRequest(HttpServletRequest request) {
		requestHolder.set(request);
	}

	private String folder;
	private String templateName;
	protected Map<String, Object> model = new LinkedHashMap<String, Object>();

	public TopnbView(String folder, String templateName) {
		this.folder = folder;
		this.templateName = templateName;
	}

	public TopnbView addObject(String attributeName, Object attributeValue) {
		model.put(attributeName, attributeValue);
		return this;
	}

	public void render(HttpServletRequest request, HttpServletResponse response) throws IOException {
		requestHolder.set(request);

		Configuration config = new Configuration();
		// 设置要解析的模板所在的目录，并加载模板文件
		// config.setDirectoryForTemplateLoading(templateFile);
		config.setTemplateLoader(new ClassTemplateLoader(this.getClass(), folder));

		// 设置包装器，并将对象包装为数据模型
		config.setObjectWrapper(new DefaultObjectWrapper());
		config.setDefaultEncoding("UTF-8");

		Map<String, Object> freemarkerVariables = new HashMap<String, Object>();
		freemarkerVariables.put("xml_escape", "fmXmlEscape");
		freemarkerVariables.put("templateBody", new BodyTemplateDirective());
		freemarkerVariables.put("serverInfo", new ServerInfoTemplateDirective());
		freemarkerVariables.put("menu", new MenuTemplateDirective());

		freemarkerVariables.put("avgTime", new AvgTimeTemplateMethod());
		freemarkerVariables.put("time", new TimeTemplateMethod());
		freemarkerVariables.put("replaceParam", new ReplaceParamTemplateMethod());

		Properties freemarkerSettings = new Properties();
		freemarkerSettings.put("template_update_delay", "1");
		freemarkerSettings.put("defaultEncoding", "UTF-8");

		try {
			config.setSettings(freemarkerSettings);
			config.setAllSharedVariables(new SimpleHash(freemarkerVariables, config.getObjectWrapper()));

		}
		catch (TemplateException e) {
			throw new IOException(e);
		}

		// 获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致
		// 否则会出现乱码
		Template template = config.getTemplate(templateName + ".ftl", Locale.CHINA, "UTF-8");
		template.setCustomAttribute("request", request);
		// Template tmp = (getEncoding() != null ? conf.getTemplate(name, locale, getEncoding()) : conf.getTemplate(name, locale));

		response.setContentType("text/html; charset=UTF-8");
		Writer out = response.getWriter();
		try {
			template.process(model, out);
		}
		catch (TemplateException e) {
			throw new IOException(e);
		}
		out.flush();
		out.close();
	}
}
