package com.justinsafford.cfUtilities.integration;

import com.justinsafford.cfUtilities.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ContextLoads {

	@Test
	public void contextLoads() {
	}

}
