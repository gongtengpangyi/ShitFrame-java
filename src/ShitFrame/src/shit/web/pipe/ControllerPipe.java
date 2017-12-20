package shit.web.pipe;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shit.web.ShitController;
import shit.web.ShitWebInitListener;
import shit.web.entity.ShitRequest;
import shit.web.exception.ShitWebMappingConfigException;
import shit.web.loader.WebControllerLoader;

/**
 * 查找controller的管道
 * @author GongTengPangYi
 *
 */
public class ControllerPipe implements MappingPipe {
	/**
	 * 请求
	 */
	private ShitRequest shitRequest;
	
	public ControllerPipe(ShitRequest shitRequest) {
		super();
		this.shitRequest = shitRequest;
	}

	@Override
	public void through(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ShitWebMappingConfigException {
		WebControllerLoader webControllerLoader = ShitWebInitListener.webControllerLoader;
		if (webControllerLoader != null && shitRequest != null) {
			ShitController shitController = webControllerLoader.load(shitRequest.getControllerName());
			ActionPipe actionPipe = new ActionPipe(shitController, shitRequest);
			actionPipe.through(request, response);
		}

	}

}
