package shit.web.entity;

import java.util.Arrays;

/**
 * 请求的具体
 * @author GongTengPangYi
 *
 */
public class ShitRequest {
	/**
	 * 控制器名字
	 */
	private String controllerName;
	
	/**
	 * 操作名字
	 */
	private String actionName;
	
	/**
	 * 请求类型
	 */
	private shit.web.entity.ShitMappingAction.Type[] types;

	public String getControllerName() {
		return controllerName;
	}

	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public shit.web.entity.ShitMappingAction.Type[] getTypes() {
		return types;
	}

	public void setTypes(shit.web.entity.ShitMappingAction.Type[] types) {
		this.types = types;
	}

	@Override
	public String toString() {
		return "ShitRequest [controllerName=" + controllerName + ", actionName=" + actionName + ", types="
				+ Arrays.toString(types) + "]";
	}
	
	
}
