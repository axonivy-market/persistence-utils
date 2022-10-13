package com.axonivy.utils.persistence.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Support for working with dates.
 */
public class DateService {
	private DateService() {};

	/**
	 * Convert a {@link Date} to a {@link LocalDateTime}.
	 *
	 * @param date
	 * @return
	 */
	public static LocalDateTime toLocalDateTime(Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}


	/**
	 * Convert a {@link Date} to a {@link LocalDate}.
	 *
	 * @param date
	 * @return
	 */
	public static LocalDate convertToLocalDate(Date date) {
		return LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	/**
	 * Convert a {@link Date} to a {@link LocalTime}.
	 *
	 * @param date
	 * @return
	 */
	public static LocalTime convertToLocalTime(Date date) {
		return LocalTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	/**
	 * Convert a LocalDateTime to Date.
	 *
	 * @param date
	 * @return
	 */
	public static Date toDate(LocalDateTime date) {
		return Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * Convert a LocalDate to Date.
	 *
	 * @param date
	 * @return
	 */
	public static Date toDate(LocalDate date) {
		return Date
				.from(date.atStartOfDay()
						.atZone(ZoneId.systemDefault())
						.toInstant());
	}

	/**
	 * Convert a LocalTime to Date.
	 *
	 * @param date
	 * @return
	 */
	public static Date toDate(LocalTime time) {
		return Date.from(time.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant());
	}
}
