// Copyright (c) 2003-2013, Jodd Team (jodd.org). All Rights Reserved.
package tn.com.digivoip.comman.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import tn.com.digivoip.comman.FwCore;
import tn.com.digivoip.comman.SystemUtil;
import tn.com.digivoip.comman.io.FastCharArrayWriter;
import tn.com.digivoip.comman.io.StreamUtil;
import tn.com.digivoip.comman.io.URLDecoder;
import tn.com.digivoip.comman.io.UnicodeInputStream;
import tn.com.digivoip.comman.string.StringPool;

/** File utilities. */
public class FileUtil{

	private static final String	MSG_NOT_A_DIRECTORY		= "Not a directory: ";
	private static final String	MSG_CANT_CREATE			= "Can't create: ";
	private static final String	MSG_NOT_FOUND			= "Not found: ";
	private static final String	MSG_NOT_A_FILE			= "Not a file: ";
	private static final String	MSG_ALREADY_EXISTS		= "Already exists: ";
	private static final String	MSG_UNABLE_TO_DELETE	= "Unable to delete: ";

	/** Simple factory for <code>File</code> objects. */
	private static File file(String fileName) {
		return new File(fileName);
	}
	/** Simple factory for <code>File</code> objects. */
	private static File file(File parent, String fileName) {
		return new File(parent, fileName);
	}
	// ---------------------------------------------------------------- misc shortcuts
	/** Checks if two files points to the same file. */
	public static boolean equals(String file1, String file2) {
		return FileUtil.equals(FileUtil.file(file1), FileUtil.file(file2));
	}
	/** Checks if two files points to the same file. */
	public static boolean equals(File file1, File file2) {
		try {
			file1 = file1.getCanonicalFile();
			file2 = file2.getCanonicalFile();
		} catch (IOException ignore) {
			return false;
		}
		return file1.equals(file2);
	}
	/** Converts file URLs to file. Ignores other schemes and returns <code>null</code>. */
	public static File toFile(URL url) {
		String fileName = FileUtil.toFileName(url);
		if (fileName == null) { return null; }
		return FileUtil.file(fileName);
	}
	/** Converts file to URL in a correct way. Returns <code>null</code> in case of error. */
	public static URL toURL(File file) throws MalformedURLException {
		return file.toURI().toURL();
	}
	/** Converts file URLs to file name. Accepts only URLs with 'file' protocol. Otherwise, for other schemes returns <code>null</code>. */
	public static String toFileName(URL url) {
		if ((url == null) || (url.getProtocol().equals("file") == false)) { return null; }
		String filename = url.getFile().replace('/', File.separatorChar);
		return URLDecoder.decode(filename, FwCore.encoding);
	}
	// ---------------------------------------------------------------- mkdirs
	/** Creates all folders at once.
	 * @see #mkdirs(java.io.File) */
	public static void mkdirs(String dirs) throws IOException {
		FileUtil.mkdirs(FileUtil.file(dirs));
	}
	/** Creates all folders at once. */
	public static void mkdirs(File dirs) throws IOException {
		if (dirs.exists()) {
			if (dirs.isDirectory() == false) { throw new IOException(FileUtil.MSG_NOT_A_DIRECTORY + dirs); }
			return;
		}
		if (dirs.mkdirs() == false) { throw new IOException(FileUtil.MSG_CANT_CREATE + dirs); }
	}
	/** Creates single folder.
	 * @see #mkdir(java.io.File) */
	public static void mkdir(String dir) throws IOException {
		FileUtil.mkdir(FileUtil.file(dir));
	}
	/** Creates single folders. */
	public static void mkdir(File dir) throws IOException {
		if (dir.exists()) {
			if (dir.isDirectory() == false) { throw new IOException(FileUtil.MSG_NOT_A_DIRECTORY + dir); }
			return;
		}
		if (dir.mkdir() == false) { throw new IOException(FileUtil.MSG_CANT_CREATE + dir); }
	}
	// ---------------------------------------------------------------- touch
	/** @see #touch(java.io.File) */
	public static void touch(String file) throws IOException {
		FileUtil.touch(FileUtil.file(file));
	}
	/** Implements the Unix "touch" utility. It creates a new file with size 0 or, if the file exists already, it is opened and closed without modifying it, but updating the file date and time. */
	public static void touch(File file) throws IOException {
		if (file.exists() == false) {
			StreamUtil.close(new FileOutputStream(file));
		}
		file.setLastModified(System.currentTimeMillis());
	}
	// ---------------------------------------------------------------- params
	/** Creates new {@link FileUtilParams} instance by cloning current default params. */
	public static FileUtilParams cloneParams() {
		try {
			return FwCore.fileUtilParams.clone();
		} catch (CloneNotSupportedException ignore) {
			return null;
		}
	}
	/** Creates new {@link FileUtilParams} instance with default values. */
	public static FileUtilParams params() {
		return new FileUtilParams();
	}
	// ---------------------------------------------------------------- copy file to file
	/** @see #copyFile(java.io.File, java.io.File, FileUtilParams) */
	public static void copyFile(String src, String dest) throws IOException {
		FileUtil.copyFile(FileUtil.file(src), FileUtil.file(dest), FwCore.fileUtilParams);
	}
	/** @see #copyFile(java.io.File, java.io.File, FileUtilParams) */
	public static void copyFile(String src, String dest, FileUtilParams params) throws IOException {
		FileUtil.copyFile(FileUtil.file(src), FileUtil.file(dest), params);
	}
	/** @see #copyFile(java.io.File, java.io.File, FileUtilParams) */
	public static void copyFile(File src, File dest) throws IOException {
		FileUtil.copyFile(src, dest, FwCore.fileUtilParams);
	}
	/** Copies a file to another file with specified {@link FileUtilParams copy params}. */
	public static void copyFile(File src, File dest, FileUtilParams params) throws IOException {
		FileUtil.checkFileCopy(src, dest, params);
		FileUtil.doCopyFile(src, dest, params);
	}
	private static void checkFileCopy(File src, File dest, FileUtilParams params) throws IOException {
		if (src.exists() == false) { throw new FileNotFoundException(FileUtil.MSG_NOT_FOUND + src); }
		if (src.isFile() == false) { throw new IOException(FileUtil.MSG_NOT_A_FILE + src); }
		if (FileUtil.equals(src, dest) == true) { throw new IOException("Files '" + src + "' and '" + dest + "' are equal"); }
		File destParent = dest.getParentFile();
		if (destParent != null && destParent.exists() == false) {
			if (params.createDirs == false) { throw new IOException(FileUtil.MSG_NOT_FOUND + destParent); }
			if (destParent.mkdirs() == false) { throw new IOException(FileUtil.MSG_CANT_CREATE + destParent); }
		}
	}
	/** Internal file copy when most of the pre-checking has passed. */
	private static void doCopyFile(File src, File dest, FileUtilParams params) throws IOException {
		if (dest.exists()) {
			if (dest.isDirectory()) { throw new IOException("Destination '" + dest + "' is a directory"); }
			if (params.overwrite == false) { throw new IOException(FileUtil.MSG_ALREADY_EXISTS + dest); }
		}
		// do copy file
		FileInputStream input = new FileInputStream(src);
		try {
			FileOutputStream output = new FileOutputStream(dest);
			try {
				StreamUtil.copy(input, output);
			} finally {
				StreamUtil.close(output);
			}
		} finally {
			StreamUtil.close(input);
		}
		// done
		if (src.length() != dest.length()) { throw new IOException("Copy file failed of '" + src + "' to '" + dest + "' due to different sizes"); }
		if (params.preserveDate) {
			dest.setLastModified(src.lastModified());
		}
	}
	// ---------------------------------------------------------------- copy file to directory
	/** @see #copyFileToDir(java.io.File, java.io.File, FileUtilParams) */
	public static File copyFileToDir(String src, String destDir) throws IOException {
		return FileUtil.copyFileToDir(FileUtil.file(src), FileUtil.file(destDir), FwCore.fileUtilParams);
	}
	/** @see #copyFileToDir(java.io.File, java.io.File, FileUtilParams) */
	public static File copyFileToDir(String src, String destDir, FileUtilParams params) throws IOException {
		return FileUtil.copyFileToDir(FileUtil.file(src), FileUtil.file(destDir), params);
	}
	/** @see #copyFileToDir(java.io.File, java.io.File, FileUtilParams) */
	public static File copyFileToDir(File src, File destDir) throws IOException {
		return FileUtil.copyFileToDir(src, destDir, FwCore.fileUtilParams);
	}
	/** Copies a file to folder with specified copy params and returns copied destination. */
	public static File copyFileToDir(File src, File destDir, FileUtilParams params) throws IOException {
		if (destDir.exists() && destDir.isDirectory() == false) { throw new IOException(FileUtil.MSG_NOT_A_DIRECTORY + destDir); }
		File dest = FileUtil.file(destDir, src.getName());
		FileUtil.copyFile(src, dest, params);
		return dest;
	}
	// ---------------------------------------------------------------- copy dir
	public static void copyDir(String srcDir, String destDir) throws IOException {
		FileUtil.copyDir(FileUtil.file(srcDir), FileUtil.file(destDir), FwCore.fileUtilParams);
	}
	public static void copyDir(String srcDir, String destDir, FileUtilParams params) throws IOException {
		FileUtil.copyDir(FileUtil.file(srcDir), FileUtil.file(destDir), params);
	}
	public static void copyDir(File srcDir, File destDir) throws IOException {
		FileUtil.copyDir(srcDir, destDir, FwCore.fileUtilParams);
	}
	/** Copies directory with specified copy params. */
	public static void copyDir(File srcDir, File destDir, FileUtilParams params) throws IOException {
		FileUtil.checkDirCopy(srcDir, destDir);
		FileUtil.doCopyDirectory(srcDir, destDir, params);
	}
	private static void checkDirCopy(File srcDir, File destDir) throws IOException {
		if (srcDir.exists() == false) { throw new FileNotFoundException(FileUtil.MSG_NOT_FOUND + srcDir); }
		if (srcDir.isDirectory() == false) { throw new IOException(FileUtil.MSG_NOT_A_DIRECTORY + srcDir); }
		if (FileUtil.equals(srcDir, destDir) == true) { throw new IOException("Source '" + srcDir + "' and destination '" + destDir + "' are equal"); }
	}
	private static void doCopyDirectory(File srcDir, File destDir, FileUtilParams params) throws IOException {
		if (destDir.exists()) {
			if (destDir.isDirectory() == false) { throw new IOException(FileUtil.MSG_NOT_A_DIRECTORY + destDir); }
		} else {
			if (params.createDirs == false) { throw new IOException(FileUtil.MSG_NOT_FOUND + destDir); }
			if (destDir.mkdirs() == false) { throw new IOException(FileUtil.MSG_CANT_CREATE + destDir); }
			if (params.preserveDate) {
				destDir.setLastModified(srcDir.lastModified());
			}
		}
		File[] files = srcDir.listFiles();
		if (files == null) { throw new IOException("Failed to list contents of: " + srcDir); }
		IOException exception = null;
		for (File file : files) {
			File destFile = FileUtil.file(destDir, file.getName());
			try {
				if (file.isDirectory()) {
					if (params.recursive == true) {
						FileUtil.doCopyDirectory(file, destFile, params);
					}
				} else {
					FileUtil.doCopyFile(file, destFile, params);
				}
			} catch (IOException ioex) {
				if (params.continueOnError == true) {
					exception = ioex;
					continue;
				}
				throw ioex;
			}
		}
		if (exception != null) { throw exception; }
	}
	// ---------------------------------------------------------------- move file
	public static void moveFile(String src, String dest) throws IOException {
		FileUtil.moveFile(FileUtil.file(src), FileUtil.file(dest), FwCore.fileUtilParams);
	}
	public static void moveFile(String src, String dest, FileUtilParams params) throws IOException {
		FileUtil.moveFile(FileUtil.file(src), FileUtil.file(dest), params);
	}
	public static void moveFile(File src, File dest) throws IOException {
		FileUtil.moveFile(src, dest, FwCore.fileUtilParams);
	}
	public static void moveFile(File src, File dest, FileUtilParams params) throws IOException {
		FileUtil.checkFileCopy(src, dest, params);
		FileUtil.doMoveFile(src, dest, params);
	}
	private static void doMoveFile(File src, File dest, FileUtilParams params) throws IOException {
		if (dest.exists()) {
			if (dest.isFile() == false) { throw new IOException(FileUtil.MSG_NOT_A_FILE + dest); }
			if (params.overwrite == false) { throw new IOException(FileUtil.MSG_ALREADY_EXISTS + dest); }
			dest.delete();
		}
		final boolean rename = src.renameTo(dest);
		if (!rename) {
			FileUtil.doCopyFile(src, dest, params);
			src.delete();
		}
	}
	// ---------------------------------------------------------------- move file to dir
	public static void moveFileToDir(String src, String destDir) throws IOException {
		FileUtil.moveFileToDir(FileUtil.file(src), FileUtil.file(destDir), FwCore.fileUtilParams);
	}
	public static void moveFileToDir(String src, String destDir, FileUtilParams params) throws IOException {
		FileUtil.moveFileToDir(FileUtil.file(src), FileUtil.file(destDir), params);
	}
	public static void moveFileToDir(File src, File destDir) throws IOException {
		FileUtil.moveFileToDir(src, destDir, FwCore.fileUtilParams);
	}
	public static void moveFileToDir(File src, File destDir, FileUtilParams params) throws IOException {
		if (destDir.exists() && destDir.isDirectory() == false) { throw new IOException(FileUtil.MSG_NOT_A_DIRECTORY + destDir); }
		FileUtil.moveFile(src, FileUtil.file(destDir, src.getName()), params);
	}
	// ---------------------------------------------------------------- move dir
	public static void moveDir(String srcDir, String destDir) throws IOException {
		FileUtil.moveDir(FileUtil.file(srcDir), FileUtil.file(destDir));
	}
	public static void moveDir(File srcDir, File destDir) throws IOException {
		FileUtil.checkDirCopy(srcDir, destDir);
		FileUtil.doMoveDirectory(srcDir, destDir);
	}
	private static void doMoveDirectory(File src, File dest) throws IOException {
		if (dest.exists()) {
			if (dest.isDirectory() == false) { throw new IOException(FileUtil.MSG_NOT_A_DIRECTORY + dest); }
			dest = FileUtil.file(dest, dest.getName());
			dest.mkdir();
		}
		final boolean rename = src.renameTo(dest);
		if (!rename) {
			FileUtil.doCopyDirectory(src, dest, FileUtil.params());
			FileUtil.deleteDir(src);
		}
	}
	// ---------------------------------------------------------------- delete file
	public static void deleteFile(String dest) throws IOException {
		FileUtil.deleteFile(FileUtil.file(dest));
	}
	public static void deleteFile(File dest) throws IOException {
		if (dest.exists() == false) { throw new FileNotFoundException(FileUtil.MSG_NOT_FOUND + dest); }
		if (dest.isFile() == false) { throw new IOException(FileUtil.MSG_NOT_A_FILE + dest); }
		if (dest.delete() == false) { throw new IOException(FileUtil.MSG_UNABLE_TO_DELETE + dest); }
	}
	// ---------------------------------------------------------------- delete dir
	public static void deleteDir(String dest) throws IOException {
		FileUtil.deleteDir(FileUtil.file(dest), FwCore.fileUtilParams);
	}
	public static void deleteDir(String dest, FileUtilParams params) throws IOException {
		FileUtil.deleteDir(FileUtil.file(dest), params);
	}
	public static void deleteDir(File dest) throws IOException {
		FileUtil.deleteDir(dest, FwCore.fileUtilParams);
	}
	/** Deletes a directory. */
	public static void deleteDir(File dest, FileUtilParams params) throws IOException {
		FileUtil.cleanDir(dest, params);
		if (dest.delete() == false) { throw new IOException(FileUtil.MSG_UNABLE_TO_DELETE + dest); }
	}
	public static void cleanDir(String dest) throws IOException {
		FileUtil.cleanDir(FileUtil.file(dest), FwCore.fileUtilParams);
	}
	public static void cleanDir(String dest, FileUtilParams params) throws IOException {
		FileUtil.cleanDir(FileUtil.file(dest), params);
	}
	public static void cleanDir(File dest) throws IOException {
		FileUtil.cleanDir(dest, FwCore.fileUtilParams);
	}
	/** Cleans a directory without deleting it. */
	public static void cleanDir(File dest, FileUtilParams params) throws IOException {
		if (dest.exists() == false) { throw new FileNotFoundException(FileUtil.MSG_NOT_FOUND + dest); }
		if (dest.isDirectory() == false) { throw new IOException(FileUtil.MSG_NOT_A_DIRECTORY + dest); }
		File[] files = dest.listFiles();
		if (files == null) { throw new IOException("Failed to list contents of: " + dest); }
		IOException exception = null;
		for (File file : files) {
			try {
				if (file.isDirectory()) {
					if (params.recursive == true) {
						FileUtil.deleteDir(file, params);
					}
				} else {
					file.delete();
				}
			} catch (IOException ioex) {
				if (params.continueOnError == true) {
					exception = ioex;
					continue;
				}
				throw ioex;
			}
		}
		if (exception != null) { throw exception; }
	}
	// ---------------------------------------------------------------- read/write chars
	public static char[] readUTFChars(String fileName) throws IOException {
		return FileUtil.readUTFChars(FileUtil.file(fileName));
	}
	/** Reads UTF file content as char array.
	 * @see UnicodeInputStream */
	public static char[] readUTFChars(File file) throws IOException {
		if (file.exists() == false) { throw new FileNotFoundException(FileUtil.MSG_NOT_FOUND + file); }
		if (file.isFile() == false) { throw new IOException(FileUtil.MSG_NOT_A_FILE + file); }
		long len = file.length();
		if (len >= Integer.MAX_VALUE) {
			len = Integer.MAX_VALUE;
		}
		UnicodeInputStream in = null;
		try {
			in = new UnicodeInputStream(new FileInputStream(file), null);
			FastCharArrayWriter fastCharArrayWriter = new FastCharArrayWriter((int) len);
			String encoding = in.getDetectedEncoding();
			if (encoding == null) {
				encoding = StringPool.UTF_8;
			}
			StreamUtil.copy(in, fastCharArrayWriter, encoding);
			return fastCharArrayWriter.toCharArray();
		} finally {
			StreamUtil.close(in);
		}
	}
	public static char[] readChars(String fileName) throws IOException {
		return FileUtil.readChars(FileUtil.file(fileName), FwCore.fileUtilParams.encoding);
	}
	public static char[] readChars(File file) throws IOException {
		return FileUtil.readChars(file, FwCore.fileUtilParams.encoding);
	}
	public static char[] readChars(String fileName, String encoding) throws IOException {
		return FileUtil.readChars(FileUtil.file(fileName), encoding);
	}
	/** Reads file content as char array. */
	public static char[] readChars(File file, String encoding) throws IOException {
		if (file.exists() == false) { throw new FileNotFoundException(FileUtil.MSG_NOT_FOUND + file); }
		if (file.isFile() == false) { throw new IOException(FileUtil.MSG_NOT_A_FILE + file); }
		long len = file.length();
		if (len >= Integer.MAX_VALUE) {
			len = Integer.MAX_VALUE;
		}
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			if (encoding.startsWith("UTF")) {
				in = new UnicodeInputStream(in, encoding);
			}
			FastCharArrayWriter fastCharArrayWriter = new FastCharArrayWriter((int) len);
			StreamUtil.copy(in, fastCharArrayWriter, encoding);
			return fastCharArrayWriter.toCharArray();
		} finally {
			StreamUtil.close(in);
		}
	}
	public static void writeChars(File dest, char[] data) throws IOException {
		FileUtil.outChars(dest, data, FwCore.encoding, false);
	}
	public static void writeChars(String dest, char[] data) throws IOException {
		FileUtil.outChars(FileUtil.file(dest), data, FwCore.encoding, false);
	}
	public static void writeChars(File dest, char[] data, String encoding) throws IOException {
		FileUtil.outChars(dest, data, encoding, false);
	}
	public static void writeChars(String dest, char[] data, String encoding) throws IOException {
		FileUtil.outChars(FileUtil.file(dest), data, encoding, false);
	}
	protected static void outChars(File dest, char[] data, String encoding, boolean append) throws IOException {
		if (dest.exists() == true) {
			if (dest.isFile() == false) { throw new IOException(FileUtil.MSG_NOT_A_FILE + dest); }
		}
		Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dest, append), encoding));
		try {
			out.write(data);
		} finally {
			StreamUtil.close(out);
		}
	}
	// ---------------------------------------------------------------- read/write string
	public static String readUTFString(String fileName) throws IOException {
		return FileUtil.readUTFString(FileUtil.file(fileName));
	}
	/** Detects optional BOM and reads UTF string from a file. If BOM is missing, UTF-8 is assumed.
	 * @see UnicodeInputStream */
	public static String readUTFString(File file) throws IOException {
		if (file.exists() == false) { throw new FileNotFoundException(FileUtil.MSG_NOT_FOUND + file); }
		if (file.isFile() == false) { throw new IOException(FileUtil.MSG_NOT_A_FILE + file); }
		long len = file.length();
		if (len >= Integer.MAX_VALUE) {
			len = Integer.MAX_VALUE;
		}
		UnicodeInputStream in = null;
		try {
			in = new UnicodeInputStream(new FileInputStream(file), null);
			FastCharArrayWriter out = new FastCharArrayWriter((int) len);
			String encoding = in.getDetectedEncoding();
			if (encoding == null) {
				encoding = StringPool.UTF_8;
			}
			StreamUtil.copy(in, out, encoding);
			return out.toString();
		} finally {
			StreamUtil.close(in);
		}
	}
	/** Detects optional BOM and reads UTF string from an input stream. If BOM is missing, UTF-8 is assumed. */
	public static String readUTFString(InputStream inputStream) throws IOException {
		UnicodeInputStream in = null;
		try {
			in = new UnicodeInputStream(inputStream, null);
			FastCharArrayWriter out = new FastCharArrayWriter();
			String encoding = in.getDetectedEncoding();
			if (encoding == null) {
				encoding = StringPool.UTF_8;
			}
			StreamUtil.copy(in, out, encoding);
			return out.toString();
		} finally {
			StreamUtil.close(in);
		}
	}
	public static String readString(String source) throws IOException {
		return FileUtil.readString(FileUtil.file(source), FwCore.fileUtilParams.encoding);
	}
	public static String readString(String source, String encoding) throws IOException {
		return FileUtil.readString(FileUtil.file(source), encoding);
	}
	public static String readString(File source) throws IOException {
		return FileUtil.readString(source, FwCore.fileUtilParams.encoding);
	}
	/** Reads file content as string encoded in provided encoding. For UTF encoded files, detects optional BOM characters. */
	public static String readString(File file, String encoding) throws IOException {
		if (file.exists() == false) { throw new FileNotFoundException(FileUtil.MSG_NOT_FOUND + file); }
		if (file.isFile() == false) { throw new IOException(FileUtil.MSG_NOT_A_FILE + file); }
		long len = file.length();
		if (len >= Integer.MAX_VALUE) {
			len = Integer.MAX_VALUE;
		}
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			if (encoding.startsWith("UTF")) {
				in = new UnicodeInputStream(in, encoding);
			}
			FastCharArrayWriter out = new FastCharArrayWriter((int) len);
			StreamUtil.copy(in, out, encoding);
			return out.toString();
		} finally {
			StreamUtil.close(in);
		}
	}
	public static void writeString(String dest, String data) throws IOException {
		FileUtil.outString(FileUtil.file(dest), data, FwCore.fileUtilParams.encoding, false);
	}
	public static void writeString(String dest, String data, String encoding) throws IOException {
		FileUtil.outString(FileUtil.file(dest), data, encoding, false);
	}
	public static void writeString(File dest, String data) throws IOException {
		FileUtil.outString(dest, data, FwCore.fileUtilParams.encoding, false);
	}
	public static void writeString(File dest, String data, String encoding) throws IOException {
		FileUtil.outString(dest, data, encoding, false);
	}
	public static void appendString(String dest, String data) throws IOException {
		FileUtil.outString(FileUtil.file(dest), data, FwCore.fileUtilParams.encoding, true);
	}
	public static void appendString(String dest, String data, String encoding) throws IOException {
		FileUtil.outString(FileUtil.file(dest), data, encoding, true);
	}
	public static void appendString(File dest, String data) throws IOException {
		FileUtil.outString(dest, data, FwCore.fileUtilParams.encoding, true);
	}
	public static void appendString(File dest, String data, String encoding) throws IOException {
		FileUtil.outString(dest, data, encoding, true);
	}
	protected static void outString(File dest, String data, String encoding, boolean append) throws IOException {
		if (dest.exists() == true) {
			if (dest.isFile() == false) { throw new IOException(FileUtil.MSG_NOT_A_FILE + dest); }
		}
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(dest, append);
			out.write(data.getBytes(encoding));
		} finally {
			StreamUtil.close(out);
		}
	}
	// ---------------------------------------------------------------- stream
	public static void writeStream(File dest, InputStream in) throws IOException {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(dest);
			StreamUtil.copy(in, out);
		} finally {
			StreamUtil.close(out);
		}
	}
	public static void writeStream(String dest, InputStream in) throws IOException {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(dest);
			StreamUtil.copy(in, out);
		} finally {
			StreamUtil.close(out);
		}
	}
	// ---------------------------------------------------------------- read/write string lines
	public static String[] readLines(String source) throws IOException {
		return FileUtil.readLines(FileUtil.file(source), FwCore.fileUtilParams.encoding);
	}
	public static String[] readLines(String source, String encoding) throws IOException {
		return FileUtil.readLines(FileUtil.file(source), encoding);
	}
	public static String[] readLines(File source) throws IOException {
		return FileUtil.readLines(source, FwCore.fileUtilParams.encoding);
	}
	/** Reads lines from source files. */
	public static String[] readLines(File file, String encoding) throws IOException {
		if (file.exists() == false) { throw new FileNotFoundException(FileUtil.MSG_NOT_FOUND + file); }
		if (file.isFile() == false) { throw new IOException(FileUtil.MSG_NOT_A_FILE + file); }
		List<String> list = new ArrayList<String>();
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			if (encoding.startsWith("UTF")) {
				in = new UnicodeInputStream(in, encoding);
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(in, encoding));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				list.add(strLine);
			}
		} finally {
			StreamUtil.close(in);
		}
		return list.toArray(new String[list.size()]);
	}
	// ---------------------------------------------------------------- read/write bytearray
	public static byte[] readBytes(String file) throws IOException {
		return FileUtil.readBytes(FileUtil.file(file));
	}
	public static byte[] readBytes(File file) throws IOException {
		if (file.exists() == false) { throw new FileNotFoundException(FileUtil.MSG_NOT_FOUND + file); }
		if (file.isFile() == false) { throw new IOException(FileUtil.MSG_NOT_A_FILE + file); }
		long len = file.length();
		if (len >= Integer.MAX_VALUE) { throw new IOException("File is larger then max array size"); }
		byte[] bytes = new byte[(int) len];
		RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
		randomAccessFile.readFully(bytes);
		randomAccessFile.close();
		return bytes;
	}
	public static void writeBytes(String dest, byte[] data) throws IOException {
		FileUtil.outBytes(FileUtil.file(dest), data, 0, data.length, false);
	}
	public static void writeBytes(String dest, byte[] data, int off, int len) throws IOException {
		FileUtil.outBytes(FileUtil.file(dest), data, off, len, false);
	}
	public static void writeBytes(File dest, byte[] data) throws IOException {
		FileUtil.outBytes(dest, data, 0, data.length, false);
	}
	public static void writeBytes(File dest, byte[] data, int off, int len) throws IOException {
		FileUtil.outBytes(dest, data, off, len, false);
	}
	public static void appendBytes(String dest, byte[] data) throws IOException {
		FileUtil.outBytes(FileUtil.file(dest), data, 0, data.length, true);
	}
	public static void appendBytes(String dest, byte[] data, int off, int len) throws IOException {
		FileUtil.outBytes(FileUtil.file(dest), data, off, len, true);
	}
	public static void appendBytes(File dest, byte[] data) throws IOException {
		FileUtil.outBytes(dest, data, 0, data.length, true);
	}
	public static void appendBytes(File dest, byte[] data, int off, int len) throws IOException {
		FileUtil.outBytes(dest, data, off, len, true);
	}
	protected static void outBytes(File dest, byte[] data, int off, int len, boolean append) throws IOException {
		if (dest.exists() == true) {
			if (dest.isFile() == false) { throw new IOException(FileUtil.MSG_NOT_A_FILE + dest); }
		}
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(dest, append);
			out.write(data, off, len);
		} finally {
			StreamUtil.close(out);
		}
	}
	// ---------------------------------------------------------------- equals content
	public static boolean compare(String file1, String file2) throws IOException {
		return FileUtil.compare(FileUtil.file(file1), FileUtil.file(file2));
	}
	/** Compare the contents of two files to determine if they are equal or not.
	 * <p>
	 * This method checks to see if the two files are different lengths or if they point to the same file, before resorting to byte-by-byte comparison of the contents.
	 * <p>
	 * Code origin: Avalon */
	public static boolean compare(File file1, File file2) throws IOException {
		boolean file1Exists = file1.exists();
		if (file1Exists != file2.exists()) { return false; }
		if (file1Exists == false) { return true; }
		if ((file1.isFile() == false) || (file2.isFile() == false)) { throw new IOException("Only files can be compared"); }
		if (file1.length() != file2.length()) { return false; }
		if (FileUtil.equals(file1, file2)) { return true; }
		InputStream input1 = null;
		InputStream input2 = null;
		try {
			input1 = new FileInputStream(file1);
			input2 = new FileInputStream(file2);
			return StreamUtil.compare(input1, input2);
		} finally {
			StreamUtil.close(input1);
			StreamUtil.close(input2);
		}
	}
	// ---------------------------------------------------------------- time
	public static boolean isNewer(String file, String reference) {
		return FileUtil.isNewer(FileUtil.file(file), FileUtil.file(reference));
	}
	/** Test if specified <code>File</code> is newer than the reference <code>File</code>.
	 * @param file the <code>File</code> of which the modification date must be compared
	 * @param reference the <code>File</code> of which the modification date is used
	 * @return <code>true</code> if the <code>File</code> exists and has been modified more recently than the reference <code>File</code>. */
	public static boolean isNewer(File file, File reference) {
		if (reference.exists() == false) { throw new IllegalArgumentException("Reference file not found: " + reference); }
		return FileUtil.isNewer(file, reference.lastModified());
	}
	public static boolean isOlder(String file, String reference) {
		return FileUtil.isOlder(FileUtil.file(file), FileUtil.file(reference));
	}
	public static boolean isOlder(File file, File reference) {
		if (reference.exists() == false) { throw new IllegalArgumentException("Reference file not found: " + reference); }
		return FileUtil.isOlder(file, reference.lastModified());
	}
	/** Tests if the specified <code>File</code> is newer than the specified time reference.
	 * @param file the <code>File</code> of which the modification date must be compared.
	 * @param timeMillis the time reference measured in milliseconds since the epoch (00:00:00 GMT, January 1, 1970)
	 * @return <code>true</code> if the <code>File</code> exists and has been modified after the given time reference. */
	public static boolean isNewer(File file, long timeMillis) {
		if (!file.exists()) { return false; }
		return file.lastModified() > timeMillis;
	}
	public static boolean isNewer(String file, long timeMillis) {
		return FileUtil.isNewer(FileUtil.file(file), timeMillis);
	}
	public static boolean isOlder(File file, long timeMillis) {
		if (!file.exists()) { return false; }
		return file.lastModified() < timeMillis;
	}
	public static boolean isOlder(String file, long timeMillis) {
		return FileUtil.isOlder(FileUtil.file(file), timeMillis);
	}
	// ---------------------------------------------------------------- smart copy
	public static void copy(String src, String dest) throws IOException {
		FileUtil.copy(FileUtil.file(src), FileUtil.file(dest), FwCore.fileUtilParams);
	}
	public static void copy(String src, File dest) throws IOException {
		FileUtil.copy(FileUtil.file(src), dest, FwCore.fileUtilParams);
	}
	public static void copy(String src, String dest, FileUtilParams params) throws IOException {
		FileUtil.copy(FileUtil.file(src), FileUtil.file(dest), params);
	}
	public static void copy(File src, File dest) throws IOException {
		FileUtil.copy(src, dest, FwCore.fileUtilParams);
	}
	/** Smart copy. If source is a directory, copy it to destination. Otherwise, if destination is directory, copy source file to it. Otherwise, try to copy source file to destination file. */
	public static void copy(File src, File dest, FileUtilParams params) throws IOException {
		if (src.isDirectory() == true) {
			FileUtil.copyDir(src, dest, params);
			return;
		}
		if (dest.isDirectory() == true) {
			FileUtil.copyFileToDir(src, dest, params);
			return;
		}
		FileUtil.copyFile(src, dest, params);
	}
	// ---------------------------------------------------------------- smart move
	public static void move(String src, String dest) throws IOException {
		FileUtil.move(FileUtil.file(src), FileUtil.file(dest), FwCore.fileUtilParams);
	}
	public static void move(String src, String dest, FileUtilParams params) throws IOException {
		FileUtil.move(FileUtil.file(src), FileUtil.file(dest), params);
	}
	public static void move(File src, File dest) throws IOException {
		FileUtil.move(src, dest, FwCore.fileUtilParams);
	}
	/** Smart move. If source is a directory, move it to destination. Otherwise, if destination is directory, move source file to it. Otherwise, try to move source file to destination file. */
	public static void move(File src, File dest, FileUtilParams params) throws IOException {
		if (src.isDirectory() == true) {
			FileUtil.moveDir(src, dest);
			return;
		}
		if (dest.isDirectory() == true) {
			FileUtil.moveFileToDir(src, dest, params);
			return;
		}
		FileUtil.moveFile(src, dest, params);
	}
	// ---------------------------------------------------------------- smart delete
	public static void delete(String dest) throws IOException {
		FileUtil.delete(FileUtil.file(dest), FwCore.fileUtilParams);
	}
	public static void delete(String dest, FileUtilParams params) throws IOException {
		FileUtil.delete(FileUtil.file(dest), params);
	}
	public static void delete(File dest) throws IOException {
		FileUtil.delete(dest, FwCore.fileUtilParams);
	}
	/** Smart delete of destination file or directory. */
	public static void delete(File dest, FileUtilParams params) throws IOException {
		if (dest.isDirectory()) {
			FileUtil.deleteDir(dest, params);
			return;
		}
		FileUtil.deleteFile(dest);
	}
	// ---------------------------------------------------------------- misc
	/** Check if one file is an ancestor of second one.
	 * @param strict if <code>false</code> then this method returns <code>true</code> if ancestor and file are equal
	 * @return <code>true</code> if ancestor is parent of file; <code>false</code> otherwise */
	public static boolean isAncestor(File ancestor, File file, boolean strict) {
		File parent = strict ? FileUtil.getParentFile(file) : file;
		while (true) {
			if (parent == null) { return false; }
			if (parent.equals(ancestor)) { return true; }
			parent = FileUtil.getParentFile(parent);
		}
	}
	/** Returns parent for the file. The method correctly processes "." and ".." in file names. The name remains relative if was relative before. Returns <code>null</code> if the file has no parent. */
	public static File getParentFile(final File file) {
		int skipCount = 0;
		File parentFile = file;
		while (true) {
			parentFile = parentFile.getParentFile();
			if (parentFile == null) { return null; }
			if (StringPool.DOT.equals(parentFile.getName())) {
				continue;
			}
			if (StringPool.DOTDOT.equals(parentFile.getName())) {
				skipCount++;
				continue;
			}
			if (skipCount > 0) {
				skipCount--;
				continue;
			}
			return parentFile;
		}
	}
	public static boolean isFilePathAcceptable(File file, FileFilter fileFilter) {
		do {
			if (fileFilter != null && !fileFilter.accept(file)) { return false; }
			file = file.getParentFile();
		} while (file != null);
		return true;
	}
	// ---------------------------------------------------------------- temp
	public static File createTempDirectory() throws IOException {
		return FileUtil.createTempDirectory(FwCore.tempFilePrefix, null, null);
	}
	/** Creates temporary directory. */
	public static File createTempDirectory(String prefix, String suffix) throws IOException {
		return FileUtil.createTempDirectory(prefix, suffix, null);
	}
	/** Creates temporary directory. */
	public static File createTempDirectory(String prefix, String suffix, File tempDir) throws IOException {
		File file = FileUtil.createTempFile(prefix, suffix, tempDir);
		file.delete();
		file.mkdir();
		return file;
	}
	/** Simple method that creates temp file. */
	public static File createTempFile() throws IOException {
		return FileUtil.createTempFile(FwCore.tempFilePrefix, null, null, true);
	}
	/** Creates temporary file. If <code>create</code> is set to <code>true</code> file will be physically created on the file system. Otherwise, it will be created and then deleted - trick that will
	 * make temp file exist only if they are used. */
	public static File createTempFile(String prefix, String suffix, File tempDir, boolean create) throws IOException {
		File file = FileUtil.createTempFile(prefix, suffix, tempDir);
		file.delete();
		if (create) {
			file.createNewFile();
		}
		return file;
	}
	/** Creates temporary file. Wraps java method and repeat creation several time if something fail. */
	public static File createTempFile(String prefix, String suffix, File dir) throws IOException {
		int exceptionsCount = 0;
		while (true) {
			try {
				return File.createTempFile(prefix, suffix, dir).getCanonicalFile();
			} catch (IOException ioex) { // fixes java.io.WinNTFileSystem.createFileExclusively access denied
				if (++exceptionsCount >= 50) { throw ioex; }
			}
		}
	}
	// ---------------------------------------------------------------- symlink
	/** Determines whether the specified file is a symbolic link rather than an actual file. Always returns <code>false</code> on Windows. */
	public static boolean isSymlink(final File file) throws IOException {
		if (SystemUtil.isHostWindows()) { return false; }
		File fileInCanonicalDir;
		if (file.getParent() == null) {
			fileInCanonicalDir = file;
		} else {
			File canonicalDir = file.getParentFile().getCanonicalFile();
			fileInCanonicalDir = new File(canonicalDir, file.getName());
		}
		return !fileInCanonicalDir.getCanonicalFile().equals(fileInCanonicalDir.getAbsoluteFile());
	}
}