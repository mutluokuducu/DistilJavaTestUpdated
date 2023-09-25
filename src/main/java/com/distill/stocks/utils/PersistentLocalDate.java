package com.distill.stocks.utils;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;
import org.joda.time.LocalDate;

import java.io.Serializable;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class PersistentLocalDate implements UserType<LocalDate> {
    @Override
    public int getSqlType() {
        return Types.DATE;
    }

    @Override
    public Class<LocalDate> returnedClass() {
        return LocalDate.class;
    }


    @Override
    public boolean equals(LocalDate x, LocalDate y) {
        if (x == y) {
            return true;
        }
        if (x == null || y == null) {
            return false;
        }
        return x.equals(y);
    }

    @Override
    public int hashCode(LocalDate x) {
        return x.hashCode();
    }

    @Override
    public LocalDate nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
        Date timestamp = rs.getDate(position);
        if (timestamp == null) {
            return null;
        }
        return LocalDate.fromDateFields(timestamp);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, LocalDate value, int index, SharedSessionContractImplementor session) throws SQLException {
        if (value == null) {
            st.setNull(index, Types.DATE);
        } else {
            st.setDate(index, new Date(value.toDate().getTime()));
        }
    }

    @Override
    public LocalDate deepCopy(LocalDate value) {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(LocalDate value) {
        return value;
    }

    @Override
    public LocalDate assemble(Serializable cached, Object owner) {
        return (LocalDate) cached;
    }

}
