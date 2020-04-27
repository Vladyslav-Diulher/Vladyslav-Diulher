package ua.nure.diulher.SummaryTask4.db.entity;

public class MedicalCard extends Entity{

    private long patientID;

    private long doctorID;

    private String diagnose;

    private String procedure;

    private String medicaments;

    private String operations;

    private boolean vipiska;

    public void setVipiska(boolean vipiska) {
        this.vipiska = vipiska;
    }

    public boolean getVipiska() {
        return vipiska;
    }

    public void setPatientID(long patientID) {
        this.patientID = patientID;
    }

    public void setDoctorID(long doctorID) {
        this.doctorID = doctorID;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public void setMedicaments(String medicaments) {
        this.medicaments = medicaments;
    }

    public void setOperations(String operations) {
        this.operations = operations;
    }

    public long getPatientID() {
        return patientID;
    }

    public long getDoctorID() {
        return doctorID;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public String getProcedure() {
        return procedure;
    }

    public String getMedicaments() {
        return medicaments;
    }

    public String getOperations() {
        return operations;
    }

    @Override
    public String toString() {
        return "MedicalCard{" +
                "ID=" + getId() +
                "patientID=" + patientID +
                ", doctorID=" + doctorID +
                ", diagnose='" + diagnose +
                ", procedure='" + procedure +
                ", medicaments='" + medicaments +
                ", operations='" + operations +
                '}';
    }
}
