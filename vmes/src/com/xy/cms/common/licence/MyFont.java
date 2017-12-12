package com.xy.cms.common.licence;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class MyFont {
	private static Font _$1 = null;

	public static Font getFont() {
		InputStream localInputStream = null;
		try {
			if (_$1 == null) {
				localInputStream = LoginLicence.class.getResourceAsStream("32pages.ttf");
				_$1 = Font.createFont(0, localInputStream);
			}
		} catch (FontFormatException localIOException2) {
			localIOException2.printStackTrace();
		} catch (IOException localIOException4) {
			localIOException4.printStackTrace();
		} finally {
			try {
				if (localInputStream != null)
					localInputStream.close();
			} catch (IOException localIOException5) {
			}
		}
		return _$1;
	}
}