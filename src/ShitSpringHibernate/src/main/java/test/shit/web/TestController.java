package test.shit.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import shit.web.ShitController;
import shit.web.entity.*;
import shit.web.entity.ShitMappingAction.Type;
import shit.web.tool.RequestParamsTool;

public class TestController implements ShitController {

	@Override
	public void responseStrategy(HttpServletRequest request, HttpServletResponse response, Object resultObj)
			throws IOException {
		response.getWriter().println(resultObj.toString());
	}
	
	@ShitMappingAction(name="/login", type=Type.GET_AND_POST)
	public JSONObject login1(@ShitMappingRequest HttpServletRequest request, @ShitMappingResponse HttpServletResponse response,
							 @ShitMappingParam("account") Integer account) throws IOException {
		JSONObject jobj = new JSONObject();
		jobj.put("account", account);
		JSONObject json1 = new JSONObject(), json2 = new JSONObject();
		response.getWriter().println("action");
		String str1 = "111;222";
		String str2 = "xxx;yyy";
		RequestParamsTool.create(request).then(str1, (key, value) -> json1.put(key,value)).then(str2, (key, value) -> json2.put(key, value));
		jobj.put("111", json1);
		jobj.put("222", json2);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return jobj;
	}

	@ShitMappingBefore(url = "/")
	public void doBefore(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.getWriter().println("before");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@ShitMappingAfter(url = "/")
	public void doAfter(HttpServletRequest request, HttpServletResponse response, Object result) {
		if (result instanceof JSONObject) {
			((JSONObject)result).put("xxx", "doAfter");
		}
		try {
			response.getWriter().println("after");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
