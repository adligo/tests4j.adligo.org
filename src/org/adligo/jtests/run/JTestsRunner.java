package org.adligo.jtests.run;

import org.adligo.jtests.base.shared.I_AssertCommand;
import org.adligo.jtests.base.shared.I_AbstractTest;
import org.adligo.jtests.base.shared.I_Runner;
import org.adligo.jtests.base.shared.results.Failure;

public class JTestsRunner implements I_Runner {

	public static void main(String[] args) {
		
	}
	
	@Override
	public void startTest(Class<? extends I_AbstractTest> p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeTestCompleted(String output) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeExhibitsCompleted(String output) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exhibitStarting(String exhibitName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void assertCompleted(I_AssertCommand cmd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void assertFailed(Failure failure) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exceptionThrown(Throwable exception) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exhibitCompleted(String output) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterExhibitsCompleted(String output) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterTestCompleted(String output) {
		// TODO Auto-generated method stub
		
	}

}
