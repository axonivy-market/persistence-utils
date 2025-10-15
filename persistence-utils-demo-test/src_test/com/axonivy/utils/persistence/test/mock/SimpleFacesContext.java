package com.axonivy.utils.persistence.test.mock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jakarta.faces.application.Application;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.application.FacesMessage.Severity;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ResponseStream;
import jakarta.faces.context.ResponseWriter;
import jakarta.faces.lifecycle.Lifecycle;
import jakarta.faces.render.RenderKit;

import org.mockito.Mockito;


public class SimpleFacesContext extends FacesContext {

	private List<FacesMessageEntry> facesMessages = new ArrayList<>();
	private boolean validationFailed;

	@Override
	public void addMessage(String clientId, FacesMessage message) {
		facesMessages.add(new FacesMessageEntry(clientId, message));
	}

	@Override
	public Iterator<FacesMessage> getMessages() {
		return facesMessages.stream()
				.map(e -> e.getFacesMessage())
				.iterator();
	}

	@Override
	public Iterator<FacesMessage> getMessages(String clientId) {
		return facesMessages.stream()
				.filter(e -> e.getClientId().equals(clientId))
				.map(e -> e.getFacesMessage())
				.iterator();
	}

	@Override
	public List<FacesMessage> getMessageList() {
		List<FacesMessage> result = new ArrayList<>();
		getMessages().forEachRemaining(result::add);
		return result;
	}

	@Override
	public List<FacesMessage> getMessageList(String clientId) {
		List<FacesMessage> result = new ArrayList<>();
		getMessages(clientId).forEachRemaining(result::add);
		return result;
	}

	@Override
	public void validationFailed() {
		validationFailed = true;
	}

	@Override
	public boolean isValidationFailed() {
		return validationFailed;
	}

	public void resetFacesMessages() {
		facesMessages.clear();
		resetValidationFailed();
	}

	public void resetValidationFailed() {
		validationFailed = false;
	}

	@Override
	public UIViewRoot getViewRoot() {
		return null;
	}

	@Override
	public Application getApplication() {
		throw new NotMockedException();
	}

	@Override
	public Iterator<String> getClientIdsWithMessages() {
		throw new NotMockedException();	}

	@Override
	public ExternalContext getExternalContext() {
		return Mockito.mock(ExternalContext.class);
	}

	@Override
	public Severity getMaximumSeverity() {
		throw new NotMockedException();	}

	@Override
	public RenderKit getRenderKit() {
		throw new NotMockedException();
	}

	@Override
	public boolean getRenderResponse() {
		throw new NotMockedException();
	}

	@Override
	public boolean getResponseComplete() {
		throw new NotMockedException();
	}

	@Override
	public ResponseStream getResponseStream() {
		throw new NotMockedException();
	}

	@Override
	public ResponseWriter getResponseWriter() {
		throw new NotMockedException();
	}

	@Override
	public void release() {
		throw new NotMockedException();
	}

	@Override
	public void renderResponse() {
		throw new NotMockedException();
	}

	@Override
	public void responseComplete() {
		throw new NotMockedException();
	}

	@Override
	public void setResponseStream(ResponseStream responseStream) {
		throw new NotMockedException();
	}

	@Override
	public void setResponseWriter(ResponseWriter responseWriter) {
		throw new NotMockedException();
	}

	@Override
	public void setViewRoot(UIViewRoot root) {
		throw new NotMockedException();
	}
	
	@Override
	public Lifecycle getLifecycle() {
		throw new NotMockedException();
	}

	private class FacesMessageEntry {
		String clientId;
		FacesMessage facesMessage;
		public FacesMessageEntry(String clientId, FacesMessage facesMessage) {
			this.clientId = clientId;
			this.facesMessage = facesMessage;
		}
		public String getClientId() {
			return clientId;
		}
		public FacesMessage getFacesMessage() {
			return facesMessage;
		}
	}
}
