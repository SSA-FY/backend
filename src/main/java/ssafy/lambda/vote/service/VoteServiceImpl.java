package ssafy.lambda.vote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssafy.lambda.vote.dto.RequestMemberDto;
import ssafy.lambda.vote.dto.RequestVoteDto;
import ssafy.lambda.vote.entity.Vote;
import ssafy.lambda.vote.entity.VoteInfo;
import ssafy.lambda.vote.repository.VoteInfoRepository;
import ssafy.lambda.vote.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService{

    private final VoteRepository voteRepository;
    private final VoteInfoRepository voteInfoRepository;

    @Override
    public void createVote(RequestVoteDto requestVoteDto) {
        Vote vote = Vote.builder()
                .content(requestVoteDto.getContent())
                .imgUrl(requestVoteDto.getBackgroundUrl())
                .build();
        voteRepository.save(vote);

    }

    @Override
    public void doVote(Long voteId, Long teamId, Long memberId, Long choosedMemberId) throws IllegalArgumentException{

        // 투표가 유효한가
        Vote finedVote = voteRepository.findById(voteId).orElseThrow(
                ()-> new IllegalArgumentException("vote doesn't exist")
        );
        if(finedVote.isProceeding() == false){
            throw new IllegalArgumentException("vote is over");
        }

        // 이미 투표했는가
        if(voteInfoRepository.existsByVoteIdAndMemberId(voteId, memberId)){
            throw new IllegalArgumentException("The member already voted");
        }

        // 투표하기
        VoteInfo voteInfo
                = VoteInfo.builder()
                    .choosedMemberId(choosedMemberId)
                    .memberId(memberId)
                    .vote(finedVote)
                .build();

        voteInfoRepository.save(voteInfo);

        return;
    }
}
