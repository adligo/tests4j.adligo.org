package org.adligo.tests4j.models.shared.dependency_groups.adligo;

import java.util.HashSet;
import java.util.Set;

import org.adligo.tests4j.models.shared.asserts.line_text.DiffIndexes;
import org.adligo.tests4j.models.shared.asserts.line_text.DiffIndexesMutant;
import org.adligo.tests4j.models.shared.asserts.line_text.DiffIndexesPair;
import org.adligo.tests4j.models.shared.asserts.line_text.I_DiffIndexes;
import org.adligo.tests4j.models.shared.asserts.line_text.I_DiffIndexesPair;
import org.adligo.tests4j.models.shared.asserts.line_text.I_LineDiff;
import org.adligo.tests4j.models.shared.asserts.line_text.I_LineDiffType;
import org.adligo.tests4j.models.shared.asserts.line_text.I_TextLines;
import org.adligo.tests4j.models.shared.asserts.line_text.I_TextLinesCompareResult;
import org.adligo.tests4j.models.shared.asserts.line_text.LineDiff;
import org.adligo.tests4j.models.shared.asserts.line_text.LineDiffMutant;
import org.adligo.tests4j.models.shared.asserts.line_text.LineDiffType;
import org.adligo.tests4j.models.shared.asserts.line_text.LineSplitter;
import org.adligo.tests4j.models.shared.asserts.line_text.TextLines;
import org.adligo.tests4j.models.shared.asserts.line_text.TextLinesCompare;
import org.adligo.tests4j.models.shared.asserts.line_text.TextLinesCompareResult;

public class Tests4J_AssertsLineText_DependencyGroup extends Tests4J_DependencyGroup {

	public Tests4J_AssertsLineText_DependencyGroup() {
		Set<String> names = new HashSet<String>();
		
		add(names, DiffIndexes.class);
		add(names, DiffIndexesMutant.class);
		add(names, DiffIndexesPair.class);
		
		add(names, I_DiffIndexes.class);
		add(names, I_DiffIndexesPair.class);
		
		add(names, I_LineDiff.class);
		add(names, I_LineDiffType.class);
		
		add(names, I_TextLines.class);
		add(names, I_TextLinesCompareResult.class);
		
		add(names, LineDiff.class);
		add(names, LineDiffMutant.class);
		
		add(names, LineDiffType.class);
		add(names, LineSplitter.class);
		
		add(names, TextLines.class);
		add(names, TextLinesCompare.class);
		add(names, TextLinesCompareResult.class);
		
		Tests4J_AssertsCommon_DependencyGroup dg = new Tests4J_AssertsCommon_DependencyGroup();
		names.addAll(dg.getClassNames());
		
		setupDelegates(names);
	}
	
	private void add(Set<String> names, Class<?> c) {
		names.add(c.getName());
	}
}
