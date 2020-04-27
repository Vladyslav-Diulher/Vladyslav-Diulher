package test;

import org.junit.Before;
import org.mockito.Mockito;

import javax.jws.soap.SOAPBinding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.junit.Test;
import ua.nure.diulher.SummaryTask4.db.DBManager;
import ua.nure.diulher.SummaryTask4.db.entity.User;
import ua.nure.diulher.SummaryTask4.web.Controller;
import ua.nure.diulher.SummaryTask4.web.command.LoginCommand;

import static org.junit.Assert.assertTrue;

public class TestLogin extends Mockito {

    @Before
    public void init() throws NamingException {
        InitialContext initContext = new InitialContext();
        Context envContext = (Context) initContext.lookup("java:/comp/env");
        // ST4DB - the name of data source
        DataSource ds = (DataSource) envContext.lookup("jdbc/project0");
    }


    @Test
    public void testServlet() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("command")).thenReturn("login");


        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new LoginCommand().execute(request, response);

        verify(request, atLeast(1)).getParameter("login"); // only if you want to verify username was called...
        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().contains("My expected string"));
    }
}


//    when(DBManager.getInstance()).thenReturn(mock(DBManager.class));
//        DBManager manager=DBManager.getInstance();
//        when(manager.findUserByLogin("admin")).thenReturn(mock(User.class));