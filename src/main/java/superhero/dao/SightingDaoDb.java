package superhero.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import superhero.model.Location;
import superhero.model.Sighting;
import superhero.model.Super;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SightingDaoDb implements SightingDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Sighting setSightingById(int sightingId) {
        try {
            final String SELECT_SIGHTING_BY_ID = "SELECT * FROM sightingLocation WHERE id = ?";
            Sighting sighting = jdbcTemplate.queryForObject(SELECT_SIGHTING_BY_ID, new SightingMapper(), sightingId);
            return sighting;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public Sighting getSightingById(int sightingId) {
        return null;
    }

    private Super getSuperForSighting(Sighting sighting) {
        final String SELECT_SUPER_FOR_SIGHTING = "SELECT s. * FROM superperson s "
                + "JOIN sightinglocation si ON s.superId = si.superId WHERE si.sightingId = ?";
        return jdbcTemplate.queryForObject(SELECT_SUPER_FOR_SIGHTING, new SuperDaoDb.SuperMapper(), sighting.getSightingId());
    }

    private Location getLocationForSighting(Sighting sighting){
        //TODO need to do join query

        final String SELECT_LOCATION_FOR_SIGHTING = "SELECT l. * FROM location l "
                + "JOIN sightinglocation si ON l.locationId = si.locationId WHERE si.sightingId = ?";
        return jdbcTemplate.queryForObject(SELECT_LOCATION_FOR_SIGHTING, new LocationDaoDb.LocationMapper(), sighting.getSightingId());

    }

    private void addSuperAndLocationToSightings(List<Sighting> sightings) {
        for (Sighting sighting :sightings) {
            sighting.setSightingSuper(getSuperForSighting(sighting));
            sighting.setSightingLocation(getLocationForSighting(sighting));
        }
    }

    @Override
    public List<Sighting> getAllSightings() {
//        final String GET_ALL_SIGHTINGS = "SELECT * FROM sightinglocation";
//        return jdbcTemplate.query(GET_ALL_SIGHTINGS, new SightingMapper());
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM sightinglocation";
        List<Sighting> sightings = jdbcTemplate.query(SELECT_ALL_SIGHTINGS, new SightingMapper());

        addSuperAndLocationToSightings(sightings);

        return sightings;
    }

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        //TODO: should the model be changed?
        final String INSERT_SIGHTING = "INSERT INTO sightinglocation (sightingDate, locationId, superId) "
                + "VALUES(?,?,?)";
        jdbcTemplate.update(INSERT_SIGHTING,
                sighting.getDate(),
                sighting.getSightingLocation(),
                sighting.getSightingSuper());

        int newSightingId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID", Integer.class);
        sighting.setSightingId(newSightingId);
        return sighting;
    }

    @Override
    public void updateSighting(Sighting sighting) {
    final String UPDATE_SIGHTING = "UPDATE sightinglocation SET sightingDate = ?, locationId = ?, superId = ?)"
            + "WHERE sightingId = ?";
    jdbcTemplate.update(UPDATE_SIGHTING,
            sighting.getDate(),
            sighting.getSightingLocation().getLocationId(),
            sighting.getSightingSuper().getSuperId(),
            sighting.getSightingId());
    }

    @Override
    @Transactional
    public void deleteSightingById(int sightingId) {
        final String DELETE_SIGHTING = "DELETE from sightinglocation WHERE sightingId = ?";
        jdbcTemplate.update(DELETE_SIGHTING, sightingId);

    }

//    @Override
//    public List<Sighting> getSightingsForLocation(Location location) {
//        final String SELECT_SIGHTINGS_FOR_LOCATION = "SELECT * FROM sightinglocation WHERE locationId = ?";
//        List<Sighting> sighting = jdbcTemplate.query(SELECT_SIGHTINGS_FOR_LOCATION, new SightingMapper(), location.getLocationId());
//        associateLocationsForSighting(sighting);
//        return sighting;
//    }
//
//    void associateLocationsForSighting(List<Sighting> sightings){
//        for (Sighting sighting : sightings){
//            sighting.setSightingLocation(getLocationForSighting(sighting.getSightingId()));
//        }
//    }


//    void associateSuperForSighting(List<Sighting> sightings) {
//        for (Sighting sighting : sightings) {
//            sighting.setSightingLocation(getSuperForSighting(sighting.getSightingId()));
//        }
//    }

    public static final class SightingMapper implements RowMapper<Sighting> {
        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setSightingId(rs.getInt("sightingId"));
            sighting.setDate(rs.getDate("date").toLocalDate());

            return sighting;
        }
    }
}
