package hello.exception.resolver;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
        Exception ex) {
        try {
            if (ex instanceof IllegalArgumentException) {
                log.info("IllegalArgumentException resolver to 400");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage()); // HTTP 상태 코드를 400으로 변경
                return new ModelAndView(); // 빈 ModelAndView를 반환하면 정상 흐름으로 리턴, 정상 응답으로 돌아감 sendError발생해서 오류페이지 탐색
            }
        } catch (IOException e) {
            log.error("resolver ex", e);
        }

        return null;
    }
}
