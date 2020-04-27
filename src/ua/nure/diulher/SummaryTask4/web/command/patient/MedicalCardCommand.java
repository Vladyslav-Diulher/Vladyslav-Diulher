package ua.nure.diulher.SummaryTask4.web.command.patient;

import org.apache.log4j.Logger;
import ua.nure.diulher.SummaryTask4.Path;
import ua.nure.diulher.SummaryTask4.db.DBManager;
import ua.nure.diulher.SummaryTask4.db.entity.MedicalCard;
import ua.nure.diulher.SummaryTask4.db.entity.User;
import ua.nure.diulher.SummaryTask4.exception.AppException;
import ua.nure.diulher.SummaryTask4.exception.DBException;
import ua.nure.diulher.SummaryTask4.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Update medCard command.
 *
 * @author V.Diulher
 */
public class MedicalCardCommand extends Command {

    private static final long serialVersionUID = 1863977774689533313L;

    private static final Logger LOG = Logger.getLogger(MedicalCardCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, DBException {
        LOG.debug("Commands starts");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        long patId = user.getId();
        MedicalCard medicalCard = DBManager.getInstance().getMedicalCard(patId);

        long patientID = medicalCard.getPatientID();
        long doctorID = medicalCard.getDoctorID();

        request.setAttribute("doctor", DBManager.getInstance().findUser(doctorID));
        request.setAttribute("patient", DBManager.getInstance().findUser(patientID));
        request.setAttribute("medicalCard", medicalCard);
        session.setAttribute("doctor", DBManager.getInstance().findUser(doctorID));
        session.setAttribute("patient", DBManager.getInstance().findUser(patientID));
        session.setAttribute("medicalCard", medicalCard);
        LOG.trace("Set the request attribute: medicalCard --> " + medicalCard);

        LOG.debug("Commands finished");
        return Path.PAGE_PATIENT_MEDCARD;

    }

}
