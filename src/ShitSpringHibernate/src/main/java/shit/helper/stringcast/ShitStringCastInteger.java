package shit.helper.stringcast;

import shit.helper.ShitReflectHelper.ShitStringCast;

public class ShitStringCastInteger implements ShitStringCast {

	@Override
	public Object cast(String value) {
		return new Integer(value);
	}

}
