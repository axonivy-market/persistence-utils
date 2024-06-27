package com.axonivy.utils.persistence.demo.ui;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.Tuple;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import com.axonivy.utils.persistence.dao.QuerySettings;
import com.axonivy.utils.persistence.demo.daos.PersonDAO;
import com.axonivy.utils.persistence.demo.entities.Person;
import com.axonivy.utils.persistence.demo.enums.PersonSearchField;
import com.axonivy.utils.persistence.demo.ui.PersonSearchLazyDataModel.PersonTuple;
import com.axonivy.utils.persistence.search.SearchFilter;


public class PersonSearchLazyDataModel extends LazyDataModel<PersonTuple> {
	private static final long serialVersionUID = 1L;
	private List<PersonTuple> personTuples;


	public PersonSearchLazyDataModel() {
		super();
	}

	@Override
	public PersonTuple getRowData(String rowKey) {
		for (PersonTuple tuple : personTuples) {
			// Field 0 contains the ID.
			if (tuple.getId().equals(rowKey)) {
				return tuple;
			}
		}
		return null;
	}

	@Override
	public String getRowKey(PersonTuple tuple) {
		// Field 0 contains the ID.
		return tuple.getId();
	}

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		SearchFilter filter = createSearchFilter(filterBy, null);
		return (int) PersonDAO.getInstance().countBySearchFilter(filter);
	}

	@Override
	public List<PersonTuple> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
		SearchFilter filter = createSearchFilter(filterBy, sortBy);

		// Handle paging.
		QuerySettings<Person> querySettings =
				new QuerySettings<Person>().withFirstResult(first).withMaxResults(pageSize);

		// Do the search.
		personTuples = PersonDAO.getInstance()
				.findBySearchFilter(filter, querySettings)
				.stream()
				.map(t -> new PersonTuple(t))
				.toList();

		return personTuples;
	}

	private SearchFilter createSearchFilter(Map<String, FilterMeta> filterBy, Map<String, SortMeta> sortBy) {
		SearchFilter filter = new SearchFilter();

		// First field is used as row key and must be the ID (not displayed, therefore not searchable).

		filter
		.add(PersonSearchField.ID)
		.add(PersonSearchField.FIRST_NAME, filterValue(filterBy.get(PersonSearchField.FIRST_NAME.name())))
		.add(PersonSearchField.LAST_NAME, filterValue(filterBy.get(PersonSearchField.LAST_NAME.name())))
		.add(PersonSearchField.IVY_USER_NAME, filterValue(filterBy.get(PersonSearchField.IVY_USER_NAME.name())))
		.add(PersonSearchField.BIRTHDATE, filterValue(filterBy.get(PersonSearchField.BIRTHDATE.name())))
		.add(PersonSearchField.DEPARTMENT_NAME, filterValue(filterBy.get(PersonSearchField.DEPARTMENT_NAME.name())))
		.add(PersonSearchField.MARITAL_STATUS, filterValue(filterBy.get(PersonSearchField.MARITAL_STATUS.name())))
		.add(PersonSearchField.MARITAL_STATUS_CONTAINS, filterValue(filterBy.get(PersonSearchField.MARITAL_STATUS_CONTAINS.name())))
		.add(PersonSearchField.SALARY, filterValue(filterBy.get(PersonSearchField.SALARY.name())))
		.add(PersonSearchField.SYNC_TO_IVY, filterValue(filterBy.get(PersonSearchField.SYNC_TO_IVY.name())));

		if(sortBy != null) {
			for(SortMeta sortMeta : sortBy.values()) {
				filter.addSort(PersonSearchField.valueOf(sortMeta.getField()), sortMeta.getOrder() == SortOrder.ASCENDING ? true : false);
			}
		}
		return filter;
	}

	private Object filterValue(FilterMeta filterMeta) {
		return filterMeta != null ? filterMeta.getFilterValue() : null;
	}

	public static class PersonTuple {
		private Tuple tuple;


		public PersonTuple(Tuple tuple) {
			this.tuple = tuple;
		}

		public Object get(PersonSearchField searchField) {
			int position = searchField.ordinal();
			if (position < tuple.getElements().size()) {
				return tuple.get(position);
			}
			return "Column for field '" + searchField + "' was not found!";
		}

		public Object get(String searchField) {
			PersonSearchField field = PersonSearchField.valueOf(searchField);
			if (field == null) {
				return "Column for field name '" + searchField + "' was not found!";
			}
			return get(field);
		}

		/**
		 * Get list of all values from the Tuple as Object
		 *
		 * @return
		 */
		public List<Object> getObjects() {
			return Arrays.asList(tuple.toArray());
		}

		public String getId() {
			return (String) get(PersonSearchField.ID);
		}
	}
}
