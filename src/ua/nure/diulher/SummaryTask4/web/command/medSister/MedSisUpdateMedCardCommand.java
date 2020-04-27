package ua.nure.diulher.SummaryTask4.web.command.medSister;

import org.apache.log4j.Logger;
import ua.nure.diulher.SummaryTask4.Path;
import ua.nure.diulher.SummaryTask4.db.DBManager;
import ua.nure.diulher.SummaryTask4.db.entity.MedicalCard;
import ua.nure.diulher.SummaryTask4.db.entity.User;
import ua.nure.diulher.SummaryTask4.exception.AppException;
import ua.nure.diulher.SummaryTask4.exception.DBException;
import ua.nure.diulher.SummaryTask4.web.command.Command;
import ua.nure.diulher.SummaryTask4.web.command.doctor.UpdateMedCardCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Update medCard command.
 *
 * @author V.Diulher
 */
public class MedSisUpdateMedCardCommand extends Command {

    private static final long serialVersionUID = 1823440054689533313L;

    private static final Logger LOG = Logger.getLogger(MedSisUpdateMedCardCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, DBException {
        LOG.debug("Commands starts");

        HttpSession session = request.getSession();
        MedicalCard medicalCardRequest = new MedicalCard();
        MedicalCard medicalCardFromSession = (MedicalCard) session.getAttribute("medicalCard");

        String procedure = request.getParameter("procedure");
        LOG.trace("Request parameter: procedure --> " + procedure);

        String medicament = request.getParameter("medicament");
        LOG.trace("Request parameter: medicament --> " + medicament);

        medicalCardRequest.setProcedure(procedure);
        medicalCardRequest.setMedicaments(medicament);

        //updating medcard
        DBManager.getInstance().medSisUpdateMedCard(medicalCardFromSession.getId(), medicalCardRequest);

        //preparing next page
        List<User> patients = DBManager.getInstance().getUsersPatients();
        request.setAttribute("patients", patients);
        LOG.trace("Set the request attribute: patients --> " + patients);

        LOG.debug("Commands finished");
        return Path.PAGE_MEDSIS_CHOOSE_PATIENT;
    }
}
