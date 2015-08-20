package io.leopard.topnb.web;

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

}