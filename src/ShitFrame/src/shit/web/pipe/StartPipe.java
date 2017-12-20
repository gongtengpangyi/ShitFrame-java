package shit.web.pipe;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shit.web.entity.ShitMappingAction.Type;
import shit.web.entity.ShitRequest;
import shit.web.exception.ShitWebMappingConfigException;
import shit.web.url.StandardUrlParser;
import shit.web.url.UrlParser;

/**
 * 开始映射的管道
 * @author GongTengPangYi
 *
 */
public class StartPipe implements MappingPipe {

	
	/**
	 * 请求类型
	 */
	private shit.web.entity.ShitMappingAction.Type[] types;

	public StartPipe(Type[] types) {
		super();
		this.types = types;
	}

	@Override
	public void through(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ShitWebMappingConfigException {
		UrlParser urlParser = new StandardUrlParser();
		ShitRequest shitRequest = urlParser.parse(request);
		shitRequest.setTypes(types);
		System.out.println(shitRequest);
		ControllerPipe controllerPipe = new ControllerPipe(shitRequest);
		controllerPipe.through(request, response);
	}

}
