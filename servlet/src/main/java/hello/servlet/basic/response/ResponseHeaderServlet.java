package hello.servlet.basic.response;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        // [status-line]
        resp.setStatus(HttpServletResponse.SC_OK);

        // [response-headers]
//        resp.setHeader("Content-Type", "text/plain;charset=utf-8");
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // 캐시 무효화
        resp.setHeader("Pragma", "no-cache"); // 캐시 무효화
        resp.setHeader("my-header", "hello"); // 내가 만든 헤더

        // [Header 편의 메서드]
//        content(resp);
//        cookie(resp);
        redirect(resp);

        // [message body]
        PrintWriter writer = resp.getWriter();
        writer.println("안녕하세요.");

    }

    private void redirect(HttpServletResponse resp) throws IOException {
        // Status Code 302
        // Location: /basic/hello-form.html
        // resp.setStatus(HttpServletResponse.SC_FOUND); // 302
        // resp.setHeader("Location", "/basic/hello-form.html");

        // 위의 두 줄을 아래 한 줄로 표현 가능 302 + Location 헤더
        resp.sendRedirect("/basic/hello-form.html");
    }

    private void cookie(HttpServletResponse resp) {
        // Set-Cookie: myCookie=good; Max-Age=600;
        // resp.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600); // 600초
        resp.addCookie(cookie);
    }

    private void content(HttpServletResponse resp) {
        // Content-Type: text/plain;charset=utf-8
        // Content-Length: 2
        // resp.setHeader("Content-Type", "text/plain;charset=utf-8");
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("utf-8");
        // resp.setContentLength(2); // (생략시 자동 생성)
    }
}
