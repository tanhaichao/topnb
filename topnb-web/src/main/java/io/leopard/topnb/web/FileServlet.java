package io.leopard.topnb.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 性能监控数据
 * 
 * @author 阿海
 */
@WebServlet(name = "topnbFileServlet", urlPatterns = "/topnb/file.leo")
public class FileServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public FileServlet() {
		System.err.println("new FileServlet.");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filename = request.getParameter("f");
		if (!isValidFilename(filename)) {
			throw new IllegalArgumentException("非法文件名[" + filename + "].");
		}
		String contentType = parseContentType(filename);
		byte[] bytes = read(filename);
		response.setContentType(contentType);
		response.setContentLength(bytes.length);

		response.setDateHeader("Expires", System.currentTimeMillis() + 1000 * 3600 * 24);
		OutputStream out = response.getOutputStream();
		out.write(bytes);
		out.flush();
	}

	protected static byte[] read(String filename) throws IOException {
		String path = "/topnb/htdocs/" + filename;
		InputStream input = FileServlet.class.getResourceAsStream(path);
		if (input == null) {
			throw new NullPointerException("文件[" + filename + "]不存在.");
		}
		byte[] buffer = new byte[1024];
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		int n = 0;
		while ((n = input.read(buffer)) != -1) {
			output.write(buffer, 0, n);
		}
		return output.toByteArray();
	}

	private static Pattern FILENAME_PATTERN = Pattern.compile("^[a-zA-Z0-9\\-_/\\.]+\\.(css|jpg|png|js)$");

	/**
	 * 文件名称合法性判断.
	 * 
	 * @param filename
	 * @return
	 */
	protected static boolean isValidFilename(String filename) {
		if (filename == null || filename.length() == 0) {
			throw new NullPointerException("文件名不能为空.");
		}
		if (filename.indexOf("..") != -1) {
			throw new IllegalArgumentException("文件名称不能包含'..'");
		}
		Matcher m = FILENAME_PATTERN.matcher(filename);
		return m.matches();
	}

	protected static String parseContentType(String filename) {
		if (filename.endsWith(".css")) {
			return "text/css";
		}
		else if (filename.endsWith(".png")) {
			return "image/png";
		}
		else if (filename.endsWith(".jpg")) {
			return "image/jpg";
		}
		else if (filename.endsWith(".js")) {
			return "application/javascript";
		}
		throw new IllegalArgumentException("未知文件类型[" + filename + "].");
	}

}
