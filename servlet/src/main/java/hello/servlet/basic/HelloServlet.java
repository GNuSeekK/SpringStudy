package hello.servlet.basic;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // request랑 response는 인터페이스, WAS 서버에서 만들어서 넘겨줌
        System.out.println("HelloServlet.service");
        System.out.println("request = " + request);
        System.out.println("response = " + response);

        String username = request.getParameter("username");// 이런식으로 사용 가능
        System.out.println("username = " + username);

        response.setContentType("text/plain"); // 헤더에 들어감
        response.setCharacterEncoding("utf-8"); // 헤더에 들어감
        response.getWriter().write("hello " + username); // 이런식으로 사용 가능
    }
}
