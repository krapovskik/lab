package mk.ukim.finki.wp.lab.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "CourseFilter")
public class CourseFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        String ID = (String) req.getSession().getAttribute("courseId");
        String path = req.getServletPath();
        String [] parts = path.split("/");
        String lastPart = "";
        if(parts.length > 0)
            lastPart = parts[parts.length - 1];

        if(ID == null && !path.equals("/courses")
                && !path.equals("/courses/edit/"+lastPart)
                && !path.equals("/courses/delete/"+lastPart)
                && !path.equals("/courses/add")
                ){
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.sendRedirect("/courses");
        }
        else {
            chain.doFilter(request,response);
        }

    }
}
