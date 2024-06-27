package com.axonivy.utils.persistence.search;

/**
 * A single order of a filter.
 */
public class FilterOrder {

	private Enum<?> searchEnum;
	private boolean ascending;
	private SearchFilter searchFilter;

	/**
	 * Constructor
	 */
	public FilterOrder() { // FilterOrder
	}

	/**
	 * Construct order.
	 *
	 * @param searchEnum enumeration to identify filter
	 * @param ascending the new ascending
	 */
	public FilterOrder(Enum<?> searchEnum, boolean ascending) {
		this.searchEnum = searchEnum;
		this.ascending = ascending;
	}

	/**
	 * Create a copy.
	 * 
	 * @return
	 */
	public FilterOrder copy() {
		var order = new FilterOrder();
		order.searchEnum = this.searchEnum;
		order.ascending = this.ascending;
		return order;
	}

	/**
	 * Get new ascending order for search filter.
	 *
	 * @param searchFilter enumeration to identify filter
	 * @return the search filter
	 */
	public static FilterOrder getAscendingOrder(Enum<?> searchFilter) {
		return new FilterOrder(searchFilter, true);
	}

	/**
	 * Get new descending order for search filter.
	 *
	 * @param searchFilter enumeration to identify filter
	 * @return the search filter
	 */
	public static FilterOrder getDescendingOrder(Enum<?> searchFilter) {
		return new FilterOrder(searchFilter, false);
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
	 * Set the filter enumeration.
	 *
	 * @param searchEnum the new search enum
	 */
	public void setSearchEnum(Enum<?> searchEnum) {
		this.searchEnum = searchEnum;
	}

	/**
	 * Is this filter ascending?.
	 *
	 * @return <code>true</code> if ascending, <code>false</code> if descending.
	 */
	public boolean isAscending() {
		return ascending;
	}

	/**
	 * Set filter to ascending.
	 *
	 * @param ascending the new ascending
	 */
	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}

	/**
	 * Get the search filter this predicate is used in.
	 * 
	 * @return {@link SearchFilter} or <code>null</code> if unsused in a {@link SearchFilter}.
	 */
	public SearchFilter getSearchFilter() {
		return searchFilter;
	}

	void setSearchFilter(SearchFilter searchFilter) {
		this.searchFilter = searchFilter;
	}

	/**
	 * See {@link Object#toString()}.
	 *
	 */
	@Override
	public String toString() {
		return "FilterOrder [searchEnum=%s, ascending=%s]".formatted(searchEnum, ascending);
	}
}