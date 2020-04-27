package ua.nure.diulher.SummaryTask4.web.command.medSister;

import org.apache.log4j.Logger;
import ua.nure.diulher.SummaryTask4.Path;
import ua.nure.diulher.SummaryTask4.db.DBManager;
import ua.nure.diulher.SummaryTask4.db.entity.MedicalCard;
import ua.nure.diulher.SummaryTask4.exception.AppException;
import ua.nure.diulher.SummaryTask4.exception.DBException;
import ua.nure.diulher.SummaryTask4.web.command.Command;
import ua.nure.diulher.SummaryTask4.web.command.doctor.GetMedCardForUpdate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Preparing medCard command.
 *
 * @author V.Diulher
 */
public class MedSisGetMedCardForUpdate extends Command {

    private static final long serialVersionUID = 1863978284229933313L;

    private static final Logger LOG = Logger.getLogger(MedSisGetMedCardForUpdate.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, DBException {
        LOG.debug("Commands starts");

        //finding medcard by patient ID
        String patient = request.getParameter("patient");
        MedicalCard medicalCard = DBManager.getInstance().getMedicalCard(new Integer(patient));

        HttpSession session = request.getSession();
        session.setAttribute("medicalCard", medicalCard);
        LOG.trace("Set the session attribute: medicalCard --> " + medicalCard);

        long patientID = medicalCard.getPatientID();
        long doctorID = medicalCard.getDoctorID();
        request.setAttribute("doctor", DBManager.getInstance().findUser(doctorID));
        request.setAttribute("patient", DBManager.getInstance().findUser(patientID));
        request.setAttribute("medicalCard", medicalCard);
        LOG.trace("Set the request attribute: medicalCard --> " + medicalCard);

        LOG.debug("Commands finished");
        return Path.PAGE_MEDSIS_MEDCARD_UPDATE;
    }
}
