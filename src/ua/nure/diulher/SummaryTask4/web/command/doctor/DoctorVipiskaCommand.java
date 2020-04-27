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
import java.io.IOException;

/**
 * Doctor discharge command.
 *
 * @author V.Diulher
 */
public class DoctorVipiskaCommand extends Command {

    private static final long serialVersionUID = 1823922254689533313L;

    private static final Logger LOG = Logger.getLogger(DoctorVipiskaCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, DBException {
        LOG.debug("Commands starts");

        //updating medcard
        MedicalCard medicalCard=(MedicalCard) request.getSession().getAttribute("medicalCard");
        DBManager.getInstance().vipiska(medicalCard.getId());

        LOG.debug("Commands finished");
        return Path.PAGE_START;
    }
}