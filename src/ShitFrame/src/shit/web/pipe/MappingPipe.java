package shit.web.pipe;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shit.web.exception.ShitWebMappingConfigException;

/**
 * 请求映射流程的管道接口
 * 
 * @author GongTengPangYi
 *
 */
public interface MappingPipe {

	/**
	 * 管道流动的过程
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 * @throws ServletException
	 * @throws IOException
	 * @throws ShitWebMappingConfigException 
	 */
	void through(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ShitWebMappingConfigException;
}
