package hello.servlet.basic.request;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 1. 파라미터 전송 기능
// http://localhost:8080/request-param?username=hello&age=20
@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        System.out.println("RequestParamServlet.service");
        System.out.println("[전체 파라미터 조회] - start");
        req.getParameterNames().asIterator()
            .forEachRemaining(
                paramName -> System.out.println(paramName + "=" + req.getParameter(paramName)));

        System.out.println("[전체 파라미터 조회] - end");
        System.out.println();

        System.out.println("[단일 파라미터 조회]");
        String username = req.getParameter("username");
        String age = req.getParameter("age");
        System.out.println("username = " + username);
        System.out.println("age = " + age);

        System.out.println("[이름이 같은 복수 파라미터 조회]");
        // http://localhost:8080/request-param?username=hello&age=20&username=hello2
        // 이런식으로 파라미터를 보내면 getParameterValues를 사용하면 됨
        String[] usernames = req.getParameterValues("username");
        for (String name : usernames) {
            System.out.println("username = " + name);
        }

        resp.getWriter().write("ok");

        // request.getParameter()는 GET URL 쿼리 파라미터 형식도 지원하고, POST HTML Form 형식도 둘 다 지원함
    }
}
