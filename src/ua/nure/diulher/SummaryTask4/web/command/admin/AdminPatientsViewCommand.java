package ua.nure.diulher.SummaryTask4.web.command.admin;

import org.apache.log4j.Logger;
import ua.nure.diulher.SummaryTask4.Path;
import ua.nure.diulher.SummaryTask4.db.DBManager;
import ua.nure.diulher.SummaryTask4.db.entity.User;
import ua.nure.diulher.SummaryTask4.exception.AppException;
import ua.nure.diulher.SummaryTask4.exception.DBException;
import ua.nure.diulher.SummaryTask4.web.command.Command;
import ua.nure.diulher.SummaryTask4.web.command.LogoutCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * View list of patients command.
 *
 * @author V.Diulher
 */
public class AdminPatientsViewCommand extends Command {

    private static final long serialVersionUID = -2785972222386657267L;

    private static final Logger LOG = Logger.getLogger(AdminPatientsViewCommand.class);

    private static class CompareByAlphabet implements Comparator<User>, Serializable {
        private static final long serialVersionUID = -1573481565177573283L;

        public int compare(User user1, User user2) {
            if (user1.getLastName().compareTo(user2.getLastName()) == 0) {
                return -user1.getFirstName().compareTo(user2.getFirstName());
            } else {
                return -user1.getLastName().compareTo(user2.getLastName());
            }
        }
    }

    private static class CompareByDate implements Comparator<User>, Serializable {
        private static final long serialVersionUID = -1573481565177573283L;

        public int compare(User user1, User user2) {
            return user1.getDate().compareTo(user2.getDate());
        }
    }

    private static final Comparator<User> compareByAlphabet = new AdminPatientsViewCommand.CompareByAlphabet();
    private static final Comparator<User> compareByDate = new AdminPatientsViewCommand.CompareByDate();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, DBException {
        LOG.debug("Commands starts");

        List<User> usersPatientsList = DBManager.getInstance().getUsersPatients();
        LOG.trace("Found in DB: usersPatientsList --> " + usersPatientsList);

        //sorting parameter
        String choice = request.getParameter("choice");
        if (choice == null) {
            choice = "alphabet";
        }
        if ("alphabet".equals(choice)) {
            Collections.sort(usersPatientsList, compareByAlphabet);
        } else {
            Collections.sort(usersPatientsList, compareByDate);
        }
        LOG.trace("CHOICE: -->  " + choice);

        request.setAttribute("usersPatientsList", usersPatientsList);
        LOG.trace("Set the request attribute: usersPatientsList --> " + usersPatientsList);

        LOG.debug("Commands finished");
        return Path.PAGE_ADMIN_PATIENTS;
    }
}
