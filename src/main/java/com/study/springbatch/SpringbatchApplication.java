package com.study.springbatch;

import com.study.springbatch.config.v2.JobParametersConfigV2;
import com.study.springbatch.config.v2.JobRunnerV2;
import com.study.springbatch.config.v3.JobExecutionConfigV3;
import com.study.springbatch.config.v3.JobRunnerV3;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

//@Import(HelloJobConfiguration.class)
//@Import({JobInstanceConfigV1.class, JobRunnerV1.class})
//@Import({JobParametersConfigV2.class, JobRunnerV2.class})
@Import({JobExecutionConfigV3.class, JobRunnerV3.class})
@EnableBatchProcessing
@SpringBootApplication(scanBasePackages = "com.study.springbatch.basic")
public class SpringbatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbatchApplication.class, args);
	}

}
