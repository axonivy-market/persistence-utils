package com.axonivy.utils.persistence.search;

import com.axonivy.utils.persistence.StringUtilities;


/**
 * A single predicate of a filter.
 *
 * Predicates are simple enumerations with one or more values. They need to be converted
 * to {@link AttributePredicates} to be usable in a search. Most predicates will only
 * have a single value, but sometimes it is necessary to have more (eg. for between queries).
 */
public class FilterPredicate {

	private Enum<?> searchEnum;
	private String serializedValue;
	private SearchFilter searchFilter;

	/**
	 * Static marker for the special value "NOT NULL".
	 */
	public static final String NOT_NULL = "NOT NULL";

	protected FilterPredicate() {
	}

	/**
	 * Construct predicate without value.
	 *
	 * @param searchEnum enumeration to identify filter
	 */
	public FilterPredicate(Enum<?> searchEnum) {
		this(searchEnum, null);
	}

	/**
	 * Construct predicate.
	 *
	 * @param searchEnum enumeration to identify filter
	 * @param value use for queries
	 */
	public FilterPredicate(Enum<?> searchEnum, Object value) {
		this.searchEnum = searchEnum;
		serializedValue = pack(value);
	}

	/**
	 * Create a copy.
	 * 
	 * @return
	 */
	public FilterPredicate copy() {
		var predicate = new FilterPredicate();
		predicate.searchEnum = this.searchEnum;
		predicate.serializedValue = this.serializedValue;
		return predicate;
	}

	/**
	 * Get the filter enumeration.
	 *
	 * @return the search enum
	 */
	@SuppressWarnings("rawtypes")
	public Enum getSearchEnum() {
		return searchEnum;
	}

	/**
	 * Convenience function for unpacking a single String.
	 *
	 * @return the value
	 */
	public String getValue() {
		return unpack(String.class);
	}

	/**
	 * Get the value of this {@link FilterPredicate}.
	 *
	 * It is ok to pass array types. For example, to get an array of enums, you could use:
	 *
	 * <pre>
	 * <code>
	 *   MyEnum[] values = filterPredicate.getValue(MyEnum[].class);
	 * </code>
	 * </pre>
	 *
	 * Of course the type requested must match the type stored before.
	 *
	 * @param clazz class
	 * @param <T> the type of the represented object
	 * @return converted class
	 */
	public <T> T getValue(Class<T> clazz) {
		return unpack(clazz);
	}

	/**
	 * Has this predicate an associated value?
	 *
	 * @return boolean
	 */
	public boolean hasValue() {
		return serializedValue != null;
	}

	/**
	 * Get the search filter this predicate is used in.
	 * 
	 * @return {@link SearchFilter} or <code>null</code> if unsused in a {@link SearchFilter}.
	 */
	public SearchFilter getSearchFilter() {
		return this.searchFilter;
	}

	void setSearchFilter(SearchFilter searchFilter) {
		this.searchFilter = searchFilter;
	}

	/**
	 * Gets the serialized value.
	 *
	 * @return the serializedValue
	 */
	protected String getSerializedValue() {
		return serializedValue;
	}

	/**
	 * Serialize value into Json string
	 *
	 * @param value The value to be serialized
	 * @return
	 */
	protected String pack(Object value) {
		return StringUtilities.fromObjectToJSON(value);
	}

	/**
	 *
	 * @return
	 */
	protected Object unpack() {
		return unpack(Object.class);
	}

	/**
	 * Get a value from the filter.
	 *
	 * @param clazz
	 * @return converted class
	 */
	protected <T extends Object> T unpack(Class<T> clazz) {
		return StringUtilities.fromJSONToObject(getSerializedValue(),clazz);
	}

	/**
	 * See {@link Object#toString()}.
	 *
	 */
	@Override
	public String toString() {
		return "FilterPredicate [searchEnum=%s, value=%s]".formatted(searchEnum, serializedValue);
	}
}