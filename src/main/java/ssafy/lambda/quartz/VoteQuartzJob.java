package ssafy.lambda.quartz;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoteQuartzJob extends QuartzJobBean {

    private final Job voteToExpiredVoteJob;
    private final JobLauncher jobLauncher;

    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLocalDateTime("executionTime", LocalDateTime.now())
                .toJobParameters();

        jobLauncher.run(voteToExpiredVoteJob, jobParameters);
    }
}
