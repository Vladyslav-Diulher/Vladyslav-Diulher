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
import java.util.regex.Pattern;


/**
 * Create patient command.
 *
 * @author V.Diulher
 */
public class AdminObtainPatentCommand extends Command {

    private static final long serialVersionUID = -2785973316386657267L;

    private static final Logger LOG = Logger.getLogger(AdminObtainPatentCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException, DBException {
        LOG.debug("Commands starts");

        //Obtaining parameters
        String login = request.getParameter("login");
        LOG.trace("Request parameter: login --> " + login);

        String password = request.getParameter("password");
        LOG.trace("Request parameter: password --> " + password);

        String firstName = request.getParameter("firstName");
        LOG.trace("Request parameter: First name --> " + firstName);

        String lastName = request.getParameter("lastName");
        LOG.trace("Request parameter: Last name --> " + lastName);

        String date = request.getParameter("birthDate");
        LOG.trace("Request parameter: Date --> " + date);

        Pattern pattern = Pattern.compile(
                "[a-z\\d\\_]+", Pattern.CASE_INSENSITIVE);

        if (!pattern.matcher(login).matches() || !pattern.matcher(password).matches()){
            throw new AppException("Login and password should contain only latin characters");
        }

        if (login == null || login.isEmpty()
                || password == null || password.isEmpty()
                || firstName == null || firstName.isEmpty()
                || lastName == null || lastName.isEmpty()
                || date == null || date.isEmpty()) {
            throw new AppException("At least one field is empty");
        }

        User patient = new User();
        patient.setRoleId(1);
        patient.setLogin(login);
        patient.setPassword(password);
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setDate(date);

        //insert patient to users table
        DBManager.getInstance().insertPatient(patient);

        //get id from users table
        User insertedPatient = DBManager.getInstance().findUserByLogin(login);
        long patientID = insertedPatient.getId();
        LOG.debug("patient ID: ---> " + patientID);

        //fill doctor_to patient table
        long doctorID = new Long(request.getParameter("doctor"));
        LOG.debug("doctor ID: ---> " + doctorID);

        DBManager.getInstance().boundPatientAndDoctor(doctorID, patientID);
        long dtpid = DBManager.getInstance().findDTPID(patientID);
        LOG.debug("DTP ID: ---> " + dtpid);

        //creating MedCard
        DBManager.getInstance().insertMedCard(dtpid);

        LOG.debug("Commands finished");
        return Path.PAGE_START;
    }
}
