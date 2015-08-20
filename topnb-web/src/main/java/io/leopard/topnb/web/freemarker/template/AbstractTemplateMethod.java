package io.leopard.topnb.web.freemarker.template;

import io.leopard.topnb.web.freemarker.TopnbView;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import freemarker.template.SimpleDate;
import freemarker.template.SimpleNumber;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public abstract class AbstractTemplateMethod implements TemplateMethodModelEx {
	@Override
	public Object exec(@SuppressWarnings("rawtypes") List arguments) throws TemplateModelException {
		// arguments.toArray();
		Object[] args = new Object[arguments.size()];
		int i = 0;
		for (Object obj : arguments) {
			if (obj != null) {
				if (obj instanceof SimpleDate) {
					args[i] = ((SimpleDate) obj).getAsDate();
				}
				else if (obj instanceof SimpleNumber) {
					args[i] = ((SimpleNumber) obj).getAsNumber();
				}
				else if (obj instanceof SimpleNumber) {
					args[i] = ((SimpleNumber) obj).getAsNumber();
				}
				else if (obj instanceof SimpleScalar) {
					args[i] = ((SimpleScalar) obj).toString();
				}
				else {
					System.err.println("obj:" + obj.getClass());
					args[i] = obj.toString();
				}
			}
			i++;
		}
		HttpServletRequest request = TopnbView.getRequest();

		try {
			return this.exec(request, args);
		}
		catch (TemplateModelException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TemplateModelException(e);
		}
	}

	public abstract Object exec(HttpServletRequest request, Object... args) throws TemplateModelException;

}
