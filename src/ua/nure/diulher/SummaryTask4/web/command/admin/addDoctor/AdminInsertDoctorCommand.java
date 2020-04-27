package ua.nure.diulher.SummaryTask4.web.command.admin.addDoctor;

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
 * Insert a doctor command.
 *
 * @author V.Diulher
 */
public class AdminInsertDoctorCommand extends Command {
    private static final long serialVersionUID = -2785365616686657267L;

    private static final Logger LOG = Logger.getLogger(AdminInsertDoctorCommand.class);

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, DBException {
        LOG.debug("Command starts");

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

        User doctor = new User();
        doctor.setLogin(login);
        doctor.setPassword(password);
        doctor.setFirstName(firstName);
        doctor.setLastName(lastName);
        doctor.setDate(date);
        doctor.setRoleId(new Integer(request.getParameter("role")));

        //Filling the user table
        DBManager.getInstance().insertDoctor(doctor);

        //Filling the position table
        long docID = DBManager.getInstance().findUserByLogin(doctor.getLogin()).getId();
        DBManager.getInstance().insertDoctorPosition(docID, doctor.getRoleId());

        LOG.debug("Command finished");
        return Path.PAGE_START;
    }
}
