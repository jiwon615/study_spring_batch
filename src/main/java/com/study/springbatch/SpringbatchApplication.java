package com.study.springbatch;

import com.study.springbatch.config.part3_step.v2.TaskBasedAndChunkBasedConfigV2;
import com.study.springbatch.config.part3_step.v3.CustomTasklet;
import com.study.springbatch.config.part3_step.v3.TaskletStepConfigV3;
import com.study.springbatch.config.part3_step.v4.JobStepConfigV4;
import com.study.springbatch.config.part4_flow.v1.FlowJobConfigV1;
import com.study.springbatch.config.part4_flow.v2.FlowJobStartNextConfigV2;
import com.study.springbatch.config.part4_flow.v2.JobRunnerV2;
import com.study.springbatch.config.part4_flow.v3.BatchStatusExitStatusConfigV3;
import com.study.springbatch.config.part4_flow.v3.JobRunnerV3;
import com.study.springbatch.config.part4_flow.v4.CustomDecider;
import com.study.springbatch.config.part4_flow.v4.JobExeuctionDeciderStatusConfigV4;
import com.study.springbatch.config.part4_flow.v4.JobRunnerV4;
import com.study.springbatch.config.part4_flow.v5.FlowStepConfigV5;
import com.study.springbatch.config.part4_flow.v5.JobRunnerV5;
import com.study.springbatch.config.part4_flow.v6.CustomJobListener;
import com.study.springbatch.config.part4_flow.v6.CustomStepListener;
import com.study.springbatch.config.part4_flow.v6.JobRunnerV6;
import com.study.springbatch.config.part4_flow.v6.JobScopeConfigV6;
import com.study.springbatch.config.part5_chunkBasic.v1.ChunkConfigV1;
import com.study.springbatch.config.part5_chunkBasic.v1.JobRunnerV1;
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
//@Import({SimpleJobConfigV3.class, JobRunnerV3.class, CustomJobParametersIncrementer.class})
//@Import({StepBuilderConfigV1.class, JobRunnerV1.class}) // 여기부터 part3
//@Import({TaskBasedAndChunkBasedConfigV2.class, JobRunnerV2.class})
//@Import({TaskletStepConfigV3.class, JobRunnerV3.class, CustomTasklet.class})
//@Import(JobStepConfigV4.class)
//@Import(FlowJobConfigV1.class) // 여기부터 part4
//@Import({FlowJobStartNextConfigV2.class, JobRunnerV2.class})
//@Import({BatchStatusExitStatusConfigV3.class, JobRunnerV3.class})
//@Import({JobExeuctionDeciderStatusConfigV4.class, JobRunnerV4.class, CustomDecider.class})
//@Import({FlowStepConfigV5.class, JobRunnerV5.class})
//@Import({JobScopeConfigV6.class, JobRunnerV6.class, CustomJobListener.class, CustomStepListener.class})
@Import({ChunkConfigV1.class, JobRunnerV1.class}) // 여기부터 part5
@EnableBatchProcessing
@SpringBootApplication(scanBasePackages = "com.study.springbatch.basic")
public class SpringbatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbatchApplication.class, args);
	}

}
