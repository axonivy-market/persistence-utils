package com.axonivy.utils.persistence.test.util;

import java.util.Locale;

import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.workflow.IWorkflowSession;

/**
 * Temporary locale setter.
 */
public class LocaleSetter implements AutoCloseable {
	private Locale orgContent;
	private Locale orgFormat;
	private IWorkflowSession session;

	private LocaleSetter(Locale locale) {
		session = Ivy.session();
		orgContent = session.getContentLocale();
		orgFormat = session.getFormattingLocale();
		session.setContentLocale(locale);
		session.setFormattingLocale(locale);

	}

	public static LocaleSetter set(Locale locale) {
		return new LocaleSetter(locale);
	}

	@Override
	public void close() {
		session.setContentLocale(orgContent);
		session.setFormattingLocale(orgFormat);
	}
}