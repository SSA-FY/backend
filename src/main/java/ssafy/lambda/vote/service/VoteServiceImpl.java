package ssafy.lambda.vote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssafy.lambda.vote.dto.RequestVoteDto;
import ssafy.lambda.vote.entity.Vote;
import ssafy.lambda.vote.entity.VoteInfo;
import ssafy.lambda.vote.repository.VoteInfoRepository;
import ssafy.lambda.vote.repository.VoteRepository;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService{

    private final VoteRepository voteRepository;
    private final VoteInfoRepository voteInfoRepository;

    @Override
    public void createVote(Long memberId, Long teamId, RequestVoteDto requestVoteDto) {

        // Entity 수정 후, 멤버십을 연결한 vote 객체 생성

        Vote vote = Vote.builder()
                .content(requestVoteDto.getContent())
                .imgUrl(requestVoteDto.getBackgroundUrl())
                .build();
        voteRepository.save(vote);

    }

    @Override
    public void doVote(Long voteId, Long teamId, Long memberId, Long choosedMemberId) throws IllegalArgumentException{

        // 투표가 유효한가
        Vote foundVote = voteRepository.findById(voteId).orElseThrow(
                ()-> new IllegalArgumentException("vote doesn't exist")
        );
        if(foundVote.isProceeding() == false){
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
                    .vote(foundVote)
                .build();

        voteInfoRepository.save(voteInfo);

        return;
    }

    @Override
    public void review(Long memberId, Long voteId, String review){
        // 투표했는가
        VoteInfo foundVoteInfo = voteInfoRepository.findByVoteIdAndMemberId(voteId, memberId).orElseThrow(
            ()-> new IllegalArgumentException("user hasn't voted yet")
        );
        // 이미 한줄평을 남겼는가
        if(foundVoteInfo.getOpinion() != null){
            throw new IllegalArgumentException("user already left a review");
        }

        foundVoteInfo.setOpinion(review);
        voteInfoRepository.save(foundVoteInfo);
    }

}
