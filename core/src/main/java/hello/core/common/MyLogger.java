package hello.core.common;

import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
// proxy를 만드는 것이다.!
// 가짜 껍데기를 넣고, 실제 동작할 때 provider 처럼 동작한다
// CGLIB을 통해서 바이트 코드를 조작하는 가짜 프록시 객체 생성한다
public class MyLogger {
    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "]" + "[" + requestURL + "]" + "[" + message + "]");
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString(); // 랜덤한 uuid 생성 global하게 딱 하나 생성 (절대 안겹침)
        System.out.println("[" + uuid + "] request scope bean create:" + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close:" + this);
    }

}
