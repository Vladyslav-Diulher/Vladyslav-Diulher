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
 * View list of doctors command.
 *
 * @author V.Diulher
 */
public class AdminDoctorsViewCommand extends Command {

    private static final long serialVersionUID = -2785976616383357267L;

    private static final Logger LOG = Logger.getLogger(AdminDoctorsViewCommand.class);

    private static class CompareByAlphabet implements Comparator<User>, Serializable {
        private static final long serialVersionUID = -1573481565177333283L;

        public int compare(User user1, User user2) {
            if (user1.getLastName().compareTo(user2.getLastName()) == 0) {
                return user1.getFirstName().compareTo(user2.getFirstName());
            } else {
                return user1.getLastName().compareTo(user2.getLastName());
            }
        }
    }

    private static class CompareByCategory implements Comparator<User>, Serializable {
        private static final long serialVersionUID = -1573481335177573283L;

        public int compare(User user1, User user2) {
            return user1.getPosition().compareTo(user2.getPosition());
        }
    }

    private static class CompareByQuatityofPatients implements Comparator<User>, Serializable {
        private static final long serialVersionUID = -1573481563377573283L;

        public int compare(User user1, User user2) {
            int u1 = 0;
            int u2 = 0;
            try {
                u1 = DBManager.getInstance().countPatients(user1.getId());
                u2 = DBManager.getInstance().countPatients(user2.getId());
            } catch (DBException e) {
                e.printStackTrace();
            }
            return u1 - u2;
        }
    }

    private static Comparator<User> compareByAlphabet = new AdminDoctorsViewCommand.CompareByAlphabet();
    private static Comparator<User> compareByCategory = new AdminDoctorsViewCommand.CompareByCategory();
    private static Comparator<User> compareByQuantity = new AdminDoctorsViewCommand.CompareByQuatityofPatients();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, DBException {
        LOG.debug("Commands starts");

        List<User> usersDoctorsList = DBManager.getInstance().getUsersDoctors();
        LOG.trace("Found in DB: usersDocrtorsList --> " + usersDoctorsList);
        String choice = request.getParameter("choice");

        if (choice == null) {
            choice = "alphabet";
        }

        if ("alphabet".equals(choice)) {
            Collections.sort(usersDoctorsList, compareByAlphabet);
        } else if ("position".equals(choice)) {
            Collections.sort(usersDoctorsList, compareByCategory);
        } else {
            Collections.sort(usersDoctorsList, compareByQuantity);
        }

        request.setAttribute("usersDoctorsList", usersDoctorsList);
        LOG.trace("Set the request attribute: usersDoctorsList --> " + usersDoctorsList);

        LOG.debug("Commands finished");
        return Path.PAGE_ADMIN_DOCTORS;


    }
}
