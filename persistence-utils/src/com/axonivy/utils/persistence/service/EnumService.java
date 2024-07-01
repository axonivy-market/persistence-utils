package com.axonivy.utils.persistence.service;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.axonivy.utils.persistence.enums.HasCmsName;

import ch.ivyteam.ivy.environment.Ivy;


public class EnumService {

	private EnumService() {}

	/**
	 * A comparator comparing two Strings, e.g. CMS values.
	 * 
	 * <p>
	 * The comparator is case insensitive and null-safe.
	 * </p>
	 * 
	 * @return
	 */
	public static Comparator<String> ciComparator() {
		return (left, right) -> nullToEmpty(left).compareToIgnoreCase(nullToEmpty(right));
	}

	/**
	 * A comparator comparing two Strings in the session locale.
	 * 
	 * @return
	 */
	public static Comparator<String> localeAwareCIComparator() {
		Collator collator = Collator.getInstance(Ivy.session().getContentLocale());
		return (left, right) -> collator.compare(left, right);
	}

	/**
	 * Get a list of {@link Map.Entry} objects sorted by CmsName.
	 * 
	 * This function should typically be called with the values() of enumerations.
	 * 
	 * @param <T>
	 * @param a
	 * @return
	 */
	public static <T extends HasCmsName> List<Map.Entry<T, String>> getSortedByCmsName(T a[], Comparator<String> cmsComparator) {
		return Stream
				.of(a)
				.map(e -> Map.entry(e, e.getCmsName()))
				.sorted((l, r) -> cmsComparator.compare(l.getValue(), r.getValue()))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	/**
	 * Get a list of {@link Map.Entry} objects sorted by CmsName.
	 * 
	 * This function should typically be called with the values() of enumerations.
	 * 
	 * @param <T>
	 * @param a
	 * @return
	 */
	public static <T extends HasCmsName> List<Map.Entry<T, String>> getSortedByCmsName(T a[]) {
		return getSortedByCmsName(a, ciComparator());
	}

	/**
	 * Get a list of {@link Map.Entry} objects sorted by CmsName.
	 *
	 * This function must be called with an enumeration implementing {@link HasCmsName}.
	 *
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	public static <T extends HasCmsName> List<Map.Entry<T, String>> getSortedByCmsName(Class<T> clazz) {
		return getSortedByCmsName(clazz.getEnumConstants());
	}


	/**
	 * Get a list of annotated {@link Map.Entry} objects of a list of {@link HasCmsName}.
	 * 
	 * This function should typically be called with the values() of enumerations.
	 * 
	 * @param <R>
	 * @param a
	 * @param mapper
	 * @return
	 */
	public static <R> List<Map.Entry<HasCmsName, R>> getAttributedList(HasCmsName a[], Function<HasCmsName, R> mapper) {
		return Stream.of(a).map(e -> Map.entry(e, mapper.apply(e))).collect(Collectors.toCollection(ArrayList::new));
	}

	private static String nullToEmpty(String which) {
		return which != null ? which : "";
	}
}
