package se.yrgo.workshop.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.yrgo.workshop.domain.Vehicle;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query("select v from Vehicle v where v.brand = :brand")
    public List<Vehicle> findByBrand(@Param("brand") String brand);

}
