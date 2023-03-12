package com.study.springbatch;

import com.study.springbatch.config.v4.JobRunnerV4;
import com.study.springbatch.config.v4.StepExecutionConfigV4;
import com.study.springbatch.config.v5.*;
import com.study.springbatch.config.v6.CustomBatchConfigurer;
import com.study.springbatch.config.v6.JobRepositoryConfigV6;
import com.study.springbatch.config.v6.JobRepositoryListener;
import com.study.springbatch.config.v6.JobRunnerV6;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

//@Import(HelloJobConfiguration.class)
//@Import({JobInstanceConfigV1.class, JobRunnerV1.class})
//@Import({JobParametersConfigV2.class, JobRunnerV2.class})
//@Import({JobExecutionConfigV3.class, JobRunnerV3.class})
//@Import({StepExecutionConfigV4.class, JobRunnerV4.class})
//@Import({ExecutionContextConfigV5.class, JobRunnerV5.class, ExecutionTasklet1.class, ExecutionTasklet2.class, ExecutionTasklet3.class, ExecutionTasklet4.class})
//@Import({JobRepositoryConfigV6.class, JobRunnerV6.class, JobRepositoryListener.class, CustomBatchConfigurer.class})
@ComponentScan("com.study.springbatch.config.v7")
@EnableBatchProcessing
@SpringBootApplication(scanBasePackages = "com.study.springbatch.basic")
public class SpringbatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbatchApplication.class, args);
	}

}
