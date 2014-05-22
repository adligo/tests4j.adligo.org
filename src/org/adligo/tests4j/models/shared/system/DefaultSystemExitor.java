package org.adligo.tests4j.models.shared.system;

public class DefaultSystemExitor implements I_SystemExit {

	@Override
	public void doSystemExit(int p) {
		System.exit(p);
	}

}
