package ssafy.lambda.batch.step;

import jakarta.persistence.EntityManagerFactory;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import ssafy.lambda.batch.step.writer.ExpiredVoteInfoItemWriter;
import ssafy.lambda.board.entity.ExpiredVoteInfo;
import ssafy.lambda.vote.entity.VoteInfo;

@Configuration
@RequiredArgsConstructor
public class InfoToExpiredInfo {

    private final ExpiredVoteInfoItemWriter expiredVoteInfoItemWriter;

    @Bean
    public Step infoToExpiredInfoStep(JobRepository jobRepository,
        PlatformTransactionManager transactionManager,
        EntityManagerFactory entityManagerFactory) {
        return new StepBuilder("infoToExpiredInfoStep", jobRepository)
            .<VoteInfo, ExpiredVoteInfo>chunk(10, transactionManager)
            .reader(voteInfoItemReader(entityManagerFactory))
            .processor(voteInfoItemProcessor())
            .writer(expiredVoteInfoItemWriter)
            .build();
    }


    @Bean
    public ItemReader<VoteInfo> voteInfoItemReader(EntityManagerFactory entityManagerFactory) {
        Instant time = Instant.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                                                       .withZone(ZoneId.of("Asia/Seoul"));
        String now = formatter.format(time);

        return new JpaPagingItemReaderBuilder<VoteInfo>()
                .name("voteInfoItemReader")
                .queryString("select vi "
                        + "from VoteInfo vi "
                        + "where vi.id in "
                        + "(select vis.id "
                        + "from VoteInfo vis "
                        + "inner join vis.vote v "
                        + "where v.expiredAt < :now"
                        + ")")
                .parameterValues(Map.of("now", Instant.parse(now)))
                .entityManagerFactory(entityManagerFactory)
                .pageSize(100)
                .build();

    }

    @Bean
    public ItemProcessor<VoteInfo, ExpiredVoteInfo> voteInfoItemProcessor() {
        return (voteInfo) -> ExpiredVoteInfo.builder()
                                            .voter(voteInfo.getVoter())
                                            .votee(voteInfo.getVotee())
                                            .opinion(voteInfo.getOpinion())
                                            .isOpen(voteInfo.getIsOpen())
                                            .createdAt(voteInfo.getCreatedAt())
                                            .voteId(voteInfo.getVote()
                                                            .getId())
                                            .build();
    }

}
