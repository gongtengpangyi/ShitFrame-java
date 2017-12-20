package shit.web.pipe;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shit.helper.ShitReflectException;
import shit.helper.ShitReflectHelper;
import shit.web.ShitController;
import shit.web.entity.*;
import shit.web.entity.ShitMappingAction.Type;
import shit.web.exception.ShitWebMappingConfigException;

/**
 * 请求操作查找并执行
 *
 * @author GongTengPangYi
 */
public class ActionPipe implements MappingPipe {

    /**
     * 请求控制器
     */
    private ShitController shitController;

    /**
     * 请求
     */
    private ShitRequest shitRequest;

    public ActionPipe(ShitController shitController, ShitRequest shitRequest) {
        super();
        this.shitController = shitController;
        this.shitRequest = shitRequest;
    }

    @Override
    public void through(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ShitWebMappingConfigException {
        Class<?> clazz = shitController.getClass();
        Method[] methods = ShitReflectHelper.findMethods(clazz, 0);

        Object result;
        Method before = null, action = null, after = null;

        ShitMappingAction mappingAction;
        ShitMappingBefore mappingBefore;
        ShitMappingAfter mappingAfter;

        for (Method method : methods) {
            mappingAction = method.getAnnotation(ShitMappingAction.class);
            mappingBefore = method.getAnnotation(ShitMappingBefore.class);
            mappingAfter = method.getAnnotation(ShitMappingAfter.class);
            if (mappingAction != null && mappingAction.name().equals(shitRequest.getActionName()) && action == null) {
                for (Type type : shitRequest.getTypes()) {
                    if (type.equals(mappingAction.type())) {
                        action = method;
                    }
                }
            } else if (mappingBefore != null && shitRequest.getActionName().startsWith(mappingBefore.url()) && before == null) {
                before = method;
            } else if (mappingAfter != null && shitRequest.getActionName().startsWith(mappingAfter.url()) && after == null) {
                after = method;
            }
        }
        doBefore(before, request, response);
        result = doAction(action, request, response);
        if (result != null) {
            doAfter(after, request, response, result);
            shitController.responseStrategy(request, response, result);
        }

    }

    /**
     * 执行before
     *
     * @param method
     * @param request
     * @param response
     * @throws ShitWebMappingConfigException
     */
    private void doBefore(Method method, HttpServletRequest request,
                          HttpServletResponse response) throws ShitWebMappingConfigException {
        if (method == null) {
            return;
        }
        Object[] paramsValue = {request, response};
        try {
            ShitReflectHelper.invokeMethod(shitController, method, paramsValue);
        } catch (ShitReflectException e) {
            e.printStackTrace();
            throw new ShitWebMappingConfigException(method.getName() + "方法配置出错");
        }
    }

    /**
     * 执行after
     *
     * @param method
     * @param request
     * @param response
     * @param result
     * @throws ShitWebMappingConfigException
     */
    private void doAfter(Method method, HttpServletRequest request,
                         HttpServletResponse response, Object result) throws ShitWebMappingConfigException {
        if (method == null) {
            return;
        }
        Object[] paramsValue = {request, response, result};
        try {
            ShitReflectHelper.invokeMethod(shitController, method, paramsValue);
        } catch (ShitReflectException e) {
            e.printStackTrace();
            throw new ShitWebMappingConfigException(method.getName() + "方法配置出错");
        }
    }

    /**
     * 执行方法
     *
     * @param method   方法
     * @param request  请求
     * @param response 响应
     * @throws IOException
     * @throws ServletException
     * @throws ShitWebMappingConfigException
     */
    private Object doAction(Method method, HttpServletRequest request,
                            HttpServletResponse response) throws ShitWebMappingConfigException {
        if (method == null) {
            return null;
        }
        Parameter[] params = method.getParameters();
        Object[] paramsValue = new Object[params.length];
        int index = 0;
        for (Parameter parameter : params) {
            /**
             * 插入参数
             */
            if (parameter.getAnnotation(ShitMappingRequest.class) != null) {
                paramsValue[index++] = request;
            } else if (parameter.getAnnotation(ShitMappingResponse.class) != null) {
                paramsValue[index++] = response;
            } else {
                ShitMappingParam mappingParam = parameter.getAnnotation(ShitMappingParam.class);
                if (mappingParam == null) {
                    continue;
                }
                String key = mappingParam.value();
                String value = request.getParameter(key);
                if (value == null) {
                    throw new ShitWebMappingConfigException(method.getName() + "方法配置出错");
                }
                paramsValue[index++] = ShitReflectHelper.stringCast(value, parameter.getType());
            }
        }
        try {
            /**
             * 执行方法
             */
            return ShitReflectHelper.invokeMethod(shitController, method, paramsValue);
        } catch (ShitReflectException e) {
            e.printStackTrace();
            throw new ShitWebMappingConfigException(method.getName() + "方法配置出错");
        }

    }

}
