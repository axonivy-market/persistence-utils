package com.axonivy.utils.persistence.dao;

import static com.axonivy.utils.persistence.enums.UpdateType.DELETE;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.transaction.TransactionRolledbackException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.hibernate.proxy.HibernateProxy;

import com.axonivy.utils.persistence.MaximumSizeMap;
import com.axonivy.utils.persistence.ReflectionUtilitities;
import com.axonivy.utils.persistence.annotations.Audit;
import com.axonivy.utils.persistence.beans.AuditableEntity;
import com.axonivy.utils.persistence.beans.AuditableEntity_;
import com.axonivy.utils.persistence.beans.GenericEntity;
import com.axonivy.utils.persistence.beans.GenericEntity_;
import com.axonivy.utils.persistence.beans.GenericIdEntity_;
import com.axonivy.utils.persistence.beans.Header;
import com.axonivy.utils.persistence.beans.Updatable;
import com.axonivy.utils.persistence.beans.VersionableEntity_;
import com.axonivy.utils.persistence.enums.HasCmsName;
import com.axonivy.utils.persistence.enums.UpdateType;
import com.axonivy.utils.persistence.history.handler.AuditHandler;
import com.axonivy.utils.persistence.logging.Logger;
import com.axonivy.utils.persistence.search.AttributePredicates;
import com.axonivy.utils.persistence.search.FilterOrder;
import com.axonivy.utils.persistence.search.FilterPredicate;
import com.axonivy.utils.persistence.search.FindByExample;
import com.axonivy.utils.persistence.search.SearchFilter;
import com.axonivy.utils.persistence.service.EnumService;

/**
 * @author Various People
 *
 * @param <M> meta model
 * @param <T> entity
 */
public abstract class GenericDAO<M extends GenericEntity_, T extends GenericEntity<? extends Serializable>> extends AbstractDAO {
	private static final Logger LOG = Logger.getLogger(GenericDAO.class);
	public static final int DEFAULT_MAX_RESULTS = 50;

	/**
	 * Number of persistence operations and objects to remember in case an exception
	 * (mainly OptimisticLockException) happens.
	 */
	private static final int MAX_UPDATES_TO_KEEP = 30000;


	/**
	 * or syntax enabled
	 */
	public static final Optional<Boolean> ORSYNTAX = Optional.of(true);

	/**
	 * liek syntax enabled
	 */
	public static final Optional<Boolean> LIKESYNTAX = Optional.of(true);

	/**
	 * like syntax turned off
	 */
	public static final Optional<Boolean> NOLIKESYNTAX = Optional.of(false);

	/**
	 * or syntax turned off
	 */
	public static final Optional<Boolean> NOORSYNTAX = Optional.of(false);

	/**
	 * Limit for query turned off
	 */
	public static final Integer NOLIMIT = -1;

	/** Limit for Dropdown values */
	public static final Integer MAX_LIMIT_DROPDOWNS = 100;

	private static final Map<Serializable, UpdateInformation> updateMap =
			Collections.synchronizedMap(new MaximumSizeMap<>(MAX_UPDATES_TO_KEEP));

	private static final Map<Class<? extends GenericEntity<? extends Serializable>>, AuditHandler> handlerMap = new HashMap<>();

	/**
	 * Gets the meta model.
	 *
	 * @return the meta model
	 */
	public M getMetaModel() {
		// intentional, no instance should be created
		return null;
	}

	/**
	 * Return the entity {@link Class} which is handled by the DAO class.
	 *
	 * @return entity
	 */
	protected abstract Class<T> getType();

	/**
	 * Return the entity {@link Class} which is handled by the DAO class.
	 *
	 * @return the entity
	 */
	public final Class<T> getEntityType() {
		return getType();
	}

	/**
	 * Simplified Find by example like is enabled, and operators are used, call
	 * returning only the first result, limiting SQL results to one
	 *<p>Example:</p>
	 *<pre>
	 *{@code
	 *FindByExample<T> example = FindByExample.getInstance(T.class);
	 *T = dao.findFirstByExample(example);	
	 *}
	 *</pre>
	 *<p>Note that T is a entity</p>
	 * @param example the example entity T object
	 * @param order   array of columns of entity T according to which should be
	 *                ordered
	 * @return first result from whole collection, limit sql results to 1 or null if
	 *         not found
	 */
	@SafeVarargs
	public final T findFirstByExample(FindByExample<? extends T> example, SingularAttribute<? super T, ?>... order) {
		List<T> res = findByExample(example, Optional.of(false), Optional.of(true), null, 0, 1, order);

		return CollectionUtils.isNotEmpty(res) ? res.get(0) : null;
	}

	/**
	 * An even simpler call to {@link GenericDAO } findByExample
	 *<p>Example:</p>
	 *<pre>
	 *{@code
	 *FindByExample<T> example = FindByExample.getInstance(T.class);
	 *example.getE().setProperty(value); // set value to search
	 *List<T> = dao.findFirstByExample(example,Optional.of(Boolean.TRUE),T_.property);	
	 *}
	 *</pre>
	 *<p>Note: T is a entity, T_ is a meta model</p> 
	 * @param example    the example entity T object
	 * @param likeSyntax optional. If null or true, only like expressions are used
	 *                   for strings, else equal expressions are used
	 * @param order      array of columns of entity T according to which should be
	 *                   ordered
	 * @return list of entities which are similar to the example
	 */
	@SafeVarargs
	public final List<T> findByExample(FindByExample<? extends T> example,
			Optional<Boolean> likeSyntax,
			SingularAttribute<? super T, ?>... order) {

		return findByExample(example, null, likeSyntax, order);
	}

	/**
	 * Do a find by Example search. This is an even simpler call to
	 * {@link GenericDAO }.findByExample(...)
	 *<p>Example:</p>
	 *<pre>
	 *{@code
	 *FindByExample<T> example = FindByExample.getInstance(T.class);
	 *example.getE().setProperty(value); // set value to search
	 *List<T> = dao.findFirstByExample(example,T_.property);	
	 *}
	 *</pre>
	 *<p>Note: T is a entity, T_ is a meta model</p> 
	 * @param example the example entity T object
	 * @param order   array of columns of entity T according to which should be
	 *                ordered
	 * @return list of results similar to the example
	 * @see FindByExample
	 */
	@SafeVarargs
	public final List<T> findByExample(FindByExample<? extends T> example, SingularAttribute<? super T, ?>... order) {

		return findByExample(example, null, null, order);
	}

	/**
	 * A simpler call to {@link GenericDAO}.findByExample(...) only ascending mode
	 * is allowed
	 *<p>Example:</p>
	 *<pre>
	 *{@code
	 *FindByExample<T> example = FindByExample.getInstance(T.class);
	 *example.getE().setProperty(value); // set value to search
	 *List<T> = dao.findFirstByExample(example,Optional.of(Boolean.TRUE), Optional.of(Boolean.TRUE),T_.property);	
	 *}
	 *</pre>
	 *<p>Note: T is a entity, T_ is a meta model</p> 
	 * @param example    the example entity T object
	 * @param orSyntax   optional. If true, only OR expressions are used in query,
	 *                   if null or false then and expressions are used
	 * @param likeSyntax optional. If null or true, only like expressions are used
	 *                   for strings, else equal expressions are used
	 * @param order      array of columns of entity T according to which should be
	 *                   ordered
	 * @return a list of entities similar to example
	 */
	@SafeVarargs
	public final List<T> findByExample(FindByExample<? extends T> example,
			Optional<Boolean> orSyntax, Optional<Boolean> likeSyntax,
			SingularAttribute<? super T, ?>... order) {

		return findByExample(example, orSyntax, likeSyntax, null, order);
	}

