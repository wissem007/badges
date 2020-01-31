package tn.com.smartsoft.commons.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.log.Logger;

public class InputStreamUtil {
	protected InputStreamUtil() {
	}

	public static void close(InputStream is) {
		if (is == null) {
			return;
		}
		try {
			is.close();
		} catch (IOException e) {
			new TechnicalException(e).printLogTrace(Logger.getLogger(InputStreamUtil.class));
		}
	}

	public static final byte[] getBytes(InputStream is) throws TechnicalException {
		byte[] bytes = null;
		byte[] buf = new byte[8192];
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int n = 0;
			while ((n = is.read(buf, 0, buf.length)) != -1) {
				baos.write(buf, 0, n);
			}
			bytes = baos.toByteArray();
		} catch (IOException e) {
			throw new TechnicalException(e);
		} finally {
			if (is != null) {
				close(is);
			}
		}
		return bytes;
	}

	public static final void copy(InputStream is, OutputStream os) throws TechnicalException {
		byte[] buf = new byte[8192];
		try {
			int n = 0;
			while ((n = is.read(buf, 0, buf.length)) != -1) {
				os.write(buf, 0, n);
			}
		} catch (IOException e) {
			throw new TechnicalException(e);
		}
	}

	public static int available(InputStream is) throws TechnicalException {
		try {
			return is.available();
		} catch (IOException e) {
			throw new TechnicalException(e);
		}
	}
}