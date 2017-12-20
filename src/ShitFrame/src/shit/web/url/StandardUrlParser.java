package shit.web.url;

import javax.servlet.http.HttpServletRequest;

import shit.web.entity.ShitRequest;

public class StandardUrlParser implements UrlParser {

	@Override
	public ShitRequest parse(HttpServletRequest request) {
		ShitRequest shitRequest = new ShitRequest();
		String uri = request.getRequestURI().toString();
		String contextPath = request.getContextPath();
		String realPath = uri.indexOf(contextPath) == -1 ? uri : uri.replace(contextPath, "");
		int index = realPath.lastIndexOf("/");
		shitRequest.setControllerName(realPath.substring(0, index));
		shitRequest.setActionName(realPath.substring(index));
		return shitRequest;
	}

}
