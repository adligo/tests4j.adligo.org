package org.adligo.tests4j.run;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.adligo.tests4j.models.shared.metadata.I_TestMetadata;
import org.adligo.tests4j.models.shared.metadata.TestMetadataMutant;
import org.adligo.tests4j.models.shared.metadata.TrialMetadataMutant;
import org.adligo.tests4j.models.shared.metadata.TrialRunMetadata;
import org.adligo.tests4j.models.shared.metadata.TrialRunMetadataMutant;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Logger;
import org.adligo.tests4j.models.shared.system.I_TrialRunListener;

public class MetadatNotifier {

	public static synchronized void sendMetadata(I_Tests4J_Logger log, Tests4J_Memory memory, I_TrialRunListener listener) {
		if (log.isEnabled()) {
			log.log("sendingMetadata.");
		}
		Iterator<TrialDescription> it = memory.getAllDescriptions();
		TrialRunMetadataMutant trmm = new TrialRunMetadataMutant();
		
		while (it.hasNext()) {
			TrialDescription td = it.next();
			TrialMetadataMutant tmm = new TrialMetadataMutant();
			tmm.setTrialName(td.getTrialName());
			Method before = td.getBeforeTrialMethod();
			if (before != null) {
				tmm.setBeforeTrialMethodName(before.getName());
			}
			boolean ignored = td.isIgnored();
			tmm.setSkipped(ignored);
			long timeout = td.getTimeout();
			tmm.setTimeout(timeout);
			
			List<TestDescription> tms = td.getTestMethods();
			if (tms != null) {
				Iterator<TestDescription> iit = tms.iterator();
				List<I_TestMetadata> testMetas = new ArrayList<I_TestMetadata>();
				while (iit.hasNext()) {
					TestDescription tm = iit.next();
					TestMetadataMutant testMeta = new TestMetadataMutant();
					Method method = tm.getMethod();
					testMeta.setTestName(method.getName());
					long testTimeout = tm.getTimeoutMillis();
					testMeta.setTimeout(testTimeout);
					testMetas.add(testMeta);
				}
				if (log.isEnabled()) {
					log.log("sendingMetadata trial " +tmm.getTrialName() + 
							" has " + testMetas.size() + " tests.");
				}
				tmm.setTests(testMetas);
			}
			Method after = td.getAfterTrialMethod();
			if (after != null) {
				tmm.setBeforeTrialMethodName(after.getName());
			}
			trmm.addTrial(tmm);
		}
		TrialRunMetadata toSend = new TrialRunMetadata(trmm);
		
		listener.onMetadataCalculated(toSend);
	}
}
