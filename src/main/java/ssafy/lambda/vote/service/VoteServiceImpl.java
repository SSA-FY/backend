package ssafy.lambda.vote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssafy.lambda.vote.dto.RequestMemberDto;
import ssafy.lambda.vote.dto.RequestVoteDto;
import ssafy.lambda.vote.entity.Vote;
import ssafy.lambda.vote.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService{

    private final VoteRepository voteRepository;
    @Override
    public void createVote(RequestVoteDto requestVoteDto) {
        Vote vote = Vote.builder()
                .content(requestVoteDto.getContent())
                .imgUrl(requestVoteDto.getBackgroundUrl())
                .build();
        voteRepository.save(vote);

    }
}
