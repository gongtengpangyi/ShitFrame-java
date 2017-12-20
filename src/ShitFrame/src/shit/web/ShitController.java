package shit.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求逻辑处理控制器接口。可以实现这个接口，并实现相应的请求响应策略方法，
 * 在每次请求的时候，请求URL非参数部分如"http://loaclhost:8080/Shit/phone/user/login"，
 * 去掉相应项目的根路径，剩余的部分"/phone/user/login"将进行映射，
 * 映射的逻辑是最后一个"/"后的部分映射action，之前的部分映射controller，
 * controller需要在配置文件中申明，具体的配置方式根据你在web.xml中选定的webController,
 * 在实现类中自定义一系列方法，并用@ShitMappingAction注解相应的URL和请求方式。
 * action方法的参数需要注解@ShitMappingParam，注解内容对应请求参数，
 * 如果是需要把request和response传入action中，需要注解@ShitMappingRequest或@ShitMappingResponse，
 * action方法中可以直接用Response做出响应，也可以返回需要返回的内容，
 * 在该controller实现的responseStrategy方法中统一响应。
 * 此外还可以用@ShitMappingBefore，@ShitMappingAfter做头尾的过滤处理
 * 例子：见TestController
 * 
 * @author GongTengPangYi
 *
 */
public interface ShitController {

	/**
	 * 请求统一响应的方法，在请求的最后执行，如果对应的action的返回不是null，则进行这一步操作
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 * @param resultObj
	 *            action方法的返回内容
	 */
	public void responseStrategy(HttpServletRequest request, HttpServletResponse response, Object resultObj) throws ServletException, IOException ;
}
