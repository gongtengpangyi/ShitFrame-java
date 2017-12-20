package shit.helper.stringcast;

import shit.helper.ShitReflectHelper.ShitStringCast;

public class ShitStringCastFloat implements ShitStringCast {

	
	@Override
	public Object cast(String value) {
		return new Float(value);
	}

}
