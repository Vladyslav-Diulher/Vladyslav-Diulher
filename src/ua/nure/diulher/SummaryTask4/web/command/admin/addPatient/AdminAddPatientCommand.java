package ua.nure.diulher.SummaryTask4.web.command.admin.addPatient;

import org.apache.log4j.Logger;
import ua.nure.diulher.SummaryTask4.Path;
import ua.nure.diulher.SummaryTask4.db.DBManager;
import ua.nure.diulher.SummaryTask4.db.entity.User;
import ua.nure.diulher.SummaryTask4.exception.AppException;
import ua.nure.diulher.SummaryTask4.exception.DBException;
import ua.nure.diulher.SummaryTask4.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Add a patient command.
 *
 * @author V.Diulher
 */
public class AdminAddPatientCommand extends Command {

    private static final long serialVersionUID = -2785976616111157267L;

    private static final Logger LOG = Logger.getLogger(AdminAddPatientCommand.class);

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, DBException {
        LOG.debug("Command starts");

        //Prepare pool of doctors for patient
        List<User> doctors=DBManager.getInstance().getUsersDoctors();
        request.setAttribute("doctors",doctors);

        LOG.debug("Command finished");
        return Path.PAGE_ADMIN_PATIENT_FORM;
    }
}
