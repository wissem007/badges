package tn.com.digivoip.comman;

import tn.com.digivoip.comman.file.FileUtilParams;
import tn.com.digivoip.comman.string.StringPool;

/** Jodd CORE module. Contains some global defaults. */
public class FwCore{

	/** Default temp file prefix. */
	public static String			tempFilePrefix	= ".digivoipMailling";
	/** Default file encoding (UTF8). */
	public static String			encoding		= StringPool.UTF_8;
	/** Default IO buffer size (16 KB). */
	public static int				ioBufferSize	= 16384;
	/** Default parameters used in {@link jodd.io.FileUtil} operations. */
	public static FileUtilParams	fileUtilParams	= new FileUtilParams();
}