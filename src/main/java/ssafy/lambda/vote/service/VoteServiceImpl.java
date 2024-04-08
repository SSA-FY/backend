package ssafy.lambda.vote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssafy.lambda.vote.dto.RequestMemberDto;
import ssafy.lambda.vote.dto.RequestVoteDto;
import ssafy.lambda.vote.entity.Vote;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService{
    @Override
    public void createVote(RequestVoteDto requestVoteDto, RequestMemberDto requestMemberDto) {
        Vote vote = Vote.builder()
                .content(requestVoteDto.getContent())
                .expiredAt(LocalDateTime.now())
                .isProceeding(true)
                .imgUrl(requestVoteDto.getBackgroundUrl())
                .build();
    }
}
