package ua.nure.diulher.SummaryTask4.db;


import ua.nure.diulher.SummaryTask4.db.entity.User;

public enum Role {
    ADMIN, PATIENT, MED_SIS, DOCTOR;

    public static Role getRole(User user) {
        int roleId = user.getRoleId();
        return Role.values()[roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }

}
