package org.adligo.tests4j.models.shared.reference_groups.jse;

import org.adligo.tests4j.models.shared.association.I_PackageConstantLookup;
import org.adligo.tests4j.models.shared.reference_groups.jse.v1_8.I_JSE_1_8_NIOFile;
import org.adligo.tests4j.shared.asserts.reference.NameOnlyReferenceGroup;
import org.adligo.tests4j.shared.asserts.reference.ReferenceGroupBaseDelegate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class represents the latest classes in java.io
 * for the latest version JSE version (1_8 on 10/1/2014),
 * allowing any method call/field reference.
 * 
 * partially generated by org.adligo.tests4j_gen.console.JSEGroupGen
 * copy/pasting...
 * Also this class should eventually include the entire api 
 * (public methods and fields), for assertion dependency.
 * 
 * @author scott
 *
 */
public class JSE_NIOFile extends ReferenceGroupBaseDelegate implements I_JSE_1_8_NIOFile, I_PackageConstantLookup {
	public static final String JAVA_NIO_FILE = "java.nio.file";
	private static final Map<String,String> CONSTANT_LOOKUP = getConstantLookup();
	public static final Set<String> CLASS_NAMES = CONSTANT_LOOKUP.keySet();
	public static final JSE_NIOFile INSTANCE = new JSE_NIOFile();
	
	private static Map<String,String> getConstantLookup() {
		Map<String,String> toRet = new HashMap<>();
    toRet.put("java.nio.file.CopyOption","COPY_OPTION");
    toRet.put("java.nio.file.DirectoryStream","DIRECTORY_STREAM");
    toRet.put("java.nio.file.DirectoryStream$Filter","FILTER");
    toRet.put("java.nio.file.FileVisitor","FILE_VISITOR");
    toRet.put("java.nio.file.OpenOption","OPEN_OPTION");
    toRet.put("java.nio.file.Path","PATH");
    toRet.put("java.nio.file.PathMatcher","PATH_MATCHER");
    toRet.put("java.nio.file.SecureDirectoryStream","SECURE_DIRECTORY_STREAM");
    toRet.put("java.nio.file.Watchable","WATCHABLE");
    toRet.put("java.nio.file.WatchEvent","WATCH_EVENT");
    toRet.put("java.nio.file.WatchEvent$Kind","KIND");
    toRet.put("java.nio.file.WatchEvent$Modifier","MODIFIER");
    toRet.put("java.nio.file.WatchKey","WATCH_KEY");
    toRet.put("java.nio.file.WatchService","WATCH_SERVICE");
    toRet.put("java.nio.file.Files","FILES");
    toRet.put("java.nio.file.FileStore","FILE_STORE");
    toRet.put("java.nio.file.FileSystem","FILE_SYSTEM");
    toRet.put("java.nio.file.FileSystems","FILE_SYSTEMS");
    toRet.put("java.nio.file.LinkPermission","LINK_PERMISSION");
    toRet.put("java.nio.file.Paths","PATHS");
    toRet.put("java.nio.file.SimpleFileVisitor","SIMPLE_FILE_VISITOR");
    toRet.put("java.nio.file.StandardWatchEventKinds","STANDARD_WATCH_EVENT_KINDS");
    toRet.put("java.nio.file.AccessMode","ACCESS_MODE");
    toRet.put("java.nio.file.FileVisitOption","FILE_VISIT_OPTION");
    toRet.put("java.nio.file.FileVisitResult","FILE_VISIT_RESULT");
    toRet.put("java.nio.file.LinkOption","LINK_OPTION");
    toRet.put("java.nio.file.StandardCopyOption","STANDARD_COPY_OPTION");
    toRet.put("java.nio.file.StandardOpenOption","STANDARD_OPEN_OPTION");
    toRet.put("java.nio.file.AccessDeniedException","ACCESS_DENIED_EXCEPTION");
    toRet.put("java.nio.file.AtomicMoveNotSupportedException","ATOMIC_MOVE_NOT_SUPPORTED_EXCEPTION");
    toRet.put("java.nio.file.ClosedDirectoryStreamException","CLOSED_DIRECTORY_STREAM_EXCEPTION");
    toRet.put("java.nio.file.ClosedFileSystemException","CLOSED_FILE_SYSTEM_EXCEPTION");
    toRet.put("java.nio.file.ClosedWatchServiceException","CLOSED_WATCH_SERVICE_EXCEPTION");
    toRet.put("java.nio.file.DirectoryIteratorException","DIRECTORY_ITERATOR_EXCEPTION");
    toRet.put("java.nio.file.DirectoryNotEmptyException","DIRECTORY_NOT_EMPTY_EXCEPTION");
    toRet.put("java.nio.file.FileAlreadyExistsException","FILE_ALREADY_EXISTS_EXCEPTION");
    toRet.put("java.nio.file.FileSystemAlreadyExistsException","FILE_SYSTEM_ALREADY_EXISTS_EXCEPTION");
    toRet.put("java.nio.file.FileSystemException","FILE_SYSTEM_EXCEPTION");
    toRet.put("java.nio.file.FileSystemLoopException","FILE_SYSTEM_LOOP_EXCEPTION");
    toRet.put("java.nio.file.FileSystemNotFoundException","FILE_SYSTEM_NOT_FOUND_EXCEPTION");
    toRet.put("java.nio.file.InvalidPathException","INVALID_PATH_EXCEPTION");
    toRet.put("java.nio.file.NoSuchFileException","NO_SUCH_FILE_EXCEPTION");
    toRet.put("java.nio.file.NotDirectoryException","NOT_DIRECTORY_EXCEPTION");
    toRet.put("java.nio.file.NotLinkException","NOT_LINK_EXCEPTION");
    toRet.put("java.nio.file.ProviderMismatchException","PROVIDER_MISMATCH_EXCEPTION");
    toRet.put("java.nio.file.ProviderNotFoundException","PROVIDER_NOT_FOUND_EXCEPTION");
    toRet.put("java.nio.file.ReadOnlyFileSystemException","READ_ONLY_FILE_SYSTEM_EXCEPTION");
		return Collections.unmodifiableMap(toRet);
	}
	
	private JSE_NIOFile() {
		super.setDelegate(new NameOnlyReferenceGroup(CONSTANT_LOOKUP.keySet()));
	}

	@Override
	public String getPackageName() {
		return JAVA_NIO_FILE;
	}

	@Override
	public String getConstantName(String javaName) {
		return CONSTANT_LOOKUP.get(javaName);
	};
}
