package com.quifeng.utils.face;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

/**
 * @Desc face存放的路径
 * @author 语录
 *
 */
public class FacePathUtis {

	/**
	 * @Desc 带图片的
	 * @param request
	 * @param file    文件名 后面+/
	 * @return
	 */
	public static String getUUID(HttpServletRequest request, String file) {
		String facePath = request.getSession().getServletContext().getRealPath(file);

		File upload = new File(facePath);
		if (!upload.exists()) {
			upload.mkdir();

		}
		return facePath + UUID.randomUUID().toString() + ".jpeg";
	}

	/**
	 * @Desc 生成基本的
	 * @param request
	 * @param file
	 * @return
	 */
	public static String getPath(HttpServletRequest request, String file) {
		return request.getSession().getServletContext().getRealPath(file);
	}

}
