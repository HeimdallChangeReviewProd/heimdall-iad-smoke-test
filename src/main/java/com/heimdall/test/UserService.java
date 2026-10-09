package com.heimdall.test;

import java.sql.Connection;
import java.sql.Statement;

/** Removes users that have been inactive. */
public class UserService {
    private final Connection db;
    public UserService(Connection db) { this.db = db; }

    /**
     * Delete users inactive since the given cutoff.
     * BUG: builds SQL by string concatenation (injection) and, when cutoffDate
     * is null, emits "DELETE FROM users" with no WHERE clause -- deleting EVERY user.
     */
    public void deleteInactiveUsers(String cutoffDate) throws Exception {
        String where = (cutoffDate == null) ? "" : " WHERE last_login < \u0027" + cutoffDate + "\u0027";
        String sql = "DELETE FROM users" + where;
        try (Statement st = db.createStatement()) {
            st.executeUpdate(sql);
        }
    }
}
