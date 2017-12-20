package shit.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shit.web.entity.ShitMappingAction.Type;
import shit.web.exception.ShitWebMappingConfigException;
import shit.web.pipe.StartPipe;

/**
 * Servlet implementation class ShitWebMappingServlet
 */
public class ShitWebMappingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/**
	 * 默认捕捉器
	 */
	private static final ShitControllerExceptionCatcher defaultCatcher = new ShitControllerExceptionCatcher() {
		
		@Override
		public void catching(Throwable e) {
			e.printStackTrace();
		}
	};
	
	/**
	 * 捕捉器
	 */
	public static ShitControllerExceptionCatcher catcher = defaultCatcher;
    
	/**
     * @see HttpServlet#HttpServlet()
     */
    public ShitWebMappingServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		invoke(request, response, Type.GET, Type.GET_AND_POST);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		invoke(request, response, Type.POST, Type.GET_AND_POST);
	}
	
	/**
	 * 完成请求的映射
	 * @param request
	 * @param response
	 * @param types
	 * @throws ServletException
	 * @throws IOException
	 */
	private void invoke(HttpServletRequest request, HttpServletResponse response, Type... types) throws ServletException, IOException {
		StartPipe startPipe = new StartPipe(types);
		try {
			startPipe.through(request, response);
		} catch (ShitWebMappingConfigException e) {
			if (catcher != null) {
				catcher.catching(e);
			}
		}
	}

}
