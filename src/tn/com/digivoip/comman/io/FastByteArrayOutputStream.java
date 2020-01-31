package tn.com.digivoip.comman.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class FastByteArrayOutputStream extends OutputStream{

	private final FastByteBuffer	buffer;

	public FastByteArrayOutputStream() {
		this(1024);
	}
	public FastByteArrayOutputStream(int size) {
		buffer = new FastByteBuffer(size);
	}
	public void write(byte[] b, int off, int len) {
		buffer.append(b, off, len);
	}
	public void write(int b) {
		buffer.append((byte) b);
	}
	public int size() {
		return buffer.size();
	}
	public void close() {
		// nop
	}
	public void reset() {
		buffer.clear();
	}
	public void writeTo(OutputStream out) throws IOException {
		int index = buffer.index();
		for (int i = 0; i < index; i++) {
			byte[] buf = buffer.array(i);
			out.write(buf);
		}
		out.write(buffer.array(index), 0, buffer.offset());
	}
	public byte[] toByteArray() {
		return buffer.toArray();
	}
	public String toString() {
		return new String(toByteArray());
	}
	public String toString(String enc) throws UnsupportedEncodingException {
		return new String(toByteArray(), enc);
	}
}