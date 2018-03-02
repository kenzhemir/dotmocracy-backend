import com.google.gson.Gson;
import model.AppuserEntity;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "TodoList", urlPatterns = {"/test"})
public class TestServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List l = HibernateUtil.readAll();
        // Create a Gson object
        Gson gson = new Gson();
        // String to send


        // Convert into JSON
        String json = gson.toJson(l);
        // Set response content type
        resp.setContentType("application/json");
        // Write the response
        resp.getWriter().write(json);
        resp.getWriter().flush();
    }

}
