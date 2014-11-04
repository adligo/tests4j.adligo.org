package org.adligo.tests4j.models.shared.reference_groups.jse;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.adligo.tests4j.models.shared.association.I_PackageConstantLookup;
import org.adligo.tests4j.models.shared.reference_groups.jse.v1_8.I_JSE_1_8_Sql;
import org.adligo.tests4j.shared.asserts.reference.NameOnlyReferenceGroup;
import org.adligo.tests4j.shared.asserts.reference.ReferenceGroupBaseDelegate;


/**
 * This class represents the latest classes in java.io
 * for the latest version JSE version (1_8 on 10/1/2014),
 * allowing any method call/field reference.
 * 
 * partially generated by org.adligo.tests4j_gen.PackageClassNameWriter
 * copy/pasting...
 * Also this class should eventually include the entire api 
 * (public methods and fields), for assertion dependency.
 * 
 * @author scott
 *
 */
public class JSE_Sql extends ReferenceGroupBaseDelegate implements I_JSE_1_8_Sql, I_PackageConstantLookup {
	public static final String JAVA_SQL = "java.sql";
	private static final Map<String,String> CONSTANT_LOOKUP = getConstantLookup();
	public static final Set<String> CLASS_NAMES = CONSTANT_LOOKUP.keySet();
	public static final JSE_Sql INSTANCE = new JSE_Sql();
	
	private static Map<String,String> getConstantLookup() {
		Map<String,String> toRet = new HashMap<>();
		toRet.put("java.sql.Array","ARRAY");
		toRet.put("java.sql.Blob","BLOB");
		toRet.put("java.sql.CallableStatement","CALLABLE_STATEMENT");
		toRet.put("java.sql.Clob","CLOB");
		toRet.put("java.sql.Connection","CONNECTION");
		toRet.put("java.sql.DatabaseMetaData","DATABASE_META_DATA");
		toRet.put("java.sql.Driver","DRIVER");
		toRet.put("java.sql.DriverAction","DRIVER_ACTION");
		toRet.put("java.sql.NClob","NCLOB");
		toRet.put("java.sql.ParameterMetaData","PARAMETER_META_DATA");
		toRet.put("java.sql.PreparedStatement","PREPARED_STATEMENT");
		toRet.put("java.sql.Ref","REF");
		toRet.put("java.sql.ResultSet","RESULT_SET");
		toRet.put("java.sql.ResultSetMetaData","RESULT_SET_META_DATA");
		toRet.put("java.sql.RowId","ROW_ID");
		toRet.put("java.sql.Savepoint","SAVEPOINT");
		toRet.put("java.sql.SQLData","SQLDATA");
		toRet.put("java.sql.SQLInput","SQLINPUT");
		toRet.put("java.sql.SQLOutput","SQLOUTPUT");
		toRet.put("java.sql.SQLType","SQLTYPE");
		toRet.put("java.sql.SQLXML","SQLXML");
		toRet.put("java.sql.Statement","STATEMENT");
		toRet.put("java.sql.Struct","STRUCT");
		toRet.put("java.sql.Wrapper","WRAPPER");
		toRet.put("java.util.Date","DATE");
		toRet.put("java.sql.DriverManager","DRIVER_MANAGER");
		toRet.put("java.sql.DriverPropertyInfo","DRIVER_PROPERTY_INFO");
		toRet.put("java.sql.SQLPermission","SQLPERMISSION");
		toRet.put("java.sql.Time","TIME");
		toRet.put("java.sql.Timestamp","TIMESTAMP");
		toRet.put("java.sql.Types","TYPES");
		toRet.put("java.sql.ClientInfoStatus","CLIENT_INFO_STATUS");
		toRet.put("java.sql.JDBCType","JDBCTYPE");
		toRet.put("java.sql.PseudoColumnUsage","PSEUDO_COLUMN_USAGE");
		toRet.put("java.sql.RowIdLifetime","ROW_ID_LIFETIME");
		toRet.put("java.sql.BatchUpdateException","BATCH_UPDATE_EXCEPTION");
		toRet.put("java.sql.DataTruncation","DATA_TRUNCATION");
		toRet.put("java.sql.SQLClientInfoException","SQLCLIENT_INFO_EXCEPTION");
		toRet.put("java.sql.SQLDataException","SQLDATA_EXCEPTION");
		toRet.put("java.sql.SQLException","SQLEXCEPTION");
		toRet.put("java.sql.SQLFeatureNotSupportedException","SQLFEATURE_NOT_SUPPORTED_EXCEPTION");
		toRet.put("java.sql.SQLIntegrityConstraintViolationException","SQLINTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION");
		toRet.put("java.sql.SQLInvalidAuthorizationSpecException","SQLINVALID_AUTHORIZATION_SPEC_EXCEPTION");
		toRet.put("java.sql.SQLNonTransientConnectionException","SQLNON_TRANSIENT_CONNECTION_EXCEPTION");
		toRet.put("java.sql.SQLNonTransientException","SQLNON_TRANSIENT_EXCEPTION");
		toRet.put("java.sql.SQLRecoverableException","SQLRECOVERABLE_EXCEPTION");
		toRet.put("java.sql.SQLSyntaxErrorException","SQLSYNTAX_ERROR_EXCEPTION");
		toRet.put("java.sql.SQLTimeoutException","SQLTIMEOUT_EXCEPTION");
		toRet.put("java.sql.SQLTransactionRollbackException","SQLTRANSACTION_ROLLBACK_EXCEPTION");
		toRet.put("java.sql.SQLTransientConnectionException","SQLTRANSIENT_CONNECTION_EXCEPTION");
		toRet.put("java.sql.SQLTransientException","SQLTRANSIENT_EXCEPTION");
		toRet.put("java.sql.SQLWarning","SQLWARNING");
		return Collections.unmodifiableMap(toRet);
	}
	
	
	private JSE_Sql() {
		super.setDelegate(new NameOnlyReferenceGroup(CONSTANT_LOOKUP.keySet()));
	}


	@Override
	public String getPackageName() {
		return JAVA_SQL;
	}


	@Override
	public String getConstantName(String javaName) {
		return CONSTANT_LOOKUP.get(javaName);
	};
}
