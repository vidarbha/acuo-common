package com.acuo.common.specification;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NotSpecificationTest
{

	@Test
	public void testAndIsSatisifedBy() throws Exception
	{
		AlwaysTrueSpec trueSpec = new AlwaysTrueSpec();
		AlwaysFalseSpec falseSpec = new AlwaysFalseSpec();
		NotSpecification<Object> notSpecification = new NotSpecification<Object>(trueSpec);
		assertFalse(notSpecification.isSatisfiedBy(new Object()));
		notSpecification = new NotSpecification<Object>(falseSpec);
		assertTrue(notSpecification.isSatisfiedBy(new Object()));
	}
}
