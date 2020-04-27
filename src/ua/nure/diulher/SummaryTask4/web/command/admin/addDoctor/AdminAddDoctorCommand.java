package ua.nure.diulher.SummaryTask4.web.command.admin.addDoctor;

import org.apache.log4j.Logger;
import ua.nure.diulher.SummaryTask4.Path;
import ua.nure.diulher.SummaryTask4.exception.AppException;
import ua.nure.diulher.SummaryTask4.exception.DBException;
import ua.nure.diulher.SummaryTask4.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Add doctor command.
 *
 * @author V.Diulher
 */
public class AdminAddDoctorCommand extends Command {
    private static final long serialVersionUID = -2785666616686657267L;

    private static final Logger LOG = Logger.getLogger(AdminAddDoctorCommand.class);

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, DBException {
        LOG.debug("Command starts");

        LOG.debug("Command finished");
        return Path.PAGE_ADMIN_DOCTOR_FORM;
    }
}
