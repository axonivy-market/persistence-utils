package com.axonivy.utils.persistence.demo.tool.test.ui;

import java.io.Serializable;
import java.io.FileNotFoundException;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.axonivy.utils.persistence.demo.tool.test.service.TestService;


@Named(value = "test")
@ViewScoped
public class TestBean implements Serializable {

	public StreamedContent getDBExport() throws FileNotFoundException {

		StreamedContent content = DefaultStreamedContent.builder()
				.stream(() -> TestService.getDBExport())
				.name("testdata.xls")
				.contentType("application/vnd.ms-excel")
				.build();

		return content;
	}
}
