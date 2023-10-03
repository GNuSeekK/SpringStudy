package hello.servlet.basic.response;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "responseHtmlServlet", urlPatterns = "/response-html")
public class ResponseHtmlServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        // Content-Type: text/html;charset=utf-8
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        // HTML은 HTTP 응답 메시지의 body에 들어감
        // HTTP 응답으로 HTML을 반환할 때는 content-type을 text/html로 지정해야 함
        // 브라우저는 content-type을 보고 HTML임을 인지하고 HTML을 렌더링함

        PrintWriter writer = resp.getWriter();
        writer.println("<html>");
        writer.println("<body>");
        writer.println("<div>안녕?</div>");
        writer.println("</body>");
        writer.println("</html>");

    }
}
