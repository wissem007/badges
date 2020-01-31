package tn.com.smartsoft.commons.csv;

public class DataBuffer {
	public char[] Buffer;

	public int Position;

	public int Count;

	public int ColumnStart;

	public int LineStart;

	public DataBuffer() {
		Buffer = new char[StaticSettings.MAX_BUFFER_SIZE];
		Position = 0;
		Count = 0;
		ColumnStart = 0;
		LineStart = 0;
	}
}