package com.axonivy.utils.persistence.helper;

import java.util.Locale;

import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.workflow.IWorkflowSession;

/**
 * Set session language within a try-with-reousrce block.
 */
public class SessionLocale implements AutoCloseable {
	private IWorkflowSession session;
	private Locale contentLocale;
	private Locale formattingLocale;

	/**
	 * Set the session content and formatting locale which will be reset calling close.
	 * 
	 * @param locale
	 * @return
	 */
	public static SessionLocale get(Locale locale) {
		return new SessionLocale(locale);
	}

	private SessionLocale(Locale locale) {
		session = Ivy.session();
		contentLocale = session.getContentLocale();
		formattingLocale = session.getFormattingLocale();

		session.setContentLocale(locale);
		session.setFormattingLocale(locale);
	}

	@Override
	public void close() {
		session.setContentLocale(contentLocale);
		session.setFormattingLocale(formattingLocale);
	}
}
