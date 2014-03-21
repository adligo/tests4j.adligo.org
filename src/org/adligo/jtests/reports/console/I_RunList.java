package org.adligo.jtests.reports.console;

import java.util.List;

import org.adligo.jtests.models.shared.I_AbstractTrial;

public interface I_RunList {
	public List<Class<? extends I_AbstractTrial>> getTrials();
	public int getMinTests();
	public int getMinUniqueAssertions();
	public int getMinAssertions();
}
