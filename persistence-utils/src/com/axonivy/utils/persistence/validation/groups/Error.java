package com.axonivy.utils.persistence.validation.groups;

import jakarta.faces.application.FacesMessage;

/**
 * Validation groups inheriting from this interface will generate {@link FacesMessage#SEVERITY_ERROR}.
 */
public interface Error extends BaseGroup {

}
