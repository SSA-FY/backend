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
import ssafy.lambda.batch.step.writer.ExpiredVoteItemWriter;
import ssafy.lambda.board.entity.ExpiredVote;
import ssafy.lambda.vote.entity.Vote;

@Configuration
@RequiredArgsConstructor
public class VoteToExpiredVote {

    private final ExpiredVoteItemWriter expiredVoteItemWriter;

    @Bean
    public Step voteToExpiredVoteStep(JobRepository jobRepository,
        PlatformTransactionManager transactionManager,
        EntityManagerFactory entityManagerFactory) {
        return new StepBuilder("voteToExpiredVoteStep", jobRepository)
            .<Vote, ExpiredVote>chunk(10, transactionManager)
            .reader(voteItemReader(entityManagerFactory))
            .processor(voteItemProcessor())
            .writer(expiredVoteItemWriter)
            .build();
    }

    @Bean
    public ItemReader<Vote> voteItemReader(EntityManagerFactory entityManagerFactory) {
        Instant time = Instant.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                                                       .withZone(ZoneId.of("Asia/Seoul"));
        String now = formatter.format(time);

        return new JpaPagingItemReaderBuilder<Vote>()
                .name("voteItemReader")
                .queryString("select v from Vote v where v.expiredAt < :now")
                .parameterValues(Map.of("now", Instant.parse(now)))
                .entityManagerFactory(entityManagerFactory)
                .pageSize(100)
                .build();
    }

    @Bean
    public ItemProcessor<Vote, ExpiredVote> voteItemProcessor() {
        return (vote) -> ExpiredVote.builder()
                                    .voteId(vote.getId())
                                    .content(vote.getContent())
                                    .createdAt(vote.getCreatedAt())
                                    .expiredAt(vote.getExpiredAt())
                                    .imgUrl(vote.getImgUrl())
                                    .team(vote.getTeam())
                                    .build();
    }
}
