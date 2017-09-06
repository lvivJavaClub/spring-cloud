package com.lohika.jclub;

import org.junit.Test;
import org.gradle.api.*;
import org.gradle.testfixtures.*;

import static org.junit.Assert.assertTrue;

public class SimpleTaskTest {

	@Test
	public void testPrintMsgTask() {
		Project project = ProjectBuilder.builder().build()
		def task = project.task('printMsg', type: SimpleTask)
		task.msg()
		assertTrue(task instanceof SimpleTask)
	}

}
