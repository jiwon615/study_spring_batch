package com.study.springbatch;

import com.study.springbatch.config.part2_job.v3.CustomJobParametersIncrementer;
import com.study.springbatch.config.part2_job.v3.JobRunnerV3;
import com.study.springbatch.config.part2_job.v3.SimpleJobConfigV3;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

//@Import(HelloJobConfiguration.class)
//@Import({JobInstanceConfigV1.class, JobRunnerV1.class})
//@Import({JobParametersConfigV2.class, JobRunnerV2.class})
//@Import({JobExecutionConfigV3.class, JobRunnerV3.class})
//@Import({StepExecutionConfigV4.class, JobRunnerV4.class})
//@Import({ExecutionContextConfigV5.class, JobRunnerV5.class, ExecutionTasklet1.class, ExecutionTasklet2.class, ExecutionTasklet3.class, ExecutionTasklet4.class})
//@Import({JobRepositoryConfigV6.class, JobRunnerV6.class, JobRepositoryListener.class, CustomBatchConfigurer.class})
//@ComponentScan("com.study.springbatch.config.part1_domain.v7")
//@Import({FirstJobConfigV1.class, SecondJobConfigV1.class}) // 여기서부터 part2
//@Import({JobBuilderFactoryConfigV2.class, JobRunnerV2.class})
@Import({SimpleJobConfigV3.class, JobRunnerV3.class, CustomJobParametersIncrementer.class})
@EnableBatchProcessing
@SpringBootApplication(scanBasePackages = "com.study.springbatch.basic")
public class SpringbatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbatchApplication.class, args);
	}

}
