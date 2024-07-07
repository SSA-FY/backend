package ssafy.lambda.quartz;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoteJobRunner implements ApplicationRunner {

    private final Scheduler voteBatchScheduler;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        JobDetail voteJobDetail = voteJobDetail(LocalDateTime.now());
        Trigger voteJobTrigger = voteJobTrigger();

        voteBatchScheduler.scheduleJob(voteJobDetail, voteJobTrigger);
    }

    /**
     * JobDetail 생성 메서드
     * VoteQuartzJob을 통해 JobDetail을 생성
     * JobDetail : Quartz 스케쥴러에 의해 실행될 작업
     * @param executeDate
     * @return
     */
    public JobDetail voteJobDetail(LocalDateTime executeDate) {
        return JobBuilder.newJob(VoteQuartzJob.class)
                         .withIdentity("VoteJob_" + executeDate)
                         .withDescription("배치 작업 수행을 위한 Quartz Job")
                         .build();
    }

    /**
     * JobTrigger 생성 메서드
     *
     * @return
     */
    public Trigger voteJobTrigger() {
        return TriggerBuilder.newTrigger()
                             .withDescription("배치 작업 수행을 위한 Trigger")
                             .startNow()
                             .withSchedule(CronScheduleBuilder.cronSchedule("*/10 * * * * ?"))
                             .build();
    }
}
