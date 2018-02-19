import org.apache.commons.text.WordUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import com.google.gson.Gson;

@WebServlet(name = "TodoList", urlPatterns = {"/test"})
public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Create a Gson object
        Gson gson = new Gson();
        // String to send
        String json = WordUtils.capitalize("great! everything works fine!");
        // Convert into JSON
        json = gson.toJson(json);
        // Set response content type
        resp.setContentType("application/json");
        // Write the response
        resp.getWriter().write(json);
        resp.getWriter().flush();
    }

}
