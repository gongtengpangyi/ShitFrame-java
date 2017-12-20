package shit.web.tool;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理请求参数的工具类
 *
 * @author GongTengPangYi
 */
public class RequestParamsTool {

    /**
     * 创建实例
     *
     * @param request
     * @return
     */
    public static RequestParamsTool create(HttpServletRequest request) {
        return new RequestParamsTool(request);
    }

    private HttpServletRequest request;

    private RequestParamsTool(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * 对应请求参数集合的操作
     *
     * @param keyStr 请求参数集合，参数中间用";"分开
     * @param doing  操作
     * @return 工具本身
     */
    public RequestParamsTool then(String keyStr, ParamDoing doing) {
        for (String key : keyStr.split(";")) {
            String value = request.getParameter(key);
            if (value != null) {
                doing.doing(key, value);
            }
        }
        return this;
    }

    /**
     * 请求参数操作接口
     */
    public interface ParamDoing {

        /**
         * 具体的操作钩子
         *
         * @param key 请求参数键
         * @param value 请求参数值
         */
        void doing(String key, String value);
    }
}
