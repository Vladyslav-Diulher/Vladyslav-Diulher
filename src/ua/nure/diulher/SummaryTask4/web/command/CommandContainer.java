package ua.nure.diulher.SummaryTask4.web.command;

import org.apache.log4j.Logger;
import ua.nure.diulher.SummaryTask4.web.command.admin.*;
import ua.nure.diulher.SummaryTask4.web.command.admin.addMedSis.AdminAddMedSisCommand;
import ua.nure.diulher.SummaryTask4.web.command.admin.addMedSis.AdminInsertMedSisCommand;
import ua.nure.diulher.SummaryTask4.web.command.admin.addDoctor.AdminAddDoctorCommand;
import ua.nure.diulher.SummaryTask4.web.command.admin.addDoctor.AdminInsertDoctorCommand;
import ua.nure.diulher.SummaryTask4.web.command.admin.addPatient.AdminAddPatientCommand;
import ua.nure.diulher.SummaryTask4.web.command.admin.addPatient.AdminObtainPatentCommand;
import ua.nure.diulher.SummaryTask4.web.command.doctor.DoctorChoosePatientCommand;
import ua.nure.diulher.SummaryTask4.web.command.doctor.DoctorVipiskaCommand;
import ua.nure.diulher.SummaryTask4.web.command.doctor.GetMedCardForUpdate;
import ua.nure.diulher.SummaryTask4.web.command.doctor.UpdateMedCardCommand;
import ua.nure.diulher.SummaryTask4.web.command.medSister.MedSisChoosePatientCommand;
import ua.nure.diulher.SummaryTask4.web.command.medSister.MedSisGetMedCardForUpdate;
import ua.nure.diulher.SummaryTask4.web.command.medSister.MedSisUpdateMedCardCommand;
import ua.nure.diulher.SummaryTask4.web.command.patient.MedicalCardCommand;
import ua.nure.diulher.SummaryTask4.web.command.patient.PatientFinalVipiskaCommand;
import ua.nure.diulher.SummaryTask4.web.command.patient.PatientDownloadCommand;

import java.util.Map;
import java.util.TreeMap;

/**
 * Command container.
 *
 * @author V.Diulher
 */
public class CommandContainer {

    private static final Logger LOG = Logger.getLogger(CommandContainer.class);

    private static Map<String, Command> commands = new TreeMap<String, Command>();

    static {
        // common commands
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());

        //admin commands
        commands.put("viewDoctorList", new AdminDoctorsViewCommand());
        commands.put("viewPatientList", new AdminPatientsViewCommand());
        commands.put("addPatient", new AdminAddPatientCommand());
        commands.put("addDoctor", new AdminAddDoctorCommand());
        commands.put("addMedSis", new AdminAddMedSisCommand());
        commands.put("fillPatientForm", new AdminAddPatientCommand());
        commands.put("insertPatient", new AdminObtainPatentCommand());
        commands.put("insertDoctor", new AdminInsertDoctorCommand());
        commands.put("insertMedSis", new AdminInsertMedSisCommand());

        //patient commands
        commands.put("medicalCard", new MedicalCardCommand());
        commands.put("patientDownload", new PatientDownloadCommand());
        commands.put("vipiskaFinal", new PatientFinalVipiskaCommand());

        //doctor commands
        commands.put("choosePatient", new DoctorChoosePatientCommand());
        commands.put("choice", new GetMedCardForUpdate());
        commands.put("updateMedCard", new UpdateMedCardCommand());
        commands.put("vipiska", new DoctorVipiskaCommand());

        //medsis commands
        commands.put("medSisChoosePatient", new MedSisChoosePatientCommand());
        commands.put("medSisGetMedCard", new MedSisGetMedCardForUpdate());
        commands.put("medSisUpdateMedCard", new MedSisUpdateMedCardCommand());

        LOG.debug("Command container was successfully initialized");
        LOG.trace("Number of commands --> " + commands.size());
    }

    /**
     * Returns command object with the given name.
     *
     * @param commandName Name of the command.
     * @return Command object.
     */
    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            LOG.trace("Command not found, name --> " + commandName);
            return commands.get("noCommand");
        }

        return commands.get(commandName);
    }

}
