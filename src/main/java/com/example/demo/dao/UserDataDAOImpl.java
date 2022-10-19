package com.example.demo.dao;

import com.example.demo.domain.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class UserDataDAOImpl implements DAO<UserData>{

    private static final String INSERT_USER_DATA
            = "INSERT INTO USER_DATA(USERNAME,ADDRESS_ID) VALUES( ?, ? );";
    private static final String DELETE_USER
            = "DELETE FROM USER_DATA WHERE USERNAME= ?";
    private static final String DELETE_USER_ADDRESS
            = "DELETE FROM USER_DATA WHERE USERNAME= ? AND ADDRESS_ID= ?";
    private static final String UPDATE_USER_ADDRESS
            = "UPDATE USER_DATA SET ADDRESS_ID= ? WHERE USERNAME= ? AND ADDRESS_ID= ?";
    private static final String SELECT_ADDRESSES
            = "SELECT ADDRESS_ID FROM USER_DATA WHERE USERNAME= :username"; //may return multiple entries
    private static final String IS_EXISTS_ADDRESS
            = "SELECT COUNT(*) FROM USER_DATA WHERE USERNAME= :username AND ADDRESS_ID= :addressId";
    private static final String IS_EXISTS_USER
            = "SELECT COUNT(*) FROM USER_DATA WHERE USERNAME= :username";


    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void insert(UserData userData) {
        jdbcTemplate.update(INSERT_USER_DATA, userData.getUserName(), userData.getAddressId());
    }

    @Override
    @Transactional
    public void delete(String username) {
        jdbcTemplate.update(DELETE_USER, username);
    }

    @Transactional
    public void deleteUserAddress(String username, String addressId) {
        jdbcTemplate.update(DELETE_USER_ADDRESS, username, addressId);
    }

    @Override
    public List<String> select(String username) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("username", username);
        List<String> addresses = namedParameterJdbcTemplate.queryForObject(SELECT_ADDRESSES, namedParameters, List.class);
        return addresses;
    }

    @Override
    public boolean isExists(String key) {
        return false;
    }

    @Override
    public void update(Object t, Object r) {

    }

    public boolean isAddressExists(String username, String addressId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("username", username)
                .addValue("addressId", addressId);
        int count = namedParameterJdbcTemplate.queryForObject(IS_EXISTS_ADDRESS, namedParameters, Integer.class);
        return count>0? true: false;
    }
    public boolean isUserExists(String username) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("username", username);
        int count = namedParameterJdbcTemplate.queryForObject(IS_EXISTS_USER, namedParameters, Integer.class);
        return count>0? true: false;
    }

    @Transactional
    public void update(String username, String addressToBeReplaced, String actualAddress) {

        jdbcTemplate.update(UPDATE_USER_ADDRESS, actualAddress, username, addressToBeReplaced);
    }
}
