package com.axonivy.utils.persistence.validation;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.application.FacesMessage.Severity;
import jakarta.faces.context.FacesContext;
import jakarta.validation.ConstraintViolation;

import org.apache.logging.log4j.Level;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.axonivy.utils.persistence.demo.Logger;
import com.axonivy.utils.persistence.demo.service.UiService;
import com.axonivy.utils.persistence.demo.service.ValidationService;
import com.axonivy.utils.persistence.test.DemoTestBase;
import com.axonivy.utils.persistence.test.mock.SimpleFacesContext;
import com.axonivy.utils.persistence.test.service.LogService;
import com.axonivy.utils.persistence.validation.groups.Warning;

import ch.ivyteam.ivy.environment.AppFixture;
import ch.ivyteam.ivy.environment.IvyTest;


@IvyTest
public class ValidationTest extends DemoTestBase {
	private static final Logger LOG = Logger.getLogger(ValidationTest.class);

	public interface Step1WarnGroup extends Warning {}
	public interface Step1ErrorGroup extends com.axonivy.utils.persistence.validation.groups.Error {}

	public interface Step2WarnGroup extends Warning {}
	public interface Step2ErrorGroup extends com.axonivy.utils.persistence.validation.groups.Error {}


	class Test1 {
		@IvyNotNull(groups = {Step1WarnGroup.class, Step2ErrorGroup.class}, message = "Field1 cannot be null", payload = {})
		private String field1;

		@IvyNotNull(groups = {Step1WarnGroup.class, Step2ErrorGroup.class}, message = "Field2 cannot be null", payload = {})
		private String field2;

		public String getField1() {
			return field1;
		}

		public void setField1(String field1) {
			this.field1 = field1;
		}

		public String getField2() {
			return field2;
		}

		public void setField2(String field2) {
			this.field2 = field2;
		}
	}

	@Test
	public void testMixedGroups(AppFixture fixture) {

		Mockito.mockStatic(UiService.class, i -> i.getMethod().getName().equals("findComponent") ? null : i.callRealMethod());

		LogService.get().consoleLog(Level.INFO);

		try(MockedStatic<FacesContext> mocked = Mockito.mockStatic(FacesContext.class)) {
			SimpleFacesContext facesContext = new SimpleFacesContext();
			mocked.when(FacesContext::getCurrentInstance).thenReturn(facesContext);

			Test1 test1 = new Test1();
			Set<ConstraintViolation<Test1>> violations;
			Severity severity;

			LOG.info("Testing validations for step 1");
			violations = ValidationService.validate(test1, Step1WarnGroup.class, Step1ErrorGroup.class);
			assertThat(violations).as("Step 1 violations").hasSize(2);
			facesContext.resetFacesMessages();
			severity = ValidationService.validate2FacesMessages(test1, Step1WarnGroup.class, Step1ErrorGroup.class);
			LOG.info("Severity: {0}", severity);

			assertFacesMessages(
					facesContext,
					"Faces Messages from step 1",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Value is missing."),
					new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Value is missing."));

			LOG.info("Testing validations for step 2");
			violations = ValidationService.validate(test1, Step2WarnGroup.class, Step2ErrorGroup.class);
			assertThat(violations).as("Step 2 violations").hasSize(2);
			facesContext.resetFacesMessages();
			severity = ValidationService.validate2FacesMessages(test1,  Step2WarnGroup.class, Step2ErrorGroup.class);
			LOG.info("Severity: {0}", severity);

			assertFacesMessages(
					facesContext,
					"Faces Messages from step 2",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Value is missing."),
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Value is missing."));
		}
	}
}
