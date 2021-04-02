/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import beans.AsynCallerBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Future;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author viter
 */
@WebServlet(name = "AsyncServlet", urlPatterns = {"/AsyncServlet"})
public class AsyncServlet extends HttpServlet {

    /**
     * Default constructor.
     */
    public AsyncServlet() {
        // TODO Auto-generated constructor stub
    }

    @EJB
    private AsynCallerBean async;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String step = request.getParameter("step");

        String msg="", op="";

        String ok;

        if (step.equals("0")) {
            msg = "<p>Clique para iniciar a demonstração:</p>";
            op = "<p>[<a href=\"./AsyncServlet?step=1\">INICIAR</a>]</p>";
        } else if (step.equals("1")) {
            async.startOperation(step);
            msg = "<p>Cálculo assíncrono iniciado</p>";
            op = "<p>[<a href=\"./AsyncServlet?step=2\">CHECAR</a>]</p>";
        } else if (step.equals("2")) {
            ok = async.checkResult();
            if (!ok.equals("ok")) {
                msg = "<p>Cálculo em andamento... " + ok + "</p>";
                op = "<p>[<a href=\"./AsyncServlet?step=2\">CHECAR</a>] [<a href=\"./AsyncServlet?step=3\">INTERROMPER</a>]</p>";
            }
            else{
                msg = "<p>Cálculo encerrado</p>";
                op = "<p>[<a href=\"./AsyncServlet?step=4\">VER RESULTADO</a>]</p>";
            }
        } else if (step.equals("3")) {
            async.cancelOperation();
            msg = "<p>Operação cancelada!</p>";
            op = "<p>[<a href=\"./AsyncServlet?step=1\">REINICIAR</a>]</p>";
        } else if (step.equals("4")) {
            String result = async.getResult();
            msg = "<p>Resultado obtido: " + result + "</p>";
            op = "<p>[<a href=\"./AsyncServlet?step=1\">REINICIAR</a>]</p>";
        }

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Async Demo EE</title>");
        out.println("<meta charset=\"ISO-8859-1\">");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Exemplo de uso métodos assíncronos</h1>");
        out.println("<h2>Passo " + step + "</h2>");
        out.println(msg);
        out.println("<p></p>");
        out.println(op);
        out.println("</body>");
        out.println("</html>");
    }

}
