package ssafy.lambda.batch.step.writer;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import ssafy.lambda.board.entity.ExpiredVoteInfo;
import ssafy.lambda.board.repository.ExpiredVoteInfoRepository;
import ssafy.lambda.vote.repository.VoteInfoRepository;

@Component
@RequiredArgsConstructor
public class ExpiredVoteInfoItemWriter implements ItemWriter<ExpiredVoteInfo> {

    private final ExpiredVoteInfoRepository expiredVoteInfoRepository;
    private final VoteInfoRepository voteInfoRepository;

    @Override
    public void write(Chunk<? extends ExpiredVoteInfo> chunk) throws Exception {
        List<Long> voteIds = new ArrayList<>();
        for (ExpiredVoteInfo expiredVoteInfo : chunk) {
            voteIds.add(expiredVoteInfo.getVoteId());
            expiredVoteInfoRepository.save(expiredVoteInfo);
        }
        voteInfoRepository.deleteVoteInfoByVoteId(voteIds);
    }
}
