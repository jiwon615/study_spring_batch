package com.study.springbatch;

import com.study.springbatch.config.v1.JobInstanceConfigV1;
import com.study.springbatch.config.v1.JobInstanceRunnerV1;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

//@Import(HelloJobConfiguration.class)
@Import({JobInstanceConfigV1.class, JobInstanceRunnerV1.class})
@EnableBatchProcessing
@SpringBootApplication(scanBasePackages = "com.study.springbatch.basic")
public class SpringbatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbatchApplication.class, args);
	}

}
