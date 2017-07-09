package com.jz.crm.base;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-mvc-context.xml",
		"classpath:spring/spring-db-context.xml", "classpath:spring/spring-application-context.xml",
		"classpath:applicationContext.xml" })
@Transactional
public class BaseIntegrationTest extends AbstractJUnit4SpringContextTests {
	
}
