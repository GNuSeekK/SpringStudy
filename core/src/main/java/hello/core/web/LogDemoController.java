package hello.core.web;

import hello.core.common.MyLogger;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    // scope request의 경우 request가 들어오면 생성되고, request가 끝나면 소멸됨
    // 부트가 실행 될때는 request가 없으므로 provider를 사용하지 않으면 오류가 뜸
//    private final ObjectProvider<MyLogger> myLoggerProvider; // request scope bean
    private final MyLogger myLogger; // request scope bean

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) throws InterruptedException {
//        System.out.println("myLogger.getClass() = " + myLogger.getClass());


//        MyLogger myLogger = myLoggerProvider.getObject(); // myLogger가 처음 만들어지는 시점
        String requestURL = request.getRequestURL().toString();
        myLogger.setRequestURL(requestURL); // request scope bean에 url 저장
        myLogger.log("controller test");
        Thread.sleep(1000);
        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }
}
