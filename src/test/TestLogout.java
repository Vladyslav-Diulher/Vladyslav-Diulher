package test;

import org.junit.Test;
import org.mockito.Mockito;
import ua.nure.diulher.SummaryTask4.web.command.LoginCommand;
import ua.nure.diulher.SummaryTask4.web.command.LogoutCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestLogout extends Mockito {

    @Test
    public void testServlet() throws Exception {

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getSession(false)).thenReturn(mock(HttpSession.class));

        new LogoutCommand().execute(request, response);
        HttpSession expected=null;
        HttpSession actual=request.getSession();
        assertEquals(expected, actual);
    }
}
