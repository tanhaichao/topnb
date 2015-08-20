package io.leopard.topnb.request;

import io.leopard.topnb.TopnbBeanFactory;
import io.leopard.topnb.methodtime.EntryService;
import io.leopard.topnb.web.freemarker.TopnbView;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

@WebListener
public class TopnbRequestServletRequestListener implements ServletRequestListener {

	private static RequestService topService = TopnbBeanFactory.getRequestService();

	private ThreadLocal<Long> START = new ThreadLocal<Long>();

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		// System.err.println("requestInitialized:" + sre);
		START.set(System.nanoTime());

		// methodtime
		HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
		TopnbView.setRequest(request);
		String uri = request.getRequestURI();
		EntryService.add(uri);
	}

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		long start = START.get();
		long time = System.nanoTime() - start;

		HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
		String url = request.getRequestURI();
		// System.out.println("add:" + url + " time:" + time);
		topService.add(url, time);
	}

}
