package com.axonivy.utils.persistence.daos;

import javax.persistence.criteria.Expression;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MessageFormatMessageFactory;

import com.axonivy.utils.persistence.dao.CriteriaQueryContext;
import com.axonivy.utils.persistence.dao.GenericIdEntityDAO;
import com.axonivy.utils.persistence.entities.Invoice;
import com.axonivy.utils.persistence.entities.Invoice_;

public class InvoiceDAO extends GenericIdEntityDAO<Invoice_, Invoice> implements BaseDAO{
	private static final Logger LOG = LogManager.getLogger(new MessageFormatMessageFactory());
	private static final InvoiceDAO INSTANCE = new InvoiceDAO();

	public static InvoiceDAO get() {
		return INSTANCE;
	}

	@Override
	protected Class<Invoice> getType() {
		return Invoice.class;
	}

	public int countPositions(String invoiceId) {
		int result = 0;

		Invoice invoice = null;
		try (CriteriaQueryContext<Invoice> ctx = initializeQuery()) {
			Expression<String> idExpr = getExpression(null, ctx.r, Invoice_.id);
			ctx.whereEq(idExpr, invoiceId);
			invoice = forceSingleResult(findByCriteria(ctx));
			// Do we have a session here?
			LOG.info("Found invoice position: {0}", invoice.getPositions().get(0).getDescription());
			result = invoice.getPositions().size();
		}
		return result;
	}

}
