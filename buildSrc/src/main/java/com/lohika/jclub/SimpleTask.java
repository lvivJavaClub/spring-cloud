package com.lohika.jclub;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.Input;

public class SimpleTask extends DefaultTask {

	private String msg;

	@TaskAction
	public void msg() {
		System.out.println("Print: " + msg);
	}

	@Input
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
