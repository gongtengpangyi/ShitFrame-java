package shit.helper.stringcast;

import shit.helper.ShitReflectHelper.ShitStringCast;

public class ShitStringCastBoolean implements ShitStringCast {

	@Override
	public Object cast(String value) {
		return new Boolean(value);
	}

}
