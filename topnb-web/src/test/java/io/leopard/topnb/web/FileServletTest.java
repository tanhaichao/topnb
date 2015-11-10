package io.leopard.topnb.web;

import java.io.IOException;

import io.leopard.topnb.web.FileServlet;

import org.junit.Assert;
import org.junit.Test;

public class FileServletTest {

	@Test
	public void isValidFilename() {

		Assert.assertTrue(FileServlet.isValidFilename("test.jpg"));
		Assert.assertTrue(FileServlet.isValidFilename("img/test.jpg"));
		Assert.assertTrue(FileServlet.isValidFilename("img/icons/monitor/ic_ok.png"));
		Assert.assertFalse(FileServlet.isValidFilename("../img/test.jpg"));
	}

	@Test
	public void read() throws IOException {
		byte[] bytes = FileServlet.read("js/plugins/jquery/jquery-ui-1.10.1.custom.min.js");
		String str = new String(bytes);
		System.out.println(str.substring(str.length() - 100));
	}

}