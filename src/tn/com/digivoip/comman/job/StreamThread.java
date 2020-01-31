package tn.com.digivoip.comman.job;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class StreamThread extends Thread{

	private static final Logger	LOG	= Logger.getLogger("org.columba.core.base");	//$NON-NLS-1$
	InputStream					is;
	String						type;
	StringBuffer				buf;

	public StreamThread(InputStream theInputStream, String theType) {
		this.is = theInputStream;
		this.type = theType;
		buf = new StringBuffer();
	}
	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				StreamThread.LOG.fine(type + ">" + line); //$NON-NLS-1$
				buf.append(line + "\n"); //$NON-NLS-1$
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	public String getBuffer() {
		return buf.toString();
	}
}
