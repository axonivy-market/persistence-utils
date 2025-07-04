package com.axonivy.utils.persistence.dao;

import java.io.Serializable;
import java.time.Instant;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

import com.axonivy.utils.persistence.beans.ToggleableEntity;
import com.axonivy.utils.persistence.beans.ToggleableEntity_;
import com.axonivy.utils.persistence.dao.markers.ToggleableMarker;
import com.axonivy.utils.persistence.enums.ToggleableStatus;
import com.axonivy.utils.persistence.search.AttributePredicates;
import com.axonivy.utils.persistence.search.FilterPredicate;

/**
 * Dao for toggleable
 *
 * @param <MetaDataGeneric> meta model
 * @param <A> Entity
 */
public abstract class ToggleableDAO<MetaDataGeneric extends ToggleableEntity_,A extends ToggleableEntity<? extends Serializable>>
	extends AuditableDAO<MetaDataGeneric,A> {

	@Override
	protected <U> void manipulateCriteriaFactory(CriteriaQueryGenericContext<A, U> context) {
		constructActiveStatusQuery(context, context.r);
		super.manipulateCriteriaFactory(context);
	}

	/**
	 * Allow use of toggelable logic for joins
	 * @param context query context
	 * @param rootPath represents a simple or compound attribute path from a
	 * bound type or collection, and is a "primitive" expression
	 * @param <U> the type of the represented object
	 */
	public <U> void constructActiveStatusQuery(
			CriteriaQueryGenericContext<A, U> context, Path<A> rootPath) {

		ToggleableStatus which = ToggleableStatus.ACTIVE;
		ToggleableMarker marker = context.getQuerySettings().getMarker(ToggleableMarker.class);
		if(null != marker) {
			which = marker.getWhich();
		}
		
		Path<Instant> expiry = rootPath.get(ToggleableEntity_.expiry);
		Path<Boolean> isEnabled = rootPath.get(ToggleableEntity_.enabled);

		switch (which) {
		case ACTIVE: //(expired null or not expired) and isEnabled = true
			Predicate nullOrNotExpiredAndEnabled = getNotExpiredAndEnabledSelection(
					context,rootPath);
			context.where(nullOrNotExpiredAndEnabled);

			break;
		case INACTIVE: //expired or not isEnabled = false
			context.where(context.c.or(
					context.c.lessThanOrEqualTo(expiry, Instant.now()),
					context.c.equal(isEnabled, false)));
			break;
		default:
			break;
		}
	}

	protected <U> Predicate getNotExpiredAndEnabledSelection(
			CriteriaQueryGenericContext<A, U> f, Path<A> rootPath  ) {
		Predicate nullOrNotExpired = f.c.or(f.c.isNull(rootPath.get(ToggleableEntity_.expiry)),f.c.greaterThan(rootPath.get(ToggleableEntity_.expiry), Instant.now()));
		return f.c.and(nullOrNotExpired,f.c.equal(rootPath.get(ToggleableEntity_.enabled), true));
	}

	@Override
	protected AttributePredicates getAttributePredicate(
			CriteriaQueryGenericContext<A, ?> query,
			FilterPredicate filterPredicate, ExpressionMap expressionMap) {
		AttributePredicates result = new AttributePredicates();

		Enum<?> searchFilter = filterPredicate.getSearchEnum();
		if(searchFilter.getClass().isEnum()) {
			Enum<?> e = searchFilter;
			if("STATUS".equals(e.name())){ //comes from OrgUnitSearchFields
				constructStatusExpression(query,query.r, filterPredicate, result);
			}
		}

		return result;
	}

	/**
	 * Create status expression
	 * @param query query context
	 * @param rootPath represents a simple or compound attribute path from a
	 * bound type or collection, and is a "primitive" expression
	 * @param filterPredicate a single predicate of a filter
	 * @param result predicates, selections and templates for ordering
	 */
	public <U> void constructStatusExpression(
			CriteriaQueryGenericContext<A, U> query,
			Path<A> rootPath, FilterPredicate filterPredicate, AttributePredicates result) {

		Predicate nullOrNotExpiredAndEnabled = getNotExpiredAndEnabledSelection(query,rootPath);
		Expression<Number> selectExpression = query.c.<Number>selectCase().when(nullOrNotExpiredAndEnabled , query.c.literal(1L)).otherwise(query.c.literal(0L));//boolean not working
		result.addSelection(selectExpression);
		result.addOrder(query.c.asc(selectExpression));
		setStatusMarkFromFilterPredicate(query, filterPredicate);
	}

	protected void setStatusMarkFromFilterPredicate(
			CriteriaQueryGenericContext<?, ?> query,
			FilterPredicate filterPredicate) {

		ToggleableStatus status = filterPredicate.getValue(ToggleableStatus.class);
		if(status != null) {
			switch(status) {
			case ACTIVE:
				query.getQuerySettings().withMarkers(ToggleableMarker.ACTIVE);
				break;
			case INACTIVE:
				query.getQuerySettings().withMarkers(ToggleableMarker.INACTIVE);
				break;
			case ALL:
				query.getQuerySettings().withMarkers(ToggleableMarker.ALL);
				break;
			default:
				break;
			}
		}
	}

}
