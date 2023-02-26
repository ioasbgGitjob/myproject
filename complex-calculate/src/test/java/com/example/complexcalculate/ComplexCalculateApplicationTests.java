package com.example.complexcalculate;

import com.example.complexcalculate.entity.ExampleCalculate;
import com.example.complexcalculate.service.ChangeFormulaService;
import com.ismail.mxreflection.core.Calculator;
import com.ismail.mxreflection.factory.MXFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ComplexCalculateApplicationTests {

	private final ChangeFormulaService changeFormulaService;
@Autowired
	ComplexCalculateApplicationTests(ChangeFormulaService changeFormulaService) {
		this.changeFormulaService = changeFormulaService;
	}


	@Test
	void contextLoads() {
		ExampleCalculate exampleCalculate = new ExampleCalculate();
		exampleCalculate.setField1("5");
		exampleCalculate.setField2(11);
		log.info("初始化" + exampleCalculate.toString());

		Calculator<ExampleCalculate> calculator = MXFactory.createCalculator(ExampleCalculate.class);
		calculator.calculate(exampleCalculate);
		log.info("默认公式:f1+f2的值:" + exampleCalculate.getField3());

		try {
			Class<?> ex = changeFormulaService.updateAnnotationValue(exampleCalculate.getClass());
		} catch (Exception e) {
			log.error("设置公式异常:", e);
		}

		Calculator<ExampleCalculate> calculator1 = MXFactory.createCalculator(ExampleCalculate.class);
		calculator1.calculate(exampleCalculate);
		log.info("新公式的值:" + exampleCalculate.getField3());


		ExampleCalculate exampleCalculate2 = new ExampleCalculate();
		exampleCalculate2.setField1("5");
		exampleCalculate2.setField2(11);
		Calculator<ExampleCalculate> calculator2 = MXFactory.createCalculator(ExampleCalculate.class);
		calculator2.calculate(exampleCalculate2);
		log.info("新的对象exampleCalculate2:" + exampleCalculate2.getField3());
	}

}
