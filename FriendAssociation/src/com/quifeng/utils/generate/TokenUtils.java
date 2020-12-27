package com.quifeng.utils.generate;

import java.util.UUID;

import com.ndktools.javamd5.Mademd5;
import com.quifeng.dao.token.TokenDao;
import com.sun.javafx.fxml.expression.Expression.Parser.Token;

/**
 * @Desc 生成token
 * @author 语录
 *
 */
public class TokenUtils {

	/**
	 * @Desc 生成token
	 * @param phone
	 * @return
	 */
	public static String getToken(String phone){
		Mademd5 md = new Mademd5();
		String data = UUID.randomUUID().toString()+phone;
		return md.toMd5(data);
	}
	
	/**
	 * @Desc 更新 token
	 * @param token
	 */
	public static void updateToken(String token,String phone){
		TokenDao t = new TokenDao();
		t.updateToken(token, getToken(phone));
	}
	
	
	
}
