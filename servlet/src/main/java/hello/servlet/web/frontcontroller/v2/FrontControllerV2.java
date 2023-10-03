package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.ControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*") // *는 모든 하위 요청을 의미한다.
public class FrontControllerV2 extends HttpServlet {

    private Map<String, ControllerV2> controllerMap = new HashMap<>();

    public FrontControllerV2() {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String requestURI = request.getRequestURI();// 요청 URI를 조회한다.

        ControllerV2 controller = controllerMap.get(requestURI); // 요청 URI에 해당하는 컨트롤러를 찾는다.
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 컨트롤러가 없으면 404 상태 코드를 반환한다.
            return;
        }

        MyView view = controller.process(request, response);
        view.render(request, response);

    }
}
