package ua.nure.diulher.SummaryTask4.web.command.doctor;

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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Choose patient for doctor command.
 *
 * @author V.Diulher
 */
public class DoctorChoosePatientCommand extends Command {

    private static final long serialVersionUID = 1863927724689533313L;

    private static final Logger LOG = Logger.getLogger(DoctorChoosePatientCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, DBException {
        LOG.debug("Commands starts");

        //getting doctors patients
        HttpSession session = request.getSession();
        User user= (User) session.getAttribute("user");
        long docId= user.getId();
        List<User> patients = DBManager.getInstance().getDoctorPatients(docId);

        request.setAttribute("patients", patients);
        LOG.trace("Set the request attribute: patients --> " + patients);

        LOG.debug("Commands finished");
        return Path.PAGE_DOCTOR_CHOOSE_PATIENT;
    }
}
