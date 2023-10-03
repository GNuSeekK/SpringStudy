package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
//@Controller
//@ResponseBody
@RestController
public class ResponseBodyController {

    @GetMapping("/response-body-string-v1")
    public ResponseEntity responseBodyV1() throws Exception {
//        response.getWriter().write("ok");
        return new ResponseEntity("ok", HttpStatus.OK);
    }
    @GetMapping("/response-body-string-v2")
    public void responseBodyV2(HttpServletResponse response) throws Exception {
        response.getWriter().write("ok");
    }
    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() throws Exception {
        return "ok";
    }

    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1() throws Exception {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);
        return new ResponseEntity<>(helloData, HttpStatus.OK); // 동적으로 HTTP 응답 코드를 변경할 수 있다.
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2() throws Exception {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);
        return helloData;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/response-body-json-v3")
    public HelloData responseBodyJsonV3(@RequestBody HelloData data) throws Exception {
        return data;
    }


}
