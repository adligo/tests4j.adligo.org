package org.adligo.tests4j.models.shared.reference_groups.jse.v1_6;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.adligo.tests4j.models.shared.association.I_PackageConstantLookupModel;
import org.adligo.tests4j.shared.asserts.reference.NameOnlyReferenceGroup;
import org.adligo.tests4j.shared.asserts.reference.ReferenceGroupBaseDelegate;

/**
 * This class represents the classes in java.io
 * for the latest version JSE version 1_8.
 * 
 * partially generated by org.adligo.tests4j_gen.console.JSEGroupGen
 * copy/pasting...
 * Also this class should eventually include the entire api 
 * (public methods and fields), for assertion dependency.
 * 
 * @author scott
 *
 */
public class JSE_1_6_IO extends ReferenceGroupBaseDelegate implements I_JSE_1_6_IO, I_PackageConstantLookupModel {
	public static final String JAVA_IO = "java.io";
	private static final Map<String,String> CONSTANT_LOOKUP = getConstantLookup();
	public static final JSE_1_6_IO INSTANCE = new JSE_1_6_IO();
	
	private static Map<String,String> getConstantLookup() {
		Map<String,String> toRet = new HashMap<>();
		toRet.put("java.io.Closeable","CLOSEABLE");
		toRet.put("java.io.DataInput","DATA_INPUT");
		toRet.put("java.io.DataOutput","DATA_OUTPUT");
		toRet.put("java.io.Externalizable","EXTERNALIZABLE");
		toRet.put("java.io.FileFilter","FILE_FILTER");
		toRet.put("java.io.FilenameFilter","FILENAME_FILTER");
		toRet.put("java.io.Flushable","FLUSHABLE");
		toRet.put("java.io.ObjectInput","OBJECT_INPUT");
		toRet.put("java.io.ObjectInputValidation","OBJECT_INPUT_VALIDATION");
		toRet.put("java.io.ObjectOutput","OBJECT_OUTPUT");
		toRet.put("java.io.ObjectStreamConstants","OBJECT_STREAM_CONSTANTS");
		toRet.put("java.io.Serializable","SERIALIZABLE");
		toRet.put("java.io.BufferedInputStream","BUFFERED_INPUT_STREAM");
		toRet.put("java.io.BufferedOutputStream","BUFFERED_OUTPUT_STREAM");
		toRet.put("java.io.BufferedReader","BUFFERED_READER");
		toRet.put("java.io.BufferedWriter","BUFFERED_WRITER");
		toRet.put("java.io.ByteArrayInputStream","BYTE_ARRAY_INPUT_STREAM");
		toRet.put("java.io.ByteArrayOutputStream","BYTE_ARRAY_OUTPUT_STREAM");
		toRet.put("java.io.CharArrayReader","CHAR_ARRAY_READER");
		toRet.put("java.io.CharArrayWriter","CHAR_ARRAY_WRITER");
		toRet.put("java.io.Console","CONSOLE");
		toRet.put("java.io.DataInputStream","DATA_INPUT_STREAM");
		toRet.put("java.io.DataOutputStream","DATA_OUTPUT_STREAM");
		toRet.put("java.io.File","FILE");
		toRet.put("java.io.FileDescriptor","FILE_DESCRIPTOR");
		toRet.put("java.io.FileInputStream","FILE_INPUT_STREAM");
		toRet.put("java.io.FileOutputStream","FILE_OUTPUT_STREAM");
		toRet.put("java.io.FilePermission","FILE_PERMISSION");
		toRet.put("java.io.FileReader","FILE_READER");
		toRet.put("java.io.FileWriter","FILE_WRITER");
		toRet.put("java.io.FilterInputStream","FILTER_INPUT_STREAM");
		toRet.put("java.io.FilterOutputStream","FILTER_OUTPUT_STREAM");
		toRet.put("java.io.FilterReader","FILTER_READER");
		toRet.put("java.io.FilterWriter","FILTER_WRITER");
		toRet.put("java.io.InputStream","INPUT_STREAM");
		toRet.put("java.io.InputStreamReader","INPUT_STREAM_READER");
		toRet.put("java.io.LineNumberInputStream","LINE_NUMBER_INPUT_STREAM");
		toRet.put("java.io.LineNumberReader","LINE_NUMBER_READER");
		toRet.put("java.io.ObjectInputStream","OBJECT_INPUT_STREAM");
		toRet.put("java.io.ObjectInputStream$GetField","GET_FIELD");
		toRet.put("java.io.ObjectOutputStream","OBJECT_OUTPUT_STREAM");
		toRet.put("java.io.ObjectOutputStream$PutField","PUT_FIELD");
		toRet.put("java.io.ObjectStreamClass","OBJECT_STREAM_CLASS");
		toRet.put("java.io.ObjectStreamField","OBJECT_STREAM_FIELD");
		toRet.put("java.io.OutputStream","OUTPUT_STREAM");
		toRet.put("java.io.OutputStreamWriter","OUTPUT_STREAM_WRITER");
		toRet.put("java.io.PipedInputStream","PIPED_INPUT_STREAM");
		toRet.put("java.io.PipedOutputStream","PIPED_OUTPUT_STREAM");
		toRet.put("java.io.PipedReader","PIPED_READER");
		toRet.put("java.io.PipedWriter","PIPED_WRITER");
		toRet.put("java.io.PrintStream","PRINT_STREAM");
		toRet.put("java.io.PrintWriter","PRINT_WRITER");
		toRet.put("java.io.PushbackInputStream","PUSHBACK_INPUT_STREAM");
		toRet.put("java.io.PushbackReader","PUSHBACK_READER");
		toRet.put("java.io.RandomAccessFile","RANDOM_ACCESS_FILE");
		toRet.put("java.io.Reader","READER");
		toRet.put("java.io.SequenceInputStream","SEQUENCE_INPUT_STREAM");
		toRet.put("java.io.SerializablePermission","SERIALIZABLE_PERMISSION");
		toRet.put("java.io.StreamTokenizer","STREAM_TOKENIZER");
		toRet.put("java.io.StringBufferInputStream","STRING_BUFFER_INPUT_STREAM");
		toRet.put("java.io.StringReader","STRING_READER");
		toRet.put("java.io.StringWriter","STRING_WRITER");
		toRet.put("java.io.Writer","WRITER");
		toRet.put("java.io.CharConversionException","CHAR_CONVERSION_EXCEPTION");
		toRet.put("java.io.EOFException","EOFEXCEPTION");
		toRet.put("java.io.FileNotFoundException","FILE_NOT_FOUND_EXCEPTION");
		toRet.put("java.io.InterruptedIOException","INTERRUPTED_IO_EXCEPTION");
		toRet.put("java.io.InvalidClassException","INVALID_CLASS_EXCEPTION");
		toRet.put("java.io.InvalidObjectException","INVALID_OBJECT_EXCEPTION");
		toRet.put("java.io.IOException","IOEXCEPTION");
		toRet.put("java.io.NotActiveException","NOT_ACTIVE_EXCEPTION");
		toRet.put("java.io.NotSerializableException","NOT_SERIALIZABLE_EXCEPTION");
		toRet.put("java.io.ObjectStreamException","OBJECT_STREAM_EXCEPTION");
		toRet.put("java.io.OptionalDataException","OPTIONAL_DATA_EXCEPTION");
		toRet.put("java.io.StreamCorruptedException","STREAM_CORRUPTED_EXCEPTION");
		toRet.put("java.io.SyncFailedException","SYNC_FAILED_EXCEPTION");
		toRet.put("java.io.UncheckedIOException","UNCHECKED_IOEXCEPTION");
		toRet.put("java.io.UnsupportedEncodingException","UNSUPPORTED_ENCODING_EXCEPTION");
		toRet.put("java.io.UTFDataFormatException","UTFDATA_FORMAT_EXCEPTION");
		toRet.put("java.io.WriteAbortedException","WRITE_ABORTED_EXCEPTION");
		toRet.put("java.io.IOError","IOERROR");
		return Collections.unmodifiableMap(toRet);
	}
	
	private JSE_1_6_IO() {
		super.setDelegate(new NameOnlyReferenceGroup(CONSTANT_LOOKUP.keySet()));
	}

	@Override
	public String getPackageName() {
		return JAVA_IO;
	}

	@Override
	public String getConstantName(String javaName) {
		return CONSTANT_LOOKUP.get(javaName);
	}

	@Override
	public Map<String, String> getModelMap() {
		return CONSTANT_LOOKUP;
	};
}
