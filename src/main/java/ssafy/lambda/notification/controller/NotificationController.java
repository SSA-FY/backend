package ssafy.lambda.notification.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "token")
@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
@Slf4j
public class NotificationController {

}
