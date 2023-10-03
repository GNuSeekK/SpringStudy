package hello.servlet.web.servlet;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "memberListServlet", urlPatterns = "/servlet/members")
public class MemberListServlet extends HttpServlet {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        List<Member> members = memberRepository.findAll();

        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        StringBuilder sb = new StringBuilder();
        sb.append("<html>\n")
            .append("<head>\n")
            .append(" <meta charset=\"UTF-8\">\n")
            .append(" <title>Title</title>\n")
            .append("</head>\n")
            .append("<body>\n")
            .append("<a href=\"/index.html\">메인</a>\n")
            .append("<table>\n")
            .append(" <thead>\n")
            .append(" <th>id</th>\n")
            .append(" <th>username</th>\n")
            .append(" <th>age</th>\n")
            .append(" </thead>\n")
            .append(" <tbody>\n");
        for (Member member : members) {
            sb.append(" <tr>\n")
                .append(" <td>").append(member.getId()).append("</td>\n")
                .append(" <td>").append(member.getUsername()).append("</td>\n")
                .append(" <td>").append(member.getAge()).append("</td>\n")
                .append(" </tr>\n");
        }
        sb.append(" </tbody>\n")
            .append("</table>\n")
            .append("</body>\n")
            .append("</html>\n");
        resp.getWriter().write(sb.toString());
    }
}
