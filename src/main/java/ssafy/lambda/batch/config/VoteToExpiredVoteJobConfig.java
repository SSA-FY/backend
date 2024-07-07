package ssafy.lambda.batch.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import ssafy.lambda.batch.step.ForienkeySetting;
import ssafy.lambda.batch.step.InfoToExpiredInfo;
import ssafy.lambda.batch.step.VoteToExpiredVote;

@Configuration
@RequiredArgsConstructor
public class VoteToExpiredVoteJobConfig {

    private final EntityManagerFactory entityManagerFactory;
    private final VoteToExpiredVote voteToExpiredVote;
    private final InfoToExpiredInfo infoToExpiredInfo;
    private final ForienkeySetting forienkeySetting;

    /**
     * 1. VoteInfo to ExpiredVoteInfo and Remove VoteInfo
     * 2. Vote to ExpiredVote and Remove Vote
     * 3. Forienkey Setting
     * @param jobRepository
     * @return
     */
    @Bean
    public Job voteToExpiredVoteJob(JobRepository jobRepository) {
        return new JobBuilder("voteToExpiredVoteJob", jobRepository)
                .start(infoToExpiredInfo.infoToExpiredInfoStep(jobRepository, transactionManager(),
                        entityManagerFactory))
                .next(voteToExpiredVote.voteToExpiredVoteStep(jobRepository, transactionManager(),
                        entityManagerFactory))
                .next(forienkeySetting.forienkeySettingStep(jobRepository, transactionManager()))
                .build();

    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
