package com.axonivy.utils.persistence.test.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.axonivy.utils.persistence.IvyEntityManager;
import com.axonivy.utils.persistence.dao.AbstractDAO;

public class TransactionCleanupTest {

	private static class TestDAO extends AbstractDAO {
		@Override
		public String getPersistenceUnitName() {
			return "test";
		}
	}

	@AfterEach
	public void cleanup() {
		IvyEntityManager.getInstance().clearThreadlocals();
	}

	@Test
	public void closeSessionClearsThreadLocalSessionEvenWhenCloseFails() throws Exception {
		IvyEntityManager entityManager = IvyEntityManager.getInstance();
		entityManager.clearThreadlocals();

		Session session = newSessionProxy();

		Object persistenceContext = newPersistenceContext();
		setField(persistenceContext, "hibernateSession", session);
		setThreadLocalField(entityManager, "sessions", 1);
		HashMap<String, Object> contexts = new HashMap<>();
		contexts.put("test.test", persistenceContext);
		setThreadLocalField(entityManager, "threadLocalPersistenceContexts", contexts);

		entityManager.closeSession();

		assertThat(getField(persistenceContext, "hibernateSession")).isNull();
	}

	@Test
	public void rollbackTransactionRemovesThreadLocalStateEvenWhenRollbackFails() throws Exception {
		TestDAO dao = new TestDAO();
		Transaction transaction = newTransactionProxy();

		Object managedTransaction = newManagedTransaction(dao);
		setField(managedTransaction, "transaction", transaction);
		setField(managedTransaction, "count", 1);
		setField(managedTransaction, "isActive", true);

		@SuppressWarnings("unchecked")
		ThreadLocal<Object> threadLocalTransaction = (ThreadLocal<Object>) getStaticField(AbstractDAO.class,
				"threadLocalTransaction");
		threadLocalTransaction.set(managedTransaction);
		@SuppressWarnings({ "rawtypes", "unchecked" })
		Map currentTransactions = AbstractDAO.getCurrentTransactions();
		currentTransactions.put(Thread.currentThread(), managedTransaction);

		assertThrows(RuntimeException.class, dao::rollbackTransaction);

		assertThat(threadLocalTransaction.get()).isNull();
		assertThat(AbstractDAO.getCurrentTransactions()).doesNotContainKey(Thread.currentThread());
	}

	@Test
	public void managedTransactionRollbackClearsActiveFlagWhenRollbackFails() throws Exception {
		TestDAO dao = new TestDAO();
		Transaction transaction = newTransactionProxy();

		Object managedTransaction = newManagedTransaction(dao);
		setField(managedTransaction, "transaction", transaction);
		setField(managedTransaction, "count", 2);
		setField(managedTransaction, "isActive", true);

		Method rollback = managedTransaction.getClass().getDeclaredMethod("rollback");
		rollback.setAccessible(true);

		assertThatThrownBy(() -> rollback.invoke(managedTransaction))
				.isInstanceOf(InvocationTargetException.class)
				.hasCauseInstanceOf(RuntimeException.class)
				.hasRootCauseMessage("rollback failed");

		assertThat(getField(managedTransaction, "count")).isEqualTo(0);
		assertThat(getField(managedTransaction, "isActive")).isEqualTo(false);
	}

	private Object newPersistenceContext() throws Exception {
		Class<?> contextClass = Class.forName("com.axonivy.utils.persistence.IvyEntityManager$PersistenceContext");
		Constructor<?> ctor = contextClass.getDeclaredConstructor(String.class, String.class);
		ctor.setAccessible(true);
		return ctor.newInstance("test", "test");
	}

	private Object newManagedTransaction(TestDAO dao) throws Exception {
		for (Class<?> nested : AbstractDAO.class.getDeclaredClasses()) {
			if (nested.getSimpleName().equals("ManagedTransaction")) {
				Constructor<?> ctor = nested.getDeclaredConstructor(AbstractDAO.class);
				ctor.setAccessible(true);
				return ctor.newInstance(dao);
			}
		}
		throw new IllegalStateException("ManagedTransaction class not found");
	}

	private static Session newSessionProxy() {
		InvocationHandler handler = (proxy, method, args) -> {
			switch (method.getName()) {
			case "isOpen":
				return true;
			case "clear":
				return null;
			case "close":
				throw new RuntimeException("close failed");
			case "toString":
				return "SessionProxy";
			case "hashCode":
				return System.identityHashCode(proxy);
			case "equals":
				return proxy == args[0];
			default:
				return null;
			}
		};
		return (Session) Proxy.newProxyInstance(Session.class.getClassLoader(), new Class<?>[] { Session.class }, handler);
	}

	private static Transaction newTransactionProxy() {
		InvocationHandler handler = (proxy, method, args) -> {
			switch (method.getName()) {
			case "rollback":
				throw new RuntimeException("rollback failed");
			case "toString":
				return "TransactionProxy";
			case "hashCode":
				return System.identityHashCode(proxy);
			case "equals":
				return proxy == args[0];
			default:
				return null;
			}
		};
		return (Transaction) Proxy.newProxyInstance(Transaction.class.getClassLoader(),
				new Class<?>[] { Transaction.class }, handler);
	}

	private static Object getStaticField(Class<?> type, String fieldName) throws Exception {
		Field field = type.getDeclaredField(fieldName);
		field.setAccessible(true);
		return field.get(null);
	}

	private static Object getField(Object target, String fieldName) throws Exception {
		Field field = target.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		return field.get(target);
	}

	private static void setField(Object target, String fieldName, Object value) throws Exception {
		Field field = target.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(target, value);
	}

	private static void setThreadLocalField(Object target, String fieldName, Object value) throws Exception {
		@SuppressWarnings("unchecked")
		ThreadLocal<Object> threadLocal = (ThreadLocal<Object>) getField(target, fieldName);
		threadLocal.set(value);
	}
}
