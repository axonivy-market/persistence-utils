package com.axonivy.utils.persistence.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Search filter.
 *
 * A search filter combines {@link FilterPredicate}s and {@link FilterOrder}s.
 */
public class SearchFilter {

	private final List<FilterPredicate> filterPredicates = new ArrayList<>();
	private final List<FilterOrder> filterOrders = new ArrayList<>();
	private AttributePredicates attributePredicates = new AttributePredicates();

	/**
	 * Constructor.
	 */
	public SearchFilter() {
		//no init needed
	}

	/**
	 * Convenience function to create a search filter.
	 * 
	 * @return
	 */
	public static SearchFilter create() {
		return new SearchFilter();
	}

	/**
	 * Reset for a new search.
	 */
	public void reset() {
		attributePredicates = new AttributePredicates();
	}

	/**
	 * Create filter and add {@link FilterPredicate}s.
	 *
	 * Copies of the original {@link FilterPredicate}s will be used for searches.
	 *
	 * @param filterPredicates
	 */
	public SearchFilter(FilterPredicate... filterPredicates) {
		internalAddPredicates(Arrays.asList(filterPredicates));
	}

	/**
	 * Add {@link FilterPredicate}s.
	 *
	 * Copies of the original {@link FilterPredicate}s will be used for searches.
	 * 
	 * @param filterPredicates
	 * @return current {@link SearchFilter} for additional function calls
	 */
	public SearchFilter add(FilterPredicate... filterPredicates) {
		internalAddPredicates(Arrays.asList(filterPredicates));
		return this;
	}

	/**
	 * Add {@link FilterOrder}s.
	 *
	 * @param filterOrders
	 * @return current {@link SearchFilter} for additional function calls
	 */
	public SearchFilter add(FilterOrder... filterOrders) {
		internalAddOrders(Arrays.asList(filterOrders));
		return this;
	}

	protected void internalAddPredicates(Collection<FilterPredicate> predicates) {
		for (FilterPredicate predicate : predicates) {
			var copy = predicate.copy();
			copy.setSearchFilter(this);
			filterPredicates.add(copy);
		}
	}

	protected void internalAddOrders(Collection<FilterOrder> orders) {
		for (FilterOrder order : orders) {
			var copy = order.copy();
			copy.setSearchFilter(this);
			filterOrders.add(copy);
		}
	}

	/**
	 * Convenience function to add a {@link FilterPredicate}.
	 *
	 * @param searchFilter enumeration to identify filter
	 * @return object
	 */
	public SearchFilter add(Enum<?> searchFilter) {
		add(new FilterPredicate(searchFilter, null));
		return this;
	}

	/**
	 * Convenience function to add a {@link FilterPredicate}.
	 *
	 * @param searchFilter enumeration to identify filter
	 * @param value object
	 * @return constructed searchFilter
	 */
	public SearchFilter add(Enum<?> searchFilter, Object value) {
		add(new FilterPredicate(searchFilter, value));
		return this;
	}

	/**
	 * Convenience function to add a {@link FilterOrder}.
	 *
	 * @param searchFilter enumeration to identify filter
	 * @param ascending the new ascending
	 * @return object
	 */
	public SearchFilter addSort(Enum<?> searchFilter, boolean ascending) {
		add(new FilterOrder(searchFilter, ascending));
		return this;
	}

	/**
	 * Get all {@link FilterPredicate}s.
	 *
	 * @return the filter predicates
	 */
	public List<FilterPredicate> getFilterPredicates() {
		return Collections.unmodifiableList(filterPredicates);
	}

	/**
	 * Get all {@link FilterOrder}s.
	 *
	 * @return the filter orders
	 */
	public List<FilterOrder> getFilterOrders() {
		return Collections.unmodifiableList(filterOrders);
	}

	/**
	 * Get the current {@link AttributePredicates}.
	 * 
	 * While building the query of a filter these will grow and
	 * access to these current selections and predicates might
	 * help build very complex queries.
	 * 
	 * Changes should not be necessary and might make the filter unusable.
	 * Reading the current number of selection might help referencing.
	 * 
	 * @return
	 */
	public AttributePredicates getAttributePredicates() {
		return attributePredicates;
	}

	/**
	 * Get {@link FilterPredicate} by enum value;
	 *
	 * @deprecated in future versions using duplicates of filters will be possible, therefore this function would not make sense.
	 * @param searchFilter enumeration to identify filter
	 * @return object
	 */
	@Deprecated
	public FilterPredicate getFilterPredicate(Enum<?> searchFilter) {
		return filterPredicates.stream().filter(p -> p.getSearchEnum().equals(searchFilter)).findFirst().orElse(null);
	}
}