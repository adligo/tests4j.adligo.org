package org.adligo.tests4j.models.shared.dependency_groups.adligo;

import java.util.HashSet;
import java.util.Set;

import org.adligo.tests4j.shared.output.ByteListOutputStream;
import org.adligo.tests4j.shared.output.DefaultLog;
import org.adligo.tests4j.shared.output.DelegatingLog;
import org.adligo.tests4j.shared.output.I_ConcurrentOutputDelegator;
import org.adligo.tests4j.shared.output.I_OutputBuffer;
import org.adligo.tests4j.shared.output.I_OutputDelegateor;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;
import org.adligo.tests4j.shared.output.ListDelegateOutputBuffer;
import org.adligo.tests4j.shared.output.PrintStreamOutputBuffer;
import org.adligo.tests4j.shared.output.SafeOutputStreamBuffer;

public class Tests4J_Output_DependencyGroup extends Tests4J_DependencyGroup {

	public Tests4J_Output_DependencyGroup() {
		Set<String> names = new HashSet<String>();
		
		add(names, ByteListOutputStream.class);
		add(names, DefaultLog.class);
		add(names, DelegatingLog.class);
		
		add(names, I_ConcurrentOutputDelegator.class);
		add(names, I_OutputBuffer.class);
		add(names, I_OutputBuffer.class);
		add(names, I_OutputDelegateor.class);
		add(names, I_Tests4J_Log.class);
		add(names, ByteListOutputStream.class);
		
		add(names, ListDelegateOutputBuffer.class);
		add(names, PrintStreamOutputBuffer.class);
		add(names, SafeOutputStreamBuffer.class);
		
		Tests4J_Common_DependencyGroup dg = new Tests4J_Common_DependencyGroup();
		names.addAll(dg.getClassNames());
		
		setupDelegates(names);
	}
	
	private void add(Set<String> names, Class<?> c) {
		names.add(c.getName());
	}
}
