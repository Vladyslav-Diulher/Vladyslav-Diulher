package ua.nure.diulher.SummaryTask4.web.command.patient;

import org.apache.log4j.Logger;
import ua.nure.diulher.SummaryTask4.Path;
import ua.nure.diulher.SummaryTask4.db.DBManager;
import ua.nure.diulher.SummaryTask4.db.entity.User;
import ua.nure.diulher.SummaryTask4.exception.DBException;
import ua.nure.diulher.SummaryTask4.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Patient discharge command.
 *
 * @author V.Diulher
 */
public class PatientFinalVipiskaCommand extends Command {

    private static final long serialVersionUID = -2785976616633357267L;

    private static final Logger LOG = Logger.getLogger(PatientFinalVipiskaCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, DBException {
        LOG.debug("Command starts");

        HttpSession session = request.getSession();
        User patient = (User) session.getAttribute("user");
        long patientId = patient.getId();
        DBManager dbManager = DBManager.getInstance();

        //find doctor of a patient
        long DTPId = dbManager.findDTPID(patientId);

        //deleting medcard
        dbManager.deleteMedcard(DTPId);
        //deleting doctor bound
        dbManager.deleteDTP(patientId);
        //deleting user
        dbManager.deleteUser(patientId);

        session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        LOG.debug("Command finished");
        return Path.PAGE_LOGIN;
    }

}
