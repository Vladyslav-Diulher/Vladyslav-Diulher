package ua.nure.diulher.SummaryTask4.web.command.doctor;

import org.apache.log4j.Logger;
import ua.nure.diulher.SummaryTask4.Path;
import ua.nure.diulher.SummaryTask4.db.DBManager;
import ua.nure.diulher.SummaryTask4.db.entity.MedicalCard;
import ua.nure.diulher.SummaryTask4.exception.AppException;
import ua.nure.diulher.SummaryTask4.exception.DBException;
import ua.nure.diulher.SummaryTask4.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Doctor find medcard command.
 *
 * @author V.Diulher
 */
public class GetMedCardForUpdate extends Command {

    private static final long serialVersionUID = 1863978284689533313L;

    private static final Logger LOG = Logger.getLogger(GetMedCardForUpdate.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, DBException {
        LOG.debug("Commands starts");

        String patient = request.getParameter("patient");
        LOG.trace("Obtained patient: ---> " + patient);

        MedicalCard medicalCard = DBManager.getInstance().getMedicalCard(new Integer(patient));

        HttpSession session = request.getSession();
        session.setAttribute("medicalCard", medicalCard);
        LOG.trace("Set the session attribute: medicalCard --> " + medicalCard);

        //preparing medcard form
        long patientID = medicalCard.getPatientID();
        long doctorID = medicalCard.getDoctorID();
        request.setAttribute("doctor", DBManager.getInstance().findUser(doctorID));
        request.setAttribute("patient", DBManager.getInstance().findUser(patientID));
        LOG.trace("Set the request attribute: medicalCard --> " + medicalCard);

        LOG.debug("Commands finished");
        return Path.PAGE_DOCTOR_MEDCARD_UPDATE;
    }
}
