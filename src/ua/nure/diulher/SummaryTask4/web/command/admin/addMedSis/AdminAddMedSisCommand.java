package ua.nure.diulher.SummaryTask4.web.command.admin.addMedSis;

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
 * Insert a medSis command.
 *
 * @author V.Diulher
 */
public class AdminAddMedSisCommand extends Command {
    private static final long serialVersionUID = -2785975616636657267L;

    private static final Logger LOG = Logger.getLogger(AdminAddMedSisCommand.class);

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, DBException {
        LOG.debug("Command starts");

        LOG.debug("Command finished");
        return Path.PAGE_ADMIN_MEDSIS_FORM;
    }
}
