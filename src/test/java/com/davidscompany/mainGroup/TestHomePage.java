package com.davidscompany.mainGroup;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import com.davidscompany.mainGroup.Sophia.LoginPage;
import com.davidscompany.mainGroup.Sophia.SophiaApplication;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage
{
	private WicketTester tester;

	@Before
	public void setUp()
	{
		tester = new WicketTester(new SophiaApplication());
	}

	@Test
	public void homepageRendersSuccessfully()
	{
		//start and render the test page
		tester.startPage(LoginPage.class);

		//assert rendered page class
		tester.assertRenderedPage(LoginPage.class);
	}

}
