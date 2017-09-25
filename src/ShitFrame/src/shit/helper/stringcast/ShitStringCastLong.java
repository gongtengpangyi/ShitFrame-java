package shit.helper.stringcast;

import shit.helper.ShitReflectHelper.ShitStringCast;

public class ShitStringCastLong implements ShitStringCast {

	@Override
	public Object cast(String value) {
		return new Long(value);
	}

}
