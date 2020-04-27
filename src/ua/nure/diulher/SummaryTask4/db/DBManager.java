package ua.nure.diulher.SummaryTask4.db;


import org.apache.log4j.Logger;
import ua.nure.diulher.SummaryTask4.db.entity.MedicalCard;
import ua.nure.diulher.SummaryTask4.db.entity.User;
import ua.nure.diulher.SummaryTask4.exception.DBException;
import ua.nure.diulher.SummaryTask4.exception.Messages;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DBManager {

    private static final Logger LOG = Logger.getLogger(DBManager.class);

    // //////////////////////////////////////////////////////////
    // singleton
    // //////////////////////////////////////////////////////////

    private static DBManager instance;

    public static synchronized DBManager getInstance() throws DBException {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() throws DBException {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            // ST4DB - the name of data source
            ds = (DataSource) envContext.lookup("jdbc/project0");             //-------------------
            LOG.trace("Data source ==> " + ds);
        } catch (NamingException ex) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
        }
    }

    private DataSource ds;

    // //////////////////////////////////////////////////////////
    // SQL queries
    // //////////////////////////////////////////////////////////

    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";

    private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM users WHERE id=?";

    private static final String SQL_UPDATE_USER = "UPDATE users SET password=?, first_name=?, last_name=?"
            + "	WHERE id=?";

    private static final String SQL_GET_DOCTORS = "SELECT u.id, u.first_name, u.last_name, u.login, u.password, p.position"
            + "	FROM users u, doctor_position dp, positions p"
            + "	WHERE u.role_id='3' AND u.id=dp.doc_id AND dp.position_id=p.id";


    private static final String SQL_GET_PATIENTS = "SELECT u.id, u.first_name, u.last_name, u.login, u.password, u.birth_date "
            + "	FROM users u"
            + "	WHERE u.role_id='1'";

    private static final String SQL_GET_MEDCARD = "SELECT  m.id, m.diagnose, m.procedures, m.medicament," +
            " m.operation, m.vipiska, p.doc_id, p.patient_id  "
            + "FROM med_card m, doctor_to_patient p "
            + "WHERE p.patient_id=? AND m.doc_pat=p.id ";

    private static final String GET_DOCTOR_PATIENTS = "SELECT u.id, u.first_name, u.login, u.password, u.last_name, u.role_id, u.birth_date "
            + "	FROM doctor_to_patient d, users u "
            + "	WHERE d.patient_id=u.id AND d.doc_id=? ";

    private static final String SQL_DOCTOR_UPDATE_MEDCARD = "UPDATE med_card SET diagnose=?, procedures=?," +
            " medicament=?, operation=? "
            + "	WHERE id=?";

    private static final String SQL_MEDSIS_UPDATE_MEDCARD = "UPDATE med_card SET  procedures=?," +
            " medicament=? "
            + "	WHERE id=?";

    private static final String SQL_VIPISKA = "UPDATE med_card SET  vipiska=true "
            + "	WHERE id=?";

    private static final String SQL_COUNT_PATIENTS = "SELECT COUNT(*) " +
            "FROM doctor_to_patient " +
            "WHERE doc_id=?";


    private static final String SQL_INSERT_PATIENT = "INSERT INTO users " +
            " VALUES (DEFAULT, ? , ?,?, ? , ? , 1)";

    private static final String SQL_INSERT_DOCTOR_TO_PATIENT = "INSERT INTO doctor_to_patient " +
            " VALUES (DEFAULT, ? , ?)";

    private static final String SQL_FIND_DTP_ID = "SELECT d.id"
            + "	FROM doctor_to_patient d "
            + "	WHERE d.patient_id=? ";

    private static final String SQL_INSERT_MEDCARD = "INSERT INTO med_card " +
            " VALUES (DEFAULT, ?,null ,null ,null ,null, false )";

    private static final String SQL_INSERT_DOCTOR = "INSERT INTO users " +
            " VALUES (DEFAULT, ? , ?, ?, ? , ? , 3)";

    private static final String SQL_INSERT_DOCTOR_POSITION = "INSERT INTO doctor_position " +
            " VALUES (? , ?)";

    private static final String SQL_INSERT_MEDSIS = "INSERT INTO users " +
            " VALUES (DEFAULT, ? , ?, ?, ? , null , 2)";


    private static final String SQL_DELETE_USER = "DELETE FROM users " +
            "WHERE id=? ";

    private static final String SQL_DELETE_DTP = "DELETE FROM doctor_to_patient " +
            "WHERE patient_id=? ";

    private static final String SQL_DELETE_MEDCARD = "DELETE FROM med_card " +
            "WHERE doc_pat=? ";


    /**
     * Delete MedCard
     *
     * @param DTPId id of doctor and patient bound.
     * @throws DBException
     */
    public void deleteMedcard(long DTPId) throws DBException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_DELETE_MEDCARD);
            int k = 1;
            pstmt.setLong(k, DTPId);
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_DOCTOR, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_DOCTOR, ex);
        } finally {
            close(pstmt);
        }
    }

    /**
     * Delete bound of patient and doctor.
     *
     * @param patientId id of patient.
     * @throws DBException
     */
    public void deleteDTP(long patientId) throws DBException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_DELETE_DTP);
            int k = 1;
            pstmt.setLong(k, patientId);
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_DOCTOR, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_DOCTOR, ex);
        } finally {
            close(pstmt);
        }
    }

    /**
     * Delete user.
     *
     * @param userId id of user to delete.
     * @throws DBException
     */
    public void deleteUser(long userId) throws DBException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_DELETE_USER);
            int k = 1;
            pstmt.setLong(k, userId);
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_DOCTOR, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_DOCTOR, ex);
        } finally {
            close(pstmt);
        }
    }

    /**
     * Prepare patient for discharge.
     *
     * @param medCardID id of medCard.
     * @throws DBException
     */
    public void vipiska(long medCardID) throws DBException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_VIPISKA);
            int k = 1;
            pstmt.setLong(k, medCardID);
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_PATIENT, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_PATIENT, ex);
        } finally {
            close(pstmt);
        }

    }

    /**
     * Insert a medSis.
     *
     * @param medSis medSis data.
     * @throws DBException
     */
    public void insertMedSis(User medSis) throws DBException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_INSERT_MEDSIS);
            int k = 1;
            pstmt.setString(k++, medSis.getLogin());
            pstmt.setString(k++, medSis.getPassword());
            pstmt.setString(k++, medSis.getFirstName());
            pstmt.setString(k++, medSis.getLastName());
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_DOCTOR, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_DOCTOR, ex);
        } finally {
            close(pstmt);
        }
    }

    /**
     * insert position of doctor.
     *
     * @param doctorID id of a doctor.
     * @param positionID id of position.
     * @throws DBException
     */
    public void insertDoctorPosition(long doctorID, long positionID) throws DBException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_INSERT_DOCTOR_POSITION);
            int k = 1;
            pstmt.setLong(k++, doctorID);
            pstmt.setLong(k, positionID);
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_DOCTOR, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_DOCTOR, ex);
        } finally {
            close(pstmt);
        }
    }

    /**
     * Insert a doctor.
     *
     * @param doctor doctor data.
     * @throws DBException
     */
    public void insertDoctor(User doctor) throws DBException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_INSERT_DOCTOR);
            int k = 1;
            pstmt.setString(k++, doctor.getLogin());
            pstmt.setString(k++, doctor.getPassword());
            pstmt.setString(k++, doctor.getFirstName());
            pstmt.setString(k++, doctor.getLastName());
            pstmt.setString(k, doctor.getDate());
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_DOCTOR, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_DOCTOR, ex);
        } finally {
            close(pstmt);
        }
    }

    /**
     * Insert medCard.
     *
     * @param DTPId id of table of bounds.
     * @throws DBException
     */
    public void insertMedCard(long DTPId) throws DBException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_INSERT_MEDCARD);
            int k = 1;
            pstmt.setLong(k, DTPId);
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_PATIENT, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_PATIENT, ex);
        } finally {
            close(pstmt);
        }
    }

    /**
     * Find id of table of bounds.
     *
     * @param patientId id of patient.
     * @throws DBException
     */
    public long findDTPID(long patientId) throws DBException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        long result = 0L;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_FIND_DTP_ID);
            int k = 1;
            pstmt.setLong(k, patientId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getLong(Fields.ENTITY_ID);
            }
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_DTPID, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_DTPID, ex);
        } finally {
            close(pstmt);
        }
        return result;
    }

    /**
     * Connect doctor and patient in a table.
     *
     * @param doctorId id of doctor.
     * @param patientId id of patient.
     * @throws DBException
     */
    public void boundPatientAndDoctor(long doctorId, long patientId) throws DBException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_INSERT_DOCTOR_TO_PATIENT);
            int k = 1;
            pstmt.setLong(k++, doctorId);
            pstmt.setLong(k, patientId);
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_PATIENT, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_PATIENT, ex);
        } finally {
            close(pstmt);
        }

    }

    /**
     * Find user.
     *
     * @param login id of doctor.
     * @throws DBException
     */
    public User findUserByLogin(String login) throws DBException {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_FIND_USER_BY_LOGIN);
            pstmt.setString(1, login);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
        } finally {
            close(con, pstmt, rs);
        }
        return user;
    }

    /**
     * Insert patient.
     *
     * @param patient patient data.
     * @throws DBException
     */
    public void insertPatient(User patient) throws DBException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_INSERT_PATIENT);
            int k = 1;
            pstmt.setString(k++, patient.getLogin());
            pstmt.setString(k++, patient.getPassword());
            pstmt.setString(k++, patient.getFirstName());
            pstmt.setString(k++, patient.getLastName());
            pstmt.setString(k, patient.getDate());
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_PATIENT, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_PATIENT, ex);
        } finally {
            close(pstmt);
        }
    }

    /**
     * Count doctors patients.
     *
     * @param doctorID id of doctor.
     * @throws DBException
     */
    public int countPatients(long doctorID) throws DBException {
        int result = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_COUNT_PATIENTS);
            pstmt.setLong(1, doctorID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
            con.commit();
        } catch (SQLException | DBException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_PATIENT, ex);
        } finally {
            close(con, pstmt, rs);
        }
        return result;

    }

    /**
     * Update medcard.
     *
     * @param medCardID id of medcard.
     * @param medicalCard parameters of medcard.
     * @throws DBException
     */
    public void doctorUpdateMedCard(long medCardID, MedicalCard medicalCard) throws DBException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_DOCTOR_UPDATE_MEDCARD);
            int k = 1;
            pstmt.setString(k++, medicalCard.getDiagnose());
            pstmt.setString(k++, medicalCard.getProcedure());
            pstmt.setString(k++, medicalCard.getMedicaments());
            pstmt.setString(k++, medicalCard.getOperations());
            pstmt.setLong(k, medCardID);
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_PATIENT, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_PATIENT, ex);
        } finally {
            close(pstmt);
        }

    }

    /**
     * Update medcard.
     *
     * @param medCardID id of medcard.
     * @param medicalCard parameters of medcard.
     * @throws DBException
     */
    public void medSisUpdateMedCard(long medCardID, MedicalCard medicalCard) throws DBException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_MEDSIS_UPDATE_MEDCARD);
            int k = 1;
            pstmt.setString(k++, medicalCard.getProcedure());
            pstmt.setString(k++, medicalCard.getMedicaments());
            pstmt.setLong(k, medCardID);
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_PATIENT, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_PATIENT, ex);
        } finally {
            close(pstmt);
        }

    }

    /**
     * Find doctors patients.
     *
     * @param doctorID id of doctor to find his patients.
     * @throws DBException
     */

    public List<User> getDoctorPatients(long doctorID) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<User> patients = new ArrayList<>();
        try {
            con = getConnection();
            pstmt = con.prepareStatement(GET_DOCTOR_PATIENTS);
            pstmt.setLong(1, doctorID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                patients.add(extractUserPatient(rs));
                LOG.debug(patients);
            }
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_PATIENT, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_PATIENT, ex);
        } finally {
            close(con, pstmt, rs);
        }
        return patients;
    }

    /**
     * Find medCard.
     *
     * @param patientID id of patient owner of medcard.
     * @throws DBException
     */
    public MedicalCard getMedicalCard(long patientID) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        MedicalCard medicalCard = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_GET_MEDCARD);
            pstmt.setLong(1, patientID);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                medicalCard = extractMedCard(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_MEDCARD, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_MEDCARD, ex);
        } finally {
            close(con, pstmt, rs);
        }
        return medicalCard;
    }

    private MedicalCard extractMedCard(ResultSet rs)
            throws SQLException {
        MedicalCard medicalCard = new MedicalCard();
        medicalCard.setId(rs.getLong(Fields.ENTITY_ID));
        medicalCard.setDiagnose(rs.getString(Fields.PATIENT_DIAGNOSE));
        medicalCard.setMedicaments(rs.getString(Fields.PATIENT_MEDICAMENT));
        medicalCard.setProcedure(rs.getString(Fields.PATIENT_PROCEDURES));
        medicalCard.setOperations(rs.getString(Fields.PATIENT_OPERATION));
        medicalCard.setDoctorID(rs.getLong(Fields.MEDCARD_DOC_ID));
        medicalCard.setPatientID(rs.getLong(Fields.MEDCARD_PATIENT_ID));
        medicalCard.setVipiska(rs.getBoolean(Fields.MEDCARD_VIPISKA));
        return medicalCard;
    }


    /**
     * Finding all doctors.
     *
     * @throws DBException
     */
    public List<User> getUsersDoctors() throws DBException {
        List<User> usersDoctorList = new ArrayList();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_GET_DOCTORS);
            while (rs.next()) {
                usersDoctorList.add(extractUserDoctor(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_DOCTOR, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_DOCTOR, ex);
        } finally {
            close(con, stmt, rs);
        }
        return usersDoctorList;
    }


    private User extractUserDoctor(ResultSet rs)
            throws SQLException {
        User user = new User();
        user.setId(rs.getLong(Fields.USER_ID));
        user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
        user.setLastName(rs
                .getString(Fields.USER_LAST_NAME));
        user.setLogin(rs
                .getString(Fields.USER_LOGIN));
        user.setPassword(rs
                .getString(Fields.USER_PASSWORD));
        user.setPosition(rs
                .getString(Fields.USER_POSITION));
        user.setRoleId(3);
        return user;
    }

    /**
     * Finding all patients.
     *
     * @throws DBException
     */
    public List<User> getUsersPatients() throws DBException {
        List<User> usersPatientList = new ArrayList();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_GET_PATIENTS);
            while (rs.next()) {
                usersPatientList.add(extractUserPatient(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_PATIENT, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_PATIENT, ex);
        } finally {
            close(con, stmt, rs);
        }
        return usersPatientList;
    }

    private User extractUserPatient(ResultSet rs)
            throws SQLException {
        User user = new User();
        user.setId(rs.getLong(Fields.USER_ID));
        user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
        user.setLastName(rs
                .getString(Fields.USER_LAST_NAME));
        user.setLogin(rs
                .getString(Fields.USER_LOGIN));
        user.setPassword(rs
                .getString(Fields.USER_PASSWORD));
        user.setDate(rs.getString(Fields.USER_DATE));
        user.setRoleId(1);
        return user;
    }


    /**
     * Returns a DB connection from the Pool Connections. Before using this
     * method you must configure the Date Source and the Connections Pool in
     * your WEB_APP_ROOT/META-INF/context.xml file.
     *
     * @return DB connection.
     */
    public Connection getConnection() throws DBException {
        Connection con = null;
        try {
            con = ds.getConnection();
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
        }
        return con;
    }

    public User findUser(long id) throws DBException {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_FIND_USER_BY_ID);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID, ex);
        } finally {
            close(con, pstmt, rs);
        }
        return user;
    }


    // //////////////////////////////////////////////////////////
    // Entity access methods (for transactions)
    // //////////////////////////////////////////////////////////

    /**
     * Update user.
     *
     * @param user user to update.
     * @throws SQLException
     */
    private void updateUser(Connection con, User user) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(SQL_UPDATE_USER);
            int k = 1;
            pstmt.setString(k++, user.getPassword());
            pstmt.setString(k++, user.getFirstName());
            pstmt.setString(k++, user.getLastName());
            pstmt.setLong(k, user.getId());
            pstmt.executeUpdate();
        } finally {
            close(pstmt);
        }
    }

    // //////////////////////////////////////////////////////////
    // DB util methods
    // //////////////////////////////////////////////////////////

    /**
     * Closes a connection.
     *
     * @param con Connection to be closed.
     */
    private void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, ex);
            }
        }
    }

    /**
     * Closes a statement object.
     */
    private void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, ex);
            }
        }
    }

    /**
     * Closes a result set object.
     */
    private void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_RESULTSET, ex);
            }
        }
    }

    /**
     * Closes resources.
     */
    private void close(Connection con, Statement stmt, ResultSet rs) {
        close(rs);
        close(stmt);
        close(con);
    }

    /**
     * Rollbacks a connection.
     *
     * @param con Connection to be rollbacked.
     */
    private void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                LOG.error("Cannot rollback transaction", ex);
            }
        }
    }

    // //////////////////////////////////////////////////////////
    // Other methods
    // //////////////////////////////////////////////////////////

    /**
     * Extracts a user entity from the result set.
     *
     * @param rs Result set from which a user entity will be extracted.
     * @return User entity
     */
    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(Fields.ENTITY_ID));
        user.setLogin(rs.getString(Fields.USER_LOGIN));
        user.setPassword(rs.getString(Fields.USER_PASSWORD));
        user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
        user.setLastName(rs.getString(Fields.USER_LAST_NAME));
        user.setRoleId(rs.getInt(Fields.USER_ROLE_ID));
        return user;
    }


}
