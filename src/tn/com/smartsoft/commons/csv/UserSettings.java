package tn.com.smartsoft.commons.csv;

public class UserSettings {
	public boolean CaseSensitive;

	public char TextQualifier;

	public boolean TrimWhitespace;

	public boolean UseTextQualifier;

	public char Delimiter;

	public char RecordDelimiter;

	public char Comment;

	public boolean UseComments;

	public int EscapeMode;

	public boolean SafetySwitch;

	public boolean SkipEmptyRecords;

	public boolean CaptureRawRecord;
	public boolean ForceQualifier;

	public UserSettings() {
		CaseSensitive = true;
		TextQualifier = Letters.QUOTE;
		TrimWhitespace = true;
		UseTextQualifier = true;
		Delimiter = Letters.COMMA;
		RecordDelimiter = Letters.NULL;
		Comment = Letters.POUND;
		UseComments = false;
		EscapeMode = CsvReader.ESCAPE_MODE_DOUBLED;
		SafetySwitch = true;
		SkipEmptyRecords = true;
		CaptureRawRecord = true;
		ForceQualifier = false;
	}
}
