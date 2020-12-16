package cn.cnic.component.flow.jpa.repository;

import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.cnic.ApplicationTests;
import cn.cnic.base.util.LoggerUtil;
import cn.cnic.component.flow.entity.Flow;


public class FlowJpaRepositoryTest extends ApplicationTests {

	@Autowired
	private FlowJpaRepository flowJpaRepository;

	Logger logger = LoggerUtil.getLogger();

	@Test
	public void testGetOne() {
		Flow ff8081816dd7c769016dd7e95ecc0002 = flowJpaRepository.getOne("ff8081816dd7c769016dd7e95ecc0002");
		logger.info(ff8081816dd7c769016dd7e95ecc0002 + "");
	}

}
