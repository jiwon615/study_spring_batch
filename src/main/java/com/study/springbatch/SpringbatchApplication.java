package com.study.springbatch;

import com.study.springbatch.config.part6_chunk_itemReader.JobRunner;
import com.study.springbatch.config.part6_chunk_itemReader.v8.Address;
import com.study.springbatch.config.part6_chunk_itemReader.v8.Customer_p6v8;
import com.study.springbatch.config.part6_chunk_itemReader.v8.DB_JpaPagingItemReaderConfigV8;
import com.study.springbatch.config.part6_chunk_itemReader.v9.CustomerService;
import com.study.springbatch.config.part6_chunk_itemReader.v9.ItemReaderAdapterConfigV9;
import com.study.springbatch.config.part7_chunk_itemWriter.JobRunnerPart7;
import com.study.springbatch.config.part7_chunk_itemWriter.v1.FlatFiles_DelimitedLineAggregatorConfigV1;
import com.study.springbatch.config.part7_chunk_itemWriter.v2.CustomerP7V2;
import com.study.springbatch.config.part7_chunk_itemWriter.v2.JsonFileItemWriterConfigV2;
import com.study.springbatch.config.part7_chunk_itemWriter.v3.Customer;
import com.study.springbatch.config.part7_chunk_itemWriter.v3.JdbcBatchItemWriterConfigV3;
import com.study.springbatch.config.part8_itemprocessor.JobRunnerPart8;
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
//@Import({ChunkConfigV1.class, JobRunnerV1.class}) // 여기부터 part5
//@ComponentScan("com.study.springbatch.config.part6_chunk_itemReader") // 여기부터 part6
//@Import({FlatFilesConfigV2.class, Customer.class, JobRunnerV2.class})
//@Import({FlatFiles_FixedLengthTokenizerConfigV3.class, JobRunnerV3.class, Customer.class})
//@Import({JsonItemReaderConfigV4.class, JobRunnerV4.class})
//@Import({DB_JdbcCursorItemReaderConfigV5 .class, JobRunner.class, Customer.class})
//@Import({DB_JpaCursorItemReaderConfigV6.class, JobRunner.class, Customer.class})
//@Import({DB_JdbcPagingItemReaderConfigV7.class, Customer.class})
//@Import({DB_JpaPagingItemReaderConfigV8.class, JobRunner.class, Customer_p6v8.class, Address.class})
//@Import({ItemReaderAdapterConfigV9.class, JobRunner.class, CustomerService.class})
//@Import({FlatFiles_DelimitedLineAggregatorConfigV1.class, JobRunnerPart7.class, Customer.class})
//@Import({JsonFileItemWriterConfigV2.class, CustomerP7V2.class, JobRunnerPart7.class})
//@Import({JdbcBatchItemWriterConfigV3.class, Customer.class, JobRunnerPart7.class})
//@ComponentScan("com.study.springbatch.config.part8_itemprocessor.v1")
@ComponentScan("com.study.springbatch.config.part8_itemprocessor.v2")
@Import(JobRunnerPart8.class)
@EnableBatchProcessing
@SpringBootApplication(scanBasePackages = "com.study.springbatch.basic")
public class SpringbatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbatchApplication.class, args);
	}

}
