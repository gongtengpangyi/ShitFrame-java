package shit.web.url;

import javax.servlet.http.HttpServletRequest;

import shit.web.entity.ShitRequest;

/**
 * URL解析器，用于解析URL，将整段的URL拆分为对应的controller名和action名
 * 
 * @author GongTengPangYi
 *
 */
public interface UrlParser {

	/**
	 * 解析当前请求的URL并生成对应的ShitRequest
	 * 
	 * @param request
	 *            请求
	 * @return 处理的结果
	 */
	public ShitRequest parse(HttpServletRequest request);
}
