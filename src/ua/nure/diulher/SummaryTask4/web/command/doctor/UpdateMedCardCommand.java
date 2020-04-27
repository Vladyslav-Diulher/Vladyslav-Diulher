package ua.nure.diulher.SummaryTask4.web.command.doctor;

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
import java.util.List;

/**
 * Update medcard command.
 *
 * @author V.Diulher
 */
public class UpdateMedCardCommand extends Command {

    private static final long serialVersionUID = 1823978258888533313L;

    private static final Logger LOG = Logger.getLogger(UpdateMedCardCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, DBException {
        LOG.debug("Commands starts");

        HttpSession session = request.getSession();
        MedicalCard medicalCardRequest = new MedicalCard();
        MedicalCard medicalCardFromSession = (MedicalCard) session.getAttribute("medicalCard");

        String operation = request.getParameter("operation");
        LOG.trace("Request parameter: operation --> " + operation);

        String procedure = request.getParameter("procedure");
        LOG.trace("Request parameter: procedure --> " + procedure);

        String medicament = request.getParameter("medicament");
        LOG.trace("Request parameter: medicament --> " + medicament);

        String diagnose = request.getParameter("diagnose");
        LOG.trace("Request parameter: diagnose --> " + diagnose);

        medicalCardRequest.setOperations(operation);
        medicalCardRequest.setProcedure(procedure);
        medicalCardRequest.setMedicaments(medicament);
        medicalCardRequest.setDiagnose(diagnose);

        DBManager.getInstance().doctorUpdateMedCard(medicalCardFromSession.getId(), medicalCardRequest);

        //getting doctor ID
        User doctor = (User) session.getAttribute("user");
        List<User> patients = DBManager.getInstance().getDoctorPatients(doctor.getId());

        request.setAttribute("patients", patients);
        LOG.trace("Set the request attribute: patients --> " + patients);

        LOG.debug("Commands finished");
        return Path.PAGE_DOCTOR_CHOOSE_PATIENT;
    }
}