	/**
	 * help method for {@link GenericDAO} .findByExample where firstResult and
	 * maxResults are set to null then meaning not defined}
	 *<p>Example:</p>
	 *<pre>
	 *FindByExample{@code<T>} example = FindByExample.getInstance(T.class);
	 *example.getE().setProperty(value); // set value to search
	 *Boolean[] isAscending =<code> {false,true};</code>
	 *List{@code<T>} = dao.findFirstByExample(example,Optional.of(Boolean.TRUE), Optional.of(Boolean.TRUE),isAscending,{@code T_.property});
	 *</pre>
	 *<p>Note: T is a entity, T_ is a meta model</p>
	 * @param example     the example entity T object
	 * @param orSyntax    optional. If true, only OR expressions are used in query,
	 *                    if null or false then and expressions are used
	 * @param likeSyntax  optional. If null or true, only like expressions are used
	 *                    for strings, else equal expressions are used
	 * @param isAscending optional. If null or empty ASC is assumed
	 * @param order       array of columns of entity T according to which should be
	 *                    ordered
	 * @return a list of entities similar to example
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByExample(FindByExample<? extends T> example,
			Optional<Boolean> orSyntax, Optional<Boolean> likeSyntax,
			Boolean[] isAscending, SingularAttribute<? super T, ?>... order) {

		return findByExample(example, orSyntax, likeSyntax, isAscending, null, null, order);
	}

	/**
	 * Find an entity which has the same fields set as the example entity , order
	 * according to params
	 *
	 * Ignoring version field Excluding 0 values are case insensitive like is
	 * supported
	 *<p>Example:</p>
	 *<pre>
	 *FindByExample{@code<T>} example = FindByExample.getInstance(T.class);
	 *example.getE().setProperty(value); // set value to search
	 *Boolean[] isAscending =<code> {false,true};</code>;
	 *Integer firstResult = 0;
	 *Integer maxResults = 1;
	 *List{@code<T>} = dao.findFirstByExample(example,Optional.of(Boolean.TRUE), Optional.of(Boolean.TRUE),isAscending,firstResult,maxResults, {@code T_.property});	
	 *</pre>
	 *<p>Note: T is a entity, T_ is a meta model</p>
	 * @param example     the example entity T object
	 * @param likeSyntax  optional. If null or true, only like expressions are used
	 *                    for strings, else equal expressions are used
	 * @param orSyntax    optional. If true, only OR expressions are used in query,
	 *                    if null or false then and expressions are used
	 * @param isAscending optional. If null or empty ASC is assumed
	 * @param firstResult start with this one starting from 0
	 * @param maxResults  maximum number of results to return
	 * @param order       array of columns of entity T according to which should be
	 *                    ordered
	 * @return ordered List of entities similar to the example entity
	 */
	@SafeVarargs
	public final List<T> findByExample(FindByExample<? extends T> example,
			Optional<Boolean> orSyntax, Optional<Boolean> likeSyntax,
			Boolean[] isAscending, Integer firstResult, Integer maxResults,
			SingularAttribute<? super T, ?>... order) {

		if (example == null) {
			return new ArrayList<>();
		}

		Class<T> clazz = getType();
		try (CriteriaQueryContext<T> f = initializeQuery()) {
			QuerySettings<T> querySettings = f.getQuerySettings();
			querySettings.withFirstResult(firstResult).withMaxResults(maxResults);

			CriteriaBuilder cb = f.c;
			CriteriaQuery<T> q = f.q;
			Root<T> r = f.r;
			boolean isOrSyntax = orSyntax != null && orSyntax.isPresent() && orSyntax.get();
			Predicate p;

			if (isOrSyntax) {
				p = cb.disjunction();
			} else {
				p = cb.conjunction();
			}

			if (querySettings.getMaxResults() == null) {
				LOG.info(
						"Limiting maximum results for findbyExample {0} to {1}, as no max results are defined",
						example.getClass().getSimpleName(), DEFAULT_MAX_RESULTS);
				querySettings.withMaxResults(DEFAULT_MAX_RESULTS);
			}

			Metamodel mm = getEM().getMetamodel();
			EntityType<T> et = mm.entity(clazz);
			Set<Attribute<? super T, ?>> attrs = et.getAttributes();
			for (Attribute<? super T, ?> a : attrs) {
				// dont use id, version or header in findbyexample searches
				if (a.getJavaType().equals(AuditableEntity_.header.getJavaType())
						|| a.getJavaMember().getName().equals(GenericIdEntity_.id.getJavaMember().getName()) || 
						a.getJavaMember().getName().equals(VersionableEntity_.version.getJavaMember().getName())) {
					continue;
				}

				p = createExpressionViaReflection(example.getE(), likeSyntax, f, isOrSyntax, p, a);
			}

			q.select(r).where(p);

			if (ArrayUtils.isNotEmpty(order)) {
				int i = 0;
				List<Order> orderBys = new ArrayList<>();
				for (SingularAttribute<? super T, ?> ord : order) {
					if (ord == null) {
						LOG.warn("the order by is null, orderby list = {0}", (Object[]) order);
						continue;
					}

					boolean asc = isAscending != null && isAscending.length > i ? isAscending[i++] : true;

					Order orderBy = asc ? f.c.asc(f.r.get(ord)) : f.c.desc(f.r.get(ord));
					orderBys.add(orderBy);
				}

				if (!orderBys.isEmpty()) {
					f.q.orderBy(orderBys);
				}
			}

			return findByCriteria(f);
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
	}

	private Predicate createExpressionViaReflection(T example, Optional<Boolean> likeSyntax, CriteriaQueryContext<T> f,
			boolean isOrSyntax, Predicate p, Attribute<? super T, ?> attr) {

		Predicate result = p;
		String javaName = attr.getJavaMember().getName();
		String getter = ReflectionUtilitities.getGetterMethodName(javaName);

		try {
			Method m = example.getClass().getMethod(getter, (Class<?>[]) null);
			if (m != null) {
				Object value = m.invoke(example, (Object[]) null);
				if (value != null && !(value instanceof Collection)) {
					Predicate expr = ((likeSyntax == null || likeSyntax.orElse(true)) && attr.getJavaType().isAssignableFrom(String.class))
							? f.c.like(f.r.get(attr.getName()), "%" + value.toString() + "%")
									: f.c.equal(f.r.get(attr.getName()), value);

					result = isOrSyntax ? f.c.or(result, expr) : f.c.and(result, expr);
				} else if (value != null) {
					Collection<?> collectionOfValues = (Collection<?>) value;
					if (!collectionOfValues.isEmpty()) {
						In<Object> in = f.c.in(f.r.get(attr.getName()));
						for (Object columnValue : collectionOfValues) {
							in.value(columnValue);
						}

						result = in;
					}
				}
			}

			return result;
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			LOG.warn("QueryByExample invoke problem: method {0} example object {1}", e, getter, example);
		}

		return result;
	}

	/**
	 * Find a bean by it's primary key.
	 * 
	 * <p>Example:</p>
	 * <pre>
	 * {@code
	 *	T bean = dao.findById(id);
	 * }
	 * </pre>
	 * <p>Note that T is an entity</p>
	 * 
	 * @param id primary key of entity
	 * @return entity with specified id
	 */
	public T findById(Serializable id) {
		try (AutoCloseable closeableSession = beginSession()) {
			T bean;
			bean = findInEM(getType(), id);
			return bean;
		} catch (EntityNotFoundException e) {
			LOG.warn("findById could not find entity of type {0} with id {1}", e, getType().getCanonicalName(), id);
			return null;
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
	}

	/**
	 * Find a bean.
	 *
	 * Try to find (and reload) the given bean.
	 *
	 * @param bean represents a bean
	 * @return entity with specified id or null
	 */
	public T find(T bean) {
		if (bean == null || bean.getId() == null) {
			return null;
		}

		return findById(bean.getId());
	}

	/**
	 * Find all beans of this type.
	 * 
	 * <p>Example:</p>
	 * <pre>
	 * {@code
	 *	List<T> beans = dao.findAll();
	 * }
	 * </pre>
	 * <p>Note that T is a entity</p>
	 * 
	 * @return list of all beans of this type
	 */
	public List<T> findAll() {
		return findAll((QuerySettings<T>) null);
	}

	/**
	 * Find all beans of specific type.
	 * 
	 * <p>Example:</p>
	 * <pre>
	 * {@code
	 *	List<T> beans = dao.findAll(new QuerySettings<T>());
	 * }
	 * </pre>
	 * <p>Note that T is a entity</p>
	 * 
	 * @param querySettings specify paging, markers,orders...
	 * @return a list
	 */
	public List<T> findAll(QuerySettings<T> querySettings) {
		try (CriteriaQueryContext<T> criteriaFactory = initializeQuery()) {
			if (querySettings != null) {
				criteriaFactory.setQuerySettings(querySettings);
			}

			return findByCriteria(criteriaFactory);
		}
	}

	/**
	 * Find beans by criteria.
	 * 
	 * <p>Example:</p>
	 * <pre>
	 * try (CriteriaQueryGenericContext{@code<T, U>} query = dao.initializeQuery(T.class, U.class)) {
	 *
	 *	Expression{@code<String>} path = dao.getExpression(null, query.r, {@code T_.property});
	 *	query.q.where(q.c.like(path, value)); // value to search, c.like method search
	 *	query.q.multiselect(path);
	 *	List{@code<U>} beans = dao.findByCriteria(query);
	 * }
	 * </pre>
	 * <p>Note that T is a entity, U is a return object</p>
	 * @param ctx query context
	 * @param <U> the type of the represented object
	 * @return list of entities according to specified factory, with applied
	 *         permissions
	 */
	public <U> List<U> findByCriteria(CriteriaQueryGenericContext<T, U> ctx) {
		return ctx != null ? findByCriteriaInternal(ctx) : new ArrayList<U>();
	}

	/**
	 * Count instances which would be returned by
	 * {@link #findByCriteria(CriteriaQueryGenericContext)} with this
	 * {@link CriteriaQueryGenericContext}.
	 *
	 * It is up to the caller to prepare the counting criteria query (which can be
	 * tricky). Best practice for now: build the query and the counting query in
	 * parallel, the counting query only needs the "where" part (including JOINS).
	 *
	 * Note: it is NOT TRIVIAL to copy a {@link CriteriaQueryContext}.
	 * <p>Example:</p>
	 * <pre>
	 * try (CriteriaQueryGenericContext{@code<T, U>} query = dao.initializeQuery(T.class, U.class);
	 *	CriteriaQueryGenericContext{@code<T, Long>}countQuery = dao.initializeQuery(T.class,Long.class);) {
	 *
	 *	Expression{@code<String>} path = dao.getExpression(null, query.r, {@code T_.property});
	 *	query.q.where(q.c.like(path, value)); // value to search, c.like method search
	 *	query.q.multiselect(path);
	 *	countQuery.where(query.q.getRestriction());
	 *	Long count = dao.countByCriteria(countQuery);
	 * }
	 * </pre>
	 * <p>Note that T is a entity, U is a return object</p>
	 * 
	 * @param f query context for serializable type T
	 * @return count of distinct rows in query
	 */
	public long countByCriteria(CriteriaQueryGenericContext<T, Long> f) {
		final Long count;
		if (f != null) {
			CriteriaQueryGenericContext<T, Long> tmpQuery = f;
			try (AutoCloseable closeableSession = beginSession()) {
				tmpQuery = new CriteriaQueryGenericContext<T, Long>(tmpQuery) { // a temporary copy so that the
					// definitive query is not changed
					@Override
					public void close() {
						// no close needed, there is a close in the copied tmpQuery already implemented
					}
				};

				tmpQuery.q.multiselect(tmpQuery.c.countDistinct(tmpQuery.r));

				count = findByCriteriaInternal(tmpQuery).stream().findFirst().orElse(0L);
			} catch (Exception e) {
				throw new PersistenceException(e);
			}
		} else {
			count = 0L;
		}

		return count;
	}

	/**
	 * Override this function to implement query restrictions for a type of bean.
	 *
	 * For an example see {@link ToggleableDAO}.
	 *
	 * @param criteriaFactory {@link CriteriaQueryGenericContext} which needs to be
	 *                        manipulated
	 */
	protected <U> void manipulateCriteriaFactory(
			CriteriaQueryGenericContext<T, U> criteriaFactory) {
	}

	/**
	 * Raw find of beans by criteria.
	 *
	 * This version does not check permissions.
	 *
	 * @param ctx
	 * @return
	 */
	private <U> List<U> findByCriteriaInternal(CriteriaQueryGenericContext<T, U> ctx) {
		LocalTime startOfMeasurements = LocalTime.now();
		try (AutoCloseable au = beginSession()) {

			QuerySettings<T> querySettings = ctx.getQuerySettings();

			// Add orders.
			ctx.orderBy(querySettings.getOrders().toArray(new Order[0]));
			// Add orders specified by simple attributes.
			var orderAttributes = querySettings.getOrderAttributes();
			var orders = orderAttributes.stream().filter(ObjectUtils::isNotEmpty).map(o -> ctx.asc(o))
					.toArray(Order[]::new);
			ctx.orderBy(orders);

			CriteriaQueryGenericContext.TypedQueryInterceptor<U> tqi = ctx.getTypedQueryInterceptor();

			// Add functionality for a bean hierarchy.
			manipulateCriteriaFactory(ctx);

			TypedQuery<U> query = getEM().createQuery(ctx.q);

			if (tqi != null) {
				tqi.beforeGetResultList(query);
			}

			var firstResult = querySettings.getFirstResult();
			if (firstResult != null && firstResult >= 0) {
				query.setFirstResult(firstResult);
			}

			var maxResults = querySettings.getMaxResults();
			if (maxResults != null && maxResults >= 0) {
				query.setMaxResults(maxResults);
			}

			handleReadingAudit(ctx);

			// Execute the query.
			List<U> resultList = query.getResultList();





			if (tqi != null) {
				resultList = tqi.afterGetResultList(resultList);
			}

			if (LOG.isDebugEnabled()) {
				double millis = startOfMeasurements.until(LocalTime.now(), ChronoUnit.NANOS) / 1000000.0;

				String criteriaString = ctx.getQueryString(query);
				if (!StringUtils.isBlank(criteriaString)) {
					criteriaString = ":\n    " + criteriaString;
				}

				if (millis > 500) {
					LOG.warn("{0}: Long Query took {2} ms, statement: {1}", getType().getSimpleName(), criteriaString, NumberFormat.getNumberInstance().format(millis));
				} else {
					LOG.debug("{0}: Query took {2} ms, statement: {1}",	getType().getSimpleName(), criteriaString, NumberFormat.getNumberInstance().format(millis));
				}
			}

			return resultList;
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
	}

	/**
	 * Raw update of beans by criteria.
	 * 
	 * This version only handles the optimistic lock in one way and
	 * does not check permissions.
	 *
	 * @param criteriaFactory update query context for serializable type T
	 * 
	 * @return number of updated row
	 */
	public long updateRawByCriteria(UpdateQueryGenericContext<T> criteriaFactory) {
		LocalTime startOfMeasurements = LocalTime.now();
		try (AutoCloseable au = beginSession();) {
			Boolean[] isError = new Boolean[] { true };
			try (AutoCloseTransaction autoclose = beginTransaction(isError)) {

				manipulateUpdateQuery(criteriaFactory);
				Query query = getEM().createQuery(criteriaFactory.u);
				int result = query.executeUpdate();

				if (LOG.isDebugEnabled()) {
					LocalTime endOfMeasurements = LocalTime.now();
					double millis = startOfMeasurements.until(endOfMeasurements, ChronoUnit.NANOS) / 1000000.0;
					String criteriaString = criteriaFactory.getQueryString(query);

					if (!StringUtils.isBlank(criteriaString)) {
						criteriaString = ":\n    " + criteriaString;
					}

					LOG.debug(
							"{0}: update by criteria took {2} ms : statement{1}",
							getType().getSimpleName(), criteriaString,
							NumberFormat.getNumberInstance().format(millis));
				}

				isError[0] = false;

				return result;
			}
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

	}

	/**
	 * Manipulate the update query context.
	 * 
	 * @param criteriaFactory
	 */
	protected void manipulateUpdateQuery(UpdateQueryGenericContext<T> criteriaFactory) {
	}

	/**
	 * Physically raw delete of beans by criteria.
	 *
	 * This version does not check permissions and do delete physically in the
	 * database.
	 * <p>Example:</p>
	 * <pre>
	 * try (DeleteQueryContext{@code<T>} query = dao.initializeDeleteQuery();) {
	 *
	 *	Expression{@code<String>} path = dao.getExpression(null, query.r, T_.property);
	 *	Predicate wdaInPredicate = query.c.equal(query.r.get(T_.property), value); // value to search, c.equal method search
	 *	query.d.where(wdaInPredicate);
	 *	Long result = dao.deletePhysicallyRawByCriteria(query);
	 * }
	 * </pre>
	 * <p>Note that T is a entity, T_ is a meta model</p>
	 * @param criteriaFactory delete query context for serializable type T
	 * @return number of updated row
	 */
	public long deletePhysicallyRawByCriteria(DeleteQueryGenericContext<T> criteriaFactory) {
		LocalTime startOfMeasurements = LocalTime.now();

		try (AutoCloseable au = beginSession();) {
			Boolean[] isError = new Boolean[] { true };

			try (AutoCloseTransaction autoclose = beginTransaction(isError)) {
				Query query = getEM().createQuery(criteriaFactory.d);
				int result = query.executeUpdate();

				if (LOG.isDebugEnabled()) {
					LocalTime endOfMeasurements = LocalTime.now();
					double millis = startOfMeasurements.until(endOfMeasurements, ChronoUnit.NANOS) / 1000000.0;
					String criteriaString = criteriaFactory.getQueryString(query);

					if (!StringUtils.isBlank(criteriaString)) {
						criteriaString = ":\n    " + criteriaString;
					}

					LOG.debug(
							"{0}: Delete by criteria took {2} ms : statement{1}",
							getType().getSimpleName(), criteriaString,
							NumberFormat.getNumberInstance().format(millis));
				}

				isError[0] = false;

				return result;
			}
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

	}

	/**
	 * Create a query always returning a tuple of objects.
	 * 
	 * @return query object returning tuples
	 */
	public CriteriaQueryGenericContext<T, Tuple> initializeTupleQuery() {
		return initializeQuery(getType(), Tuple.class);
	}

	/**
	 * Create the default query for this beans.
	 *
	 * The default query returns all instances with no specific order.
	 * @param rootType the entity {@link Class} which is handled by the DAO class
	 * @param queryType the type of the represented return object
	 * @param <U> the type of the represented object
	 * @return criteria query for combination of T and U
	 */
	public <U> CriteriaQueryGenericContext<T, U> initializeQuery(Class<T> rootType, Class<U> queryType) {

		CriteriaBuilder cb = getEM().getCriteriaBuilder();
		beginSession();

		CriteriaQuery<U> query = cb.createQuery(queryType);
		Root<T> root = query.from(rootType);

		return CriteriaQueryGenericContext.from(cb, query, root, () -> {
			closeSession();
			return null;
		});
	}

	/**
	 * Create an untyped default query.
	 *
	 * @return new query context
	 */
	public CriteriaQueryContext<T> initializeQuery() {
		CriteriaQueryGenericContext<T, T> f = initializeQuery(getType(), getType());

		return CriteriaQueryContext.createFactoryfrom(f.c, f.q, f.r, () -> {
			closeSession();
			return null;
		});
	}

	/**
	 * Create an untyped delete query.
	 *
	 * @return new query context
	 */
	public DeleteQueryContext<T> initializeDeleteQuery() {
		DeleteQueryGenericContext<T> f = initializeDeleteQuery(getType());

		return DeleteQueryContext.createFactoryfrom(f.c, f.d, f.r, () -> {
			closeSession();
			return null;
		});
	}

	/**
	 * Create an untyped update query.
	 *
	 * @return new query context
	 */
	public UpdateQueryContext<T> initializeUpdateQuery() {
		UpdateQueryGenericContext<T> f = initializeUpdateQuery(getType());

		return UpdateQueryContext.createFactoryfrom(f.c, f.u, f.r, () -> {
			closeSession();
			return null;
		});
	}

	private DeleteQueryGenericContext<T> initializeDeleteQuery(Class<T> rootType) {
		CriteriaBuilder cb = getEM().getCriteriaBuilder();
		beginSession();

		CriteriaDelete<T> query = cb.createCriteriaDelete(rootType);
		Root<T> root = query.from(rootType);

		return DeleteQueryGenericContext.from(cb, query, root, () -> {
			closeSession();
			return null;
		});
	}

	private UpdateQueryGenericContext<T> initializeUpdateQuery(Class<T> rootType) {
		CriteriaBuilder cb = getEM().getCriteriaBuilder();
		beginSession();

		CriteriaUpdate<T> query = cb.createCriteriaUpdate(rootType);
		Root<T> root = query.from(rootType);

		return UpdateQueryGenericContext.from(cb, query, root, () -> {
			closeSession();
			return null;
		});
	}

	/**
	 * Get a {@link CriteriaBuilder} instance.
	 *
	 * The instance is useful to create {@link Order} or other criteria related
	 * expressions.
	 *
	 * @return the criteria builder
	 */
	public CriteriaBuilder getCriteriaBuilder() {
		return getEM().getCriteriaBuilder();
	}

	protected String canonicalBeanName(T bean) {
		String cName = "<null>";

		if (bean != null) {
			cName = bean.getClass().getCanonicalName();
		} else if (getType() != null) {
			cName = getType().getClass().getCanonicalName();
		}

		return cName;
	}

	/**
	 * Save a bean.
	 *
	 * The save may lead to an insert or an update.
	 * 
	 * <p>Example:</p>
	 * <pre>
	 * {@code
	 *	 T saveBean = dao.save(bean);
	 * }
	 * </pre>
	 * <p>Note that bean is a entity T object</p>
	 * 
	 * @param bean represents a entity T object
	 * @return bean
	 */
	public T save(T bean) {
		return save(bean, (DaoCallback<T>[]) null);
	}

	/**
	 * Save a bean.
	 *
	 * The save may lead to an insert or an update.
	 *
	 * @param oldBean represents a bean
	 * @param callbacks Dao CallBack for GenericEntities
	 * @return bean
	 */
	protected T save(T oldBean,	@SuppressWarnings("unchecked") DaoCallback<T>... callbacks) {
		T bean = oldBean;
		UpdateType type = UpdateType.UPDATE;

		T current = find(bean);
		if (current == null) {
			type = UpdateType.ADD;
		} else {
		}

		if (callbacks != null) {
			for (DaoCallback<T> callback : callbacks) {
				if (callback != null) {
					callback.prePersist().accept(bean);
				}
			}
		}

		bean = mergeBean(bean, type, current);

		if (callbacks != null) {
			for (DaoCallback<T> callback : callbacks) {
				if (callback != null) {
					callback.postPersist().accept(bean);
				}
			}
		}

		return bean;
	}

	/**
	 * Delete a bean.
	 *
	 * The delete must not necessarily be physical for all beans.
	 * <p>Example:</p>
	 * <pre>
	 * {@code
	 *	 T deleteBean = dao.delete(bean);
	 * }
	 * </pre>
	 * <p>Note that bean is a entity T object</p>
	 * @param oldBean represents a entity T object
	 * @return bean
	 */
	public T delete(T oldBean) {
		T bean = oldBean;
		if (bean == null) {
			LOG.warn("null passed to delete");
			return bean;
		}

		bean = mergeBean(bean, DELETE, null);

		return bean;
	}

	protected T mergeBean(T bean, UpdateType type) {
		return mergeBean(bean, type, (T) null);
	}

	@SuppressWarnings("unchecked")
	protected T mergeBean(T toBeMergedBean, UpdateType type, T current) {
		StopWatch sw = new StopWatch();
		sw.start();
		T tmpBean = toBeMergedBean;

		UpdateInformation newUpdateInformation = new UpdateInformation(tmpBean, type, tmpBean.getSessionUsername());

		try (AutoCloseable closeableSession = beginSession()) {
			beginTransaction();

			handleUpdatingAudit(toBeMergedBean, current, type);

			switch (type) {
			case ADD:
				getEM().persist(tmpBean);
				getEM().flush();
				break;
			case UPDATE:
				tmpBean = (T) getEM().merge(tmpBean);
				getEM().flush();
				break;
			case DELETE:
				tmpBean = removeBean(tmpBean);
				break;
			}

			if (this instanceof Caching) {
				LOG.debug("invalidate cache");
				((Caching<T>) this).invalidateCache(type, tmpBean);
			}

			if (HibernateProxy.class.isAssignableFrom(tmpBean.getClass())) {
				tmpBean = (T) ((HibernateProxy) tmpBean).getHibernateLazyInitializer().getImplementation();
			}

			commitTransaction();

			// remember information about the operation in
			// case we get an optimistic lock exception.
			updateMap.put(tmpBean.getId(), newUpdateInformation);
		} catch (Exception e) {
			String message = MessageFormat.format("Exception during operation {0}: {1}: {2}.", newUpdateInformation, e.getClass(), e.getMessage());
			UpdateInformation oldUpdateInformation = updateMap.get(tmpBean.getId());
			if (oldUpdateInformation != null) {
				message = String.join(" ",
						message,
						MessageFormat.format("The previous operation for this entity was: {1}.", message, oldUpdateInformation));
			}

			try {
				rollbackTransaction();
			} catch (Exception e2) {
				message = String.join(" ",
						message,
						MessageFormat.format("Additionally, there was an exception trying to roll back the operation: {0}: {1}.", e2.getClass(), e2.getMessage()));
			}

			LOG.error(message);

			throw new PersistenceException(message, e);
		}

		LOG.debug("{0}({1}): merge execution time: {2}", getType(),	tmpBean.getId(), sw.getDuration());
		updateEvent(tmpBean, type);

		return tmpBean;
	}

	@SuppressWarnings("incomplete-switch")
	private void handleUpdatingAudit(T tmpBean, T current, UpdateType updateType) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Class<T> clazz = getType();

		if (clazz.isAnnotationPresent(Audit.class)) {
			AuditHandler handler = getAuditHandler(clazz);

			if (handler != null) {
				switch (updateType) {
				case UPDATE:
					handler.handleUpdate(current, tmpBean);
					break;
				case DELETE:
					handler.handleDelete(tmpBean);
					break;
				}	
			}
		}
	}

	private void handleReadingAudit(CriteriaQueryGenericContext<?, ?> query) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Class<T> clazz = getType();
		if (clazz.isAnnotationPresent(Audit.class)) {
			AuditHandler handler = getAuditHandler(clazz);
			if (handler != null) {
				handler.handleRead(query);
			}
		}
	}

	private AuditHandler getAuditHandler(Class<? extends GenericEntity<? extends Serializable>> clazz)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		AuditHandler handler = handlerMap.get(clazz);

		if (handler == null) {
			Audit auditAnotation = clazz.getAnnotation(Audit.class);
			Class<? extends AuditHandler> handlerClass = auditAnotation.handler();
			if (handlerClass != null) {
				handler = handlerClass.getDeclaredConstructor().newInstance();
				handlerMap.put(clazz, handler);
			}
		}

		return handler;
	}

	@SuppressWarnings("unchecked")
	protected T removeBean(T oldBean) {
		T bean = oldBean;

		bean = findInEM(getType(), bean.getId());
		bean = (T) getEM().merge(bean);
		getEM().remove(bean);

		return bean;
	}

	protected void updateEvent(T bean, UpdateType updateType) {
		try {
			if (Updatable.class.isAssignableFrom(getType())) {
				Updatable<?> upd = (Updatable<?>) bean;
				LOG.debug("bean {0} was updated, type {1}", upd.getId(), updateType);
			}
		} catch (Exception e) {
			LOG.warn("Failed to send update event", e);
		}
	}

	/**
	 * Save all beans (must be of same type) in list.
	 * 
	 * <p>Example:</p>
	 * <pre>
	 * {@code
	 *	 List<T> saveBeans = dao.saveAll(beans);
	 * }
	 * </pre>
	 * <p>Note that beans is a list entity T object</p>
	 * @param beans represents a list entity T object
	 * @return list bean
	 * @throws TransactionRolledbackException this exception must be thrown when 
	 * a call to Session.commit results in a rollback of the current transaction 
	 * 
	 */
	public List<T> saveAll(List<T> beans) throws TransactionRolledbackException {
		final Boolean[] errorFlag = new Boolean[] { true };// per default we assume an error can happen, and set it to
		// false only at the end
		try (AutoCloseable autocloseSession = beginSession()) {
			startTransactionAndSaveList(beans, errorFlag);
		} catch (Exception e) {
			LOG.error("Error while calling saveAll", e);
			throw new TransactionRolledbackException(e.getLocalizedMessage());
		}

		return beans;
	}

	private void startTransactionAndSaveList(List<T> beans, final Boolean[] errorFlag) throws TransactionRolledbackException {
		try (AutoCloseTransaction autocloseTransaction = beginTransaction(errorFlag)) {
			for (ListIterator<T> iter = beans.listIterator(); iter.hasNext();) {
				T bean = iter.next();
				bean = save(bean);
				iter.set(bean);
			}
			errorFlag[0] = false;// no Error encountered,
		} catch (TransactionRolledbackException e) {
			LOG.error("Error, could not save all beans", e);
			throw e;
		}
	}

	/**
	 * Delete all beans (must be of same type) in list.
	 * 
	 * <p>Example:</p>
	 * <pre>
	 * {@code
	 *	 List<T> deleteBeans = dao.deleteAll(beans);
	 * }
	 * </pre>
	 * <p>Note that beans is a list entity T object</p>
	 * @param beans represents a list entity T object
	 * @return list bean
	 */
	public List<T> deleteAll(List<T> beans) {
		final Boolean[] errorFlag = new Boolean[] { true };
		try (AutoCloseable autocloseSession = beginSession()) {
			try (AutoCloseTransaction autocloseTransaction = beginTransaction(errorFlag)) {
				if (beans != null) {
					for (T bean : beans) {
						delete(bean);
					}
				}

				errorFlag[0] = false;

				return beans;
			}
		} catch (Exception e) {
			LOG.error("Error, could not delete all beans", e);
			return beans;
		}

	}

	/**
	 * Delete object and dependent objects.
	 *
	 * It is up to the objects DAO to correctly implement the function
	 *<p>Example:</p>
	 *<pre>
	 *{@code
	 *	T deleteBeans = dao.deleteCascade(bean);
	 *}
	 *</pre>
	 *<p>Note that bean is a entity T object<p>
	 * @param bean represents a bean
	 * @return bean
	 */
	public final T deleteCascade(T bean, CascadeDelete<T> cascadeDelete) {
		T modifiedBean = bean;
		try (AutoCloseable closeableSession = beginSession()) {
			final Boolean[] isError = new Boolean[] { true };
			try (AutoCloseTransaction closeableTransaction = beginTransaction(isError)) {
				cascadeDelete.deleteChildren(modifiedBean);
				modifiedBean = delete(modifiedBean);
				isError[0] = false;
			} // commitTransaction(); - commit is done automatically via try with resources
			// autoclose
		} catch (Exception e) {
			rollbackTransaction();
			throw new PersistenceException(e);
		}

		return modifiedBean;
	}

	/**
	 * Delete all beans with cascade (must be of same type) in list.
	 * 
	 * <p>Example:</p>
	 * <pre>
	 * {@code
	 *	 List<T> deleteBeans = dao.deleteAllCascade(beans);
	 * }
	 * </pre>
	 * <p>Note that beans is a list entity T object</p>
	 * @param beans represents a list entity T object
	 * @return list bean
	 */
	public List<T> deleteAllCascade(List<T> beans, CascadeDelete<T> cascadeDelete) {
		final Boolean[] errorFlag = new Boolean[] { true };
		try (AutoCloseable autocloseSession = beginSession()) {
			try (AutoCloseTransaction autocloseTransaction = beginTransaction(errorFlag)) {
				if (beans != null) {
					for (T bean : beans) {
						deleteCascade(bean, cascadeDelete);
					}
				}

				errorFlag[0] = false;

				return beans;
			}
		} catch (Exception e) {
			LOG.error("Error, could not delete all beans", e);
			return beans;
		}

	}

	/**
	 * Reload a detached bean.
	 *
	 * Try to find (and reload) the given bean.
	 *
	 * @param bean represents a bean
	 * @return bean
	 */
	public T refresh(T bean) {
		if (bean == null) {
			return null;
		}

		getEM().refresh(bean);

		return bean;
	}

	protected T findInEM(Class<T> clazz, Serializable id) {
		return clazz != null && id != null ? getEM().find(clazz, id) : null;
	}

	protected String getTablename() {
		return ReflectionUtilitities.getTablename(getType());
	}

	protected void copyProperties(Object histBean, Object current) {
		ReflectionUtilitities.copyProperties(histBean, current);
	}

	/**
	 * Enable a filter for the current session.
	 *
	 * A session should be started by {@link AbstractDAO#beginSession()}, otherwise
	 * the filter will be valid for the rest of the request.
	 *
	 * The function works by calling {@link Session#enableFilter(String)}.
	 *
	 * @param filterName a string for filter 
	 * @return The Filter instance representing the enabled filter.
	 */
	public Filter enableFilter(String filterName) {
		Session session = getEM();

		return session.enableFilter(filterName);
	}

	/**
	 * Perform a search by a given {@link SearchFilter}.
	 *
	 * This function is generic and it should not be necessarry to overload it for
	 * different objects. Instead, override/implement the function
	 * {@link #getAttributePredicate(CriteriaQueryGenericContext, FilterPredicate, ExpressionMap)}.
	 *<p>Example:</p>
	 *<pre>
	 *{@code
	 *SearchFilter searchFilter = new SearchFilter().add(SearchField.FIELD, value); // value search
	 *List<Tuple> result = dao.findBySearchFilter(searchFilter);
	 *}
	 *</pre>
	 *<p>Note: SearchField a enumeration for search field</p> 
	 * @param searchFilter a search filter combines {@link FilterPredicate}s and {@link FilterOrder}s
	 * @return list of found tuples
	 */
	public List<Tuple> findBySearchFilter(SearchFilter searchFilter) {
		return findBySearchFilter(searchFilter, null);
	}

	/**
	 * Perform a search by a given {@link SearchFilter}.
	 *
	 * This function is generic and it should not be necessary to overload it for
	 * different objects. Instead, override/implement the function
	 * {@link #getAttributePredicate(CriteriaQueryGenericContext, FilterPredicate, ExpressionMap)}.
	 * <p>Example:</p>
	 * <pre>
	 * {@code
	 *   SearchFilter searchFilter = new SearchFilter().add(SearchField.FIELD, value); // value search
	 *   List<Tuple> result = dao
	 *   			.findBySearchFilter(searchFilter, new QuerySettings<Product>()
	 *   			.withFirstResult(0)
	 *   			.withMaxResults(2)
	 *   			.withMarkers(AuditableMarker.ACTIVE)
	 *   			.withOrderAttributes(T_.property)));
	 * }
	 * </pre>
	 * <p>Note: SearchField a enumeration for search field, T_ is meta model</p> 
	 *
	 * @param searchFilter a search filter combines {@link FilterPredicate}s and {@link FilterOrder}s
	 * @param querySettings specify paging, markers,orders...
	 * @return list of tuples which searchfilter found
	 */
	public List<Tuple> findBySearchFilter(SearchFilter searchFilter, QuerySettings<T> querySettings) {
		LOG.debug("find by search filter");

		try (CriteriaQueryGenericContext<T, Tuple> query = initializeQuery(getType(), Tuple.class)) {

			if (querySettings != null) {
				query.setQuerySettings(querySettings);
			}

			AttributePredicates totalAps = searchFilterToAttributePredicates(searchFilter, query);

			// Build query from all predicates and selections and orders.
			query.q.where(totalAps.getPredicatesArray());
			query.q.multiselect(totalAps.getSelectionsArray());
			query.q.orderBy(totalAps.getOrders());

			LOG.debug("query: {0}", query);
			List<Tuple> tuples = findByCriteria(query);
			LOG.debug("found {0} tuples for given predicates", tuples.size());

			return tuples;
		}
	}

	/**
	 * Count number of results of {@link SearchFilter}.
	 * 
	 * <p>
	 * Counting is performed non-distinct, without any {@link QuerySettings}.
	 * </p>
	 * 
	 * @param searchFilter
	 * @return
	 */
	public long countBySearchFilter(SearchFilter searchFilter) {
		return countBySearchFilter(searchFilter, null);
	}

	/**
	 * Count number of results of {@link SearchFilter}.
	 * 
	 * <p>
	 * Counting is performed non-distinct.
	 * </p>
	 * 
	 * @param searchFilter
	 * @param querySettings
	 * @return
	 */
	public long countBySearchFilter(SearchFilter searchFilter, QuerySettings<T> querySettings) {
		return countBySearchFilter(searchFilter, false, querySettings);
	}

	/**
	 * Count number of results of {@link SearchFilter}.
	 * 
	 * <p>Example:</p>
	 * <pre>
	 * {@code
	 *   SearchFilter searchFilter = new SearchFilter().add(SearchField.FIELD, value);
	 *   List<Tuple> result = dao.countBySearchFilter(
	 *   	searchFilter,
	 *   	true,
	 *   	new QuerySettings<MyEntity>().withMarkers(MyMarker.MARK1))
	 * }
	 * </pre>
	 *
	 *
	 * Note: distinct is probably not working as expected, therefore the
	 * function is private (and cannot be called counting distinct) until
	 * an intuitive solution is found.
	 *
	 * @param searchFilter a search filter combines {@link FilterPredicate}s and {@link FilterOrder}s
	 * @param distinct do a distinct count
	 * @param querySettings specify paging, markers,orders...
	 * @return total records after filter
	 */
	private long countBySearchFilter(SearchFilter searchFilter, boolean distinct, QuerySettings<T> querySettings) {
		try (CriteriaQueryGenericContext<T, Long> query = initializeQuery(getType(), Long.class)) {

			if (querySettings != null) {
				query.setQuerySettings(querySettings);
			}

			AttributePredicates totalAps = searchFilterToAttributePredicates(searchFilter, query);

			// Build query from all predicates but orders for counting.
			query.q.where(totalAps.getPredicatesArray());

			var countEx = distinct ? query.c.countDistinct(query.r) : query.c.count(query.r);
			query.q.multiselect(countEx);

			long result = forceSingleResult(findByCriteria(query));

			return result;
		}
	}

	/**
	 * Convert {@link SearchFilter} to {@link AttributePredicates}.
	 *
	 * The {@link AttributePredicates} are needed to build the projections (select
	 * ... from) and the where part of the query.
	 *
	 * @param searchFilter a search filter combines {@link FilterPredicate}s and {@link FilterOrder}s
	 * @param query Query context for serializable type T
	 * @return
	 */
	protected AttributePredicates searchFilterToAttributePredicates(SearchFilter searchFilter, CriteriaQueryGenericContext<T, ?> query) {

		// Reset the search filter, so we son't get predicates from a previous search.
		searchFilter.reset();

		// Build and collect all predicates and all selections.
		var attributePredicates = searchFilter.getAttributePredicates();
		var orderMap = new HashMap<Enum<?>, List<Order>>();

		var expressionMap = query.getCurrentExpressionMap();
		for (var filterPredicate : searchFilter.getFilterPredicates()) {
			// Create the attributes for a single search enum.
			LOG.debug("Adding filter predicate {0}.", filterPredicate);
			var singleEnumAttributePredicates = getAttributePredicate(query, filterPredicate, expressionMap);

			// Add the new selections and predicates to the total collection.
			attributePredicates.addSelections(singleEnumAttributePredicates.getSelections());
			attributePredicates.addPredicates(singleEnumAttributePredicates.getPredicates());

			// Remember the orders (if multiple are found, only first one will be taken).
			orderMap.putIfAbsent(filterPredicate.getSearchEnum(), singleEnumAttributePredicates.getOrders());
		}

		// Add all needed orders.
		for (var filterOrder : searchFilter.getFilterOrders()) {
			var orders = orderMap.get(filterOrder.getSearchEnum());

			if(orders == null) {
				// Sort was not part of selection fields, must be created now.
				var singleEnumAttributePredicates = getAttributePredicate(query,
						new FilterPredicate(filterOrder.getSearchEnum()),
						expressionMap);
				orders = singleEnumAttributePredicates != null ? singleEnumAttributePredicates.getOrders() : null;
			}

			if (orders != null) {
				if (!filterOrder.isAscending()) {
					// Descending is wanted, so reverse the default, which is ascending.
					// Note:
					// A single searchFilter might produce selections, and therefore orders
					// for multiple fields. In this case the whole list of orders will be set
					// here to the same sort order. Whether this makes sense in all cases, or
					// whether multi-select filters will even be used, will be seen.
					// At the moment all filters produce a single selection, so this all
					// does not really matter.
					orders = orders.stream().map(Order::reverse).collect(Collectors.toList());
				}
				// Add all orders.
				attributePredicates.addOrders(orders);
			} else {
				LOG.warn("Search filter order predicate was not created by DAO, ignoring order by ''{0}'' {1}.",
						filterOrder.getSearchEnum(), filterOrder.isAscending() ? "ascending" : "descending");
			}
		}

		return attributePredicates;
	}

	/**
	 * Take a {@link FilterPredicate} and create corresponding
	 * {@link AttributePredicates}.
	 *
	 * <p>
	 * A single {@link FilterPredicate} could be converted to multiple predicates.
	 * (For example: The filter contains an interval, and predicates need to be
	 * constructed to check, whether the interval is contained in an interval of two
	 * entity fields).
	 * </p>
	 *
	 * <p>
	 * Similarly, a single {@link FilterPredicate} might produce multiple result
	 * fields, which need to be part of the selection.
	 * </p>
	 *
	 * <p>
	 * This method defines and handles all predicates.
	 * </p>
	 * 
	 * @param query
	 * @param filterPredicate
	 * @param expressionMap
	 * @return
	 */
	protected AttributePredicates getAttributePredicate(CriteriaQueryGenericContext<T, ?> query, FilterPredicate filterPredicate, ExpressionMap expressionMap) {
		LOG.error("Search predicates for this type {0} of query {1} are not implemented. FilterPredicate : {2}, expressionMap {3}",
				getType(), query, filterPredicate, expressionMap);
		return null;
	}

	/**
	 * Create selection, filter-predicate and order for a String equal search.
	 * 
	 * @param query
	 * @param filterPredicate
	 * @param attrPredicates
	 * @param expression
	 * @return result of add selection operation
	 */
	protected boolean addSelectionOrderAndEqual(CriteriaQueryGenericContext<T, ?> query, FilterPredicate filterPredicate, AttributePredicates attrPredicates, Expression<String> expression) {
		var res = attrPredicates.addSelection(expression);
		attrPredicates.addOrder(query.c.asc(expression));
		var name = filterPredicate.getValue();
		if (name != null) {
			attrPredicates.addPredicate(query.c.equal(expression, name));
		}
		return res;
	}

	/**
	 * Create selection, filter-predicate and order for a String like search.
	 * 
	 * @param query
	 * @param filterPredicate
	 * @param attrPredicates
	 * @param expression
	 * @return result of add selection operation
	 */
	protected boolean addSelectionOrderAndLike(CriteriaQueryGenericContext<T, ?> query, FilterPredicate filterPredicate, AttributePredicates attrPredicates, Expression<String> expression) {
		var res = attrPredicates.addSelection(expression);
		attrPredicates.addOrder(query.c.asc(expression));
		var name = filterPredicate.getValue();
		if (name != null) {
			attrPredicates.addPredicate(query.c.like(expression, "%" + name + "%"));
		}
		return res;
	}

	/**
	 * Add selection, filter-predicate and order for an enum implementing {@link HasCmsName} which will be filtered by a list of possible values.
	 *
	 * <p>
	 * First all enumeration values will be looked up in CMS and converted to the current language.
	 * Next this list is sorted and a case query is prepared giving every possible value it's
	 * sort index. This index will then be used for sorting. In is handled as usually.
	 * </p>
	 *
	 * @param <C>
	 * @param ctx
	 * @param clazz the enumeration which implements HasCmsName
	 * @param filterPredicate searched with in query
	 * @param attributePredicate
	 * @param typeExpression
	 */
	@SuppressWarnings("unchecked")
	protected <C extends HasCmsName> void addCmsEnumSelectionOrderAndIn(CriteriaQueryGenericContext<T, ?> ctx, Class<C> clazz, FilterPredicate filterPredicate, AttributePredicates attributePredicate, Expression<C> typeExpression) {
		addCmsEnumSelectionOrderForValueProvider(ctx, clazz,
				(filter, entries) -> {
					// Create the array class of our Enum
					// and use it to extract an enum array from the filterPredicate.
					var arrayType = Array.newInstance(clazz, 0).getClass();
					var values = (Object[])filterPredicate.getValue(arrayType);
					return values == null ? null : (Collection<C>) List.of(values);
				},
				filterPredicate, attributePredicate, typeExpression);
	}

	/**
	 * Add selection, filter-predicate and order for an enum implementing {@link HasCmsName} which will be filtered by String comtained in the current locales CMS name.
	 *
	 * <p>
	 * First all enumeration values will be looked up in CMS and converted to the current language.
	 * Next this list is sorted and a case query is prepared giving every possible value it's
	 * sort index. This index will then be used for sorting. Contains will be performed on the
	 * CMS entries of the enum and then the resulting enums will be searched.
	 * </p>
	 *
	 * @param <C>
	 * @param ctx
	 * @param clazz the enumeration which implements HasCmsName
	 * @param filterPredicate searched with in query
	 * @param attributePredicate
	 * @param typeExpression
	 */
	protected <C extends HasCmsName> void addCmsEnumSelectionOrderAndContains(CriteriaQueryGenericContext<T, ?> ctx, Class<C> clazz, FilterPredicate filterPredicate, AttributePredicates attributePredicate, Expression<C> typeExpression) {
		addCmsEnumSelectionOrderForValueProvider(ctx, clazz,
				(filter, entries) -> {
					var value = filterPredicate.getValue(String.class);
					Collection<C> values = null;
					// Filter the enum values where cms name contains search text.
					if (value != null) {
						final var contained = value.toLowerCase();
						values = entries.stream()
								.filter(e -> e.getValue().toLowerCase().contains(contained))
								.map(e -> e.getKey())
								.toList();
					}
					return values;
				},
				filterPredicate, attributePredicate, typeExpression);
	}

	/**
	 * Add selection, filter-predicate and order for an enum implementing {@link HasCmsName} which will be filtered by a list of possible values.
	 *
	 * <p>
	 * First all enumeration values will be looked up in CMS and converted to the current language.
	 * Next this list is sorted and a case query is prepared giving every possible value it's
	 * sort index. This index will then be used for sorting. Contains will be performed on the
	 * CMS entries of the enum and then the resulting enums will be searched.
	 * </p>
	 * 
	 * <p>
	 * Note, this function should probably not be used directly but as a helper to create
	 * predicates for enum based searches. See examples in this class how to use it.
	 * </p>
	 *
	 * @param <C>
	 * @param ctx
	 * @param clazz the enumeration which implements HasCmsName
	 * @param valuesProvider function getting a {@link FilterPredicate} and a List of {@link Entry} (key is the enumeration, value is the CMS String), which should return the enum values that should be filtered
	 * @param filterPredicate searched with in query
	 * @param attributePredicate
	 * @param typeExpression
	 */
	protected <C extends HasCmsName> void addCmsEnumSelectionOrderForValueProvider(CriteriaQueryGenericContext<T, ?> ctx, Class<C> clazz, BiFunction<FilterPredicate, List<Map.Entry<C, String>>, Collection<C>> valuesProvider, FilterPredicate filterPredicate, AttributePredicates attributePredicate, Expression<C> typeExpression) {
		attributePredicate.addSelection(typeExpression);

		// Get a list of CMS entries sorted by the CMS String in the current locale.
		// Every entry contains the enum as the key and the CMS String as the value.
		var entries = EnumService.getSortedByCmsName(clazz);

		// Filter the enum values, that match the filter predicate.
		var values = valuesProvider.apply(filterPredicate, entries);

		if(values != null) {
			if(values.size() > 0) {
				attributePredicate.addPredicate(typeExpression.in(values));
			}
			else {
				// empty list
				attributePredicate.addPredicate(ctx.alwaysFalse());
			}
		}

		// Create a case query that replaces every enum by it's sort index.
		var caseExpression = ctx.c.selectCase();

		// Get enum entries sorted by CMS name.
		var i = 0;
		for (var entry : entries) {
			caseExpression = caseExpression.when(ctx.c.equal(typeExpression, entry.getKey()), i);
			i++;
		}
		caseExpression.otherwise(i);

		// Add order for the sort index.
		attributePredicate.addOrder(ctx.c.asc(caseExpression));
	}

	/**
	 * Return the last known {@link UpdateInformation} for the given key.
	 *
	 * Note, that {@link UpdateInformation}s are stored in a ring buffer with a
	 * fixed size. If the buffer is full, the oldest will be overwritten.
	 *
	 * So, this function could return null, even if there was an update a long time
	 * ago.
	 *
	 * @param key a primary key
	 * @return Returns the value to which the specified key is mapped,
	 * or {@code null} if this map contains no mapping for the key.
	 */
	public UpdateInformation getUpdateInformation(Serializable key) {
		return updateMap.get(key);
	}

	/**
	 * Expect a collection containing at maximum a single result and return the single result or <code>null</code> if there is none.
	 *
	 * If there is more than one entry, return the first one, but log a warning.
	 *
	 * @param <CT>
	 * @param results
	 * @return
	 */
	public <CT> CT forceSingleResult(Collection<CT> results) {
		CT result = null;

		if(results != null) {
			int entries = results.size();
			if(entries > 0) {
				result = results.stream().findFirst().get();
				if (entries > 1) {
					LOG.warn("Expected single result but got {0} entries. The ids are: {1}",
							entries,
							results.stream()
							.map(r -> {
								String string = "null";
								if(r != null) {
									if(r instanceof GenericEntity) {
										GenericEntity<?> entity = (GenericEntity<?>) r;
										string = "%s:%s".formatted(entity.getClass(), entity.getId());
									}
									else {
										string = r.toString();
									}
								}
								return string;
							})
							.collect(Collectors.joining(", ")));
				}
			}
		} else {
			LOG.warn("Expected a collection with a single result, but the collection is null (which is very unusual).");
		}

		return result;
	}

	/**
	 * Information need to find out about an update operation.
	 */
	@SuppressWarnings("rawtypes")
	public static class UpdateInformation {
		UpdateType type = null;
		String className = null;
		String id = null;
		Header header = null;
		String user = null;
		String thread = null;

		public UpdateInformation(GenericEntity<?> bean, UpdateType type,
				String sessionUser) {
			this.type = type;

			this.className = bean.getClass().getSimpleName();

			if (bean.getId() != null) {
				this.id = bean.getId().toString();
			}

			this.header = bean instanceof AuditableEntity ? ((AuditableEntity) bean).getHeader() : null;

			this.user = sessionUser;
			this.thread = Thread.currentThread().getName();
		}

		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}

		/**
		 * @return the header
		 */
		public Header getHeader() {
			return header;
		}

		/**
		 * @return the type
		 */
		public UpdateType getType() {
			return type;
		}

		/**
		 * @return the user
		 */
		public String getUser() {
			return user;
		}

		/**
		 * @return the thread
		 */
		public String getThread() {
			return thread;
		}

		@Override
		public String toString() {
			return this.getClass().getSimpleName() + " [type=" + type + ", class=" + className + ", id=" + id
					+ header + ", thread=" + thread + ", user=" + user + "]";
		}
	}
}
