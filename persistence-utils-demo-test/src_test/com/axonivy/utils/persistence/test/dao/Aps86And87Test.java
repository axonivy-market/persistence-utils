package com.axonivy.utils.persistence.test.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.axonivy.utils.persistence.beans.Header;
import com.axonivy.utils.persistence.daos.OptionDAO;
import com.axonivy.utils.persistence.entities.Option;

import ch.ivyteam.ivy.environment.IvyTest;

@IvyTest
public class Aps86And87Test {
	
	private Option option;
	
	@BeforeEach
	public void prepare() {
		option = new Option();
		option.setName("myOption");
		option.setValue("myValue");
		option.setExpiry(Instant.now().plus(1, ChronoUnit.HOURS));
		
		option = OptionDAO.get().save(option);
		option = OptionDAO.get().delete(option);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test1() {
		assertThat(option).as("Prepared test").isNotNull();
		Header header = option.getHeader();
		
		// test if dates are set
		assertThat(header.getCreatedDate()).as("creation date set").isNotNull();
		assertThat(header.getModifiedDate()).as("last modification date set").isNotNull();
		assertThat(header.getFlaggedDeletedDate()).as("deletion date set").isNotNull();
		assertThat(option.getExpiry()).as("expiry set").isNotNull();
		
		// test conversion methods
		assertThat(header.getCreatedDateAsDate()).as("creation date converted to Date")
			.isEqualTo(Date.from(header.getCreatedDate()));
		assertThat(header.getCreatedDateAsLocalDateTime()).as("creation date converted to LocalDateTime")
			.isEqualTo(convertToLocalDateTime(header.getCreatedDate()));
		
		assertThat(header.getModifiedDateAsDate()).as("last modification date converted to Date")
			.isEqualTo(Date.from(header.getModifiedDate()));
		assertThat(header.getModifiedDateAsLocalDateTime()).as("last modification date converted to LocalDateTime")
			.isEqualTo(convertToLocalDateTime(header.getModifiedDate()));
		
		assertThat(header.getFlaggedDeletedDateAsDate()).as("deletion date converted to Date")
			.isEqualTo(Date.from(header.getFlaggedDeletedDate()));
		assertThat(header.getFlaggedDeletedDateAsLocalDateTime()).as("deletion date converted to LocalDateTime")
			.isEqualTo(convertToLocalDateTime(header.getFlaggedDeletedDate()));
		
		assertThat(option.getExpiryAsDate()).as("expriy date converted to Date")
			.isEqualTo(Date.from(option.getExpiry()));
		assertThat(option.getExpiryAsLocalDateTime()).as("expiry date converted to LocalDateTime")
			.isEqualTo(convertToLocalDateTime(option.getExpiry()));
		
		// setter methods
		LocalDateTime nowLDT = LocalDateTime.now();
		header.setCreateDateAsLocalDateTime(nowLDT);
		header.setModifiedDateAsLocalDateTime(nowLDT);
		header.setFlaggedDeletedDateAsLocalDateTime(nowLDT);
		option.setExpiryAsLocalDateTime(nowLDT);
		
		assertThat(header.getCreatedDate()).as("creation date set by LocalDateTime")
			.isEqualTo(convertToInstant(nowLDT));
		assertThat(header.getModifiedDate()).as("last modification date set by LocalDateTime")
			.isEqualTo(convertToInstant(nowLDT));
		assertThat(header.getFlaggedDeletedDate()).as("last modification date set by LocalDateTime")
			.isEqualTo(convertToInstant(nowLDT));
		assertThat(option.getExpiry()).as("expiry date set by LocalDateTime")
			.isEqualTo(convertToInstant(nowLDT));
		
		Date date = new Date();
		header.setCreateDateAsDate(date);
		header.setModifiedDateAsDate(date);
		header.setFlaggedDeletedDateAsDate(date);
		option.setExpiryAsDate(date);
		
		assertThat(header.getCreatedDate()).as("creation date set by Date")
			.isEqualTo(date.toInstant());
		assertThat(header.getModifiedDate()).as("last modification date set by Date")
			.isEqualTo(date.toInstant());
		assertThat(header.getFlaggedDeletedDate()).as("last modification date set by Date")
			.isEqualTo(date.toInstant());
		assertThat(option.getExpiry()).as("expiry date set by Date")
			.isEqualTo(date.toInstant());
	}
	
	@Test
	public void test2() {
		assertThat(option.isEnabled()).as("initially enabled").isTrue();
		assertThat(option.isActive()).as("initially enabled").isTrue();
		assertThat(option.isExpired()).as("initially not expired").isFalse();
		option.setEnabled(false);
		assertThat(option.isEnabled()).as("disabled").isFalse();
		assertThat(option.isActive()).as("active").isFalse();
		option.setEnabled(true);
		option.setExpiry(Instant.now().minus(1, ChronoUnit.HOURS));
		assertThat(option.isEnabled()).as("enabled").isTrue();
		assertThat(option.isExpired()).as("expired").isTrue();
		assertThat(option.isActive()).as("not active").isFalse();
	}
	

	private LocalDateTime convertToLocalDateTime(Instant instant) {
		return LocalDateTime.ofInstant(instant, ZoneOffset.systemDefault());
	}
	
	private Instant convertToInstant(LocalDateTime localDateTime) {
		return localDateTime.atZone(ZoneId.systemDefault()).toInstant();
	}

}
