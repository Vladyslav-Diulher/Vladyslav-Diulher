package ua.nure.diulher.SummaryTask4.web.command.medSister;

import org.apache.log4j.Logger;
import ua.nure.diulher.SummaryTask4.Path;
import ua.nure.diulher.SummaryTask4.db.DBManager;
import ua.nure.diulher.SummaryTask4.db.entity.User;
import ua.nure.diulher.SummaryTask4.exception.AppException;
import ua.nure.diulher.SummaryTask4.exception.DBException;
import ua.nure.diulher.SummaryTask4.web.command.Command;
import ua.nure.diulher.SummaryTask4.web.command.doctor.DoctorChoosePatientCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * MedSis choose patient command.
 *
 * @author V.Diulher
 */
public class MedSisChoosePatientCommand extends Command {

    private static final long serialVersionUID = 1863922266689533313L;

    private static final Logger LOG = Logger.getLogger(MedSisChoosePatientCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, DBException {
        LOG.debug("Commands starts");

        //getting list of ALL patients
        List<User> patients = DBManager.getInstance().getUsersPatients();
        request.setAttribute("patients", patients);
        LOG.trace("Set the request attribute: patients --> " + patients);

        LOG.debug("Commands finished");
        return Path.PAGE_MEDSIS_CHOOSE_PATIENT;
    }
}
