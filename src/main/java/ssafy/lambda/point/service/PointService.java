package ssafy.lambda.point.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.point.dto.ResponseGetPointDto;
import ssafy.lambda.point.entity.Point;
import ssafy.lambda.point.repository.PointRepository;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

    @Transactional
    public ResponseGetPointDto create(Member member, String description, Long amount) {
        Point point = Point.builder()
                           .member(member)
                           .description(description)
                           .amount(amount)
                           .build();

        pointRepository.save(point);

        return ResponseGetPointDto.builder()
                                  .amount(point.getAmount())
                                  .description(point.getDescription())
                                  .build();
    }

    @Transactional
    public Map<String, List<ResponseGetPointDto>> findAllByMemberId(UUID memberId) {
        return pointRepository.findAllByMemberId(memberId)
                              .stream()
                              .collect(Collectors.toMap(
                                  point -> formatter.format(Date.from(point.getCreatedAt())),
                                  point -> List.of(ResponseGetPointDto.builder()
                                                                      .description(
                                                                          point.getDescription())
                                                                      .amount(
                                                                          point.getAmount())
                                                                      .build()),
                                  (oldList, newList) -> Stream.concat(oldList.stream(),
                                                                  newList.stream())
                                                              .toList()
                              ));
    }
}