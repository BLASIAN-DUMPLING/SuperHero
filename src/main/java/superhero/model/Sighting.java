package superhero.model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Sighting {

//    sightingId int not null primary key auto_increment,
//    sightingDate timestamp not null default current_timestamp,
//    locationId int,
//    superId int,
//    Constraint foreign key (superId) references super(superId),
//    Constraint foreign key (locationId) references location(locationId));

    private int sightingId;

    private LocalDate sightingDate;

    private Location sightingLocation;

    List<Location> sightingLocations;

    List<Super> supers;

    public List<Super> getSupers() {
        return supers;
    }

    public void setSupers(List<Super> supers) {
        this.supers = supers;
    }

    public List<Location> getSightingLocations() {
        return sightingLocations;
    }

    public void setSightingLocations(List<Location> sightingLocations) {
        this.sightingLocations = sightingLocations;
    }

    private Super sightingSuper;

    public int getSightingId() {
        return sightingId;
    }

    public void setSightingId(int sightingId) {
        this.sightingId = sightingId;
    }

    public LocalDate getSightingDate() {
        return sightingDate;
    }

    public void setSightingDate(LocalDate sightingDate) {
        this.sightingDate = sightingDate;
    }

    public Location getSightingLocation() {
        return sightingLocation;
    }

    public void setSightingLocation(Location sightingLocation) {
        this.sightingLocation = sightingLocation;
    }

    public Super getSightingSuper() {
        return sightingSuper;
    }

    public void setSightingSuper(Super sightingSuper) {
        this.sightingSuper = sightingSuper;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + this.sightingId;
        hash = 61 * hash + Objects.hashCode(this.sightingDate);
        hash = 61 * hash + Objects.hashCode(this.sightingLocation);
        hash = 61 * hash + Objects.hashCode(this.sightingSuper);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sighting other = (Sighting) obj;
        if (this.sightingId != other.sightingId) {
            return false;
        }
        if (!Objects.equals(this.sightingDate, other.sightingDate)) {
            return false;
        }
        if (!Objects.equals(this.sightingLocation, other.sightingLocation)) {
            return false;
        }
        if (!Objects.equals(this.sightingSuper, other.sightingSuper)) {
            return false;
        }
              return true;
    }

    @Override
    public String toString() {
        return "Sighting{" +
                "sightingId=" + sightingId +
                ", sightingDate=" + sightingDate +
                ", sightingLocation=" + sightingLocation +
                ", sightingLocations=" + sightingLocations +
                ", supers=" + supers +
                ", sightingSuper=" + sightingSuper +
                '}';
    }
}
