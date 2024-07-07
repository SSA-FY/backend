package ssafy.lambda.batch.step;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import ssafy.lambda.board.dto.ExpiredVoteWrapper;
import ssafy.lambda.board.repository.ExpiredVoteInfoRepository;

@Configuration
@RequiredArgsConstructor
public class ForienkeySetting {

    private final ExpiredVoteInfoRepository expiredVoteInfoRepository;

    @Bean
    public Step forienkeySettingStep(JobRepository jobRepository,
        PlatformTransactionManager transactionManager) {
        return new StepBuilder("forienkeySettingStep", jobRepository)
            .tasklet(((contribution, chunkContext) -> {
                List<ExpiredVoteWrapper> expiredVoteWrappers = expiredVoteInfoRepository.findByExpiredVoteIsNull();
                for (ExpiredVoteWrapper expiredVoteWrapper : expiredVoteWrappers) {
                    expiredVoteWrapper.getExpiredVoteInfo()
                                      .addExpiredVote(expiredVoteWrapper.getExpiredVote());
                }
                return RepeatStatus.FINISHED;
            }), transactionManager)
            .build();
    }
}
