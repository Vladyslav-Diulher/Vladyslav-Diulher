package ua.nure.diulher.SummaryTask4.web.command.patient;

import org.apache.log4j.Logger;
import ua.nure.diulher.SummaryTask4.Path;
import ua.nure.diulher.SummaryTask4.db.entity.MedicalCard;
import ua.nure.diulher.SummaryTask4.db.entity.User;
import ua.nure.diulher.SummaryTask4.web.command.Command;

import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.reflect.Parameter;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Patient discharge command.
 *
 * @author V.Diulher
 */
public class PatientDownloadCommand extends Command {

    private static final long serialVersionUID = -2785976616686657267L;

    private final static String LS = System.lineSeparator();

    private static final Logger LOG = Logger.getLogger(PatientDownloadCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOG.debug("Command starts");

        //preparing text document to download
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition",
                "attachment;filename=patientVipiska.txt");

        ServletOutputStream out = response.getOutputStream();
        String sb = fillMedCard(request);
        InputStream in =
                new ByteArrayInputStream(sb.getBytes("UTF-8"));

        byte[] outputByte = new byte[4096];
        //copy binary contect to output stream
        while (in.read(outputByte, 0, 4096) != -1) {
            out.write(outputByte, 0, 4096);
        }
        in.close();
        out.flush();
        out.close();

        LOG.debug("Command finished");
        return Path.PAGE_START;
    }

    private String fillMedCard(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String locale = request.getParameter("locale");
        LOG.debug("LOCALE: ---> " + locale);

        ResourceBundle bundle = ResourceBundle.getBundle("resources", new Locale(locale));
        StringBuilder sb = new StringBuilder(bundle.getString("medcard") + LS + bundle.getString("patient") + ": ");
        MedicalCard card = (MedicalCard) session.getAttribute("medicalCard");

        User doctor = (User) session.getAttribute("doctor");
        User patient = (User) session.getAttribute("patient");

        sb.append(patient.getFirstName() + " " + patient.getLastName() + LS);
        sb.append(bundle.getString("doctor") + ": ").append(doctor.getFirstName() + " " + doctor.getLastName()).append(LS);
        sb.append(bundle.getString("diagnose") + ": ").append(card.getDiagnose()).append(LS);
        sb.append(bundle.getString("procedures") + ": ").append(card.getProcedure()).append(LS);
        sb.append(bundle.getString("medicament") + ": ").append(card.getMedicaments()).append(LS);
        sb.append(bundle.getString("doctor") + ": ").append(card.getOperations()).append(LS);
        return sb.toString();
    }
}
