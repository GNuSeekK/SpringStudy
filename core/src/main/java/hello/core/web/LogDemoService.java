package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

// 서비스 계층은 웹 기술에 종속되지 않고 순수하게 유지하는 것이 유지보수 관점에서 좋다
// 그래서 서비스 계층에는 웹 기술을 전혀 사용하지 않는다. MyLogger는 request scope bean이다.
@Service
@RequiredArgsConstructor
public class LogDemoService {

//    private final ObjectProvider<MyLogger> myLoggerProvider; // request scope bean
    private final MyLogger myLogger; // request scope bean
    public void logic(String id) {
//        MyLogger myLogger = myLoggerProvider.getObject();
        myLogger.log("service id = " + id);
    }
}
