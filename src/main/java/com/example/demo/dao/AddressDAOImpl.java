package com.example.demo.dao;

import com.example.demo.domain.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AddressDAOImpl implements DAO<Address>{

    private static final String INSERTED_ADDRESS
            = "INSERT INTO ADDRESS(ADDRESS_ID, ADDRESS) VALUES( ?, ? );";
    private static final String SELECT_ADDRESS
            = "SELECT ADDRESS FROM ADDRESS WHERE ADDRESS_ID= :addressId";
    private static final String IS_EXISTS_ADDRESS
            = "SELECT COUNT(*) FROM ADDRESS WHERE ADDRESS_ID= :addressId";

    private static final String UPDATE_ADDRESS
            = "UPDATE ADDRESS SET ADDRESS= ? WHERE ADDRESS_ID= ?";

    private static final String DELETE_ADDRESS
            = "DELETE FROM ADDRESS WHERE ADDRESS_ID= ?";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    @Transactional
    public void insert(Address address) {
        jdbcTemplate.update(INSERTED_ADDRESS, address.getAddressId(), address.getAddress());
    }

    @Override
    @Transactional
    public void delete(String addressId) {
        jdbcTemplate.update(DELETE_ADDRESS, addressId );
    }

    @Override
    public String select(String addressId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("addressId", addressId);
        return namedParameterJdbcTemplate.queryForObject(
                SELECT_ADDRESS, namedParameters, String.class);
    }

    @Override
    public boolean isExists(String addressId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("addressId", addressId);
        int count = namedParameterJdbcTemplate.queryForObject(IS_EXISTS_ADDRESS, namedParameters, Integer.class);
        return count>0? true: false;
    }

    @Override                                 
    @Transactional
    public void update(Object addressId, Object address) {
        jdbcTemplate.update(UPDATE_ADDRESS, address.toString(), addressId.toString());
    }
}
