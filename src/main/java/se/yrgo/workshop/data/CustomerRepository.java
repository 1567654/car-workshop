package se.yrgo.workshop.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.yrgo.workshop.domain.Customer;
import se.yrgo.workshop.domain.Vehicle;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Customer findByName(String name);

    @Query("select c.vehicles from Customer c where c.id = :customerId")
    public List<Vehicle> findByCustomer(@Param("customerId") Long customerId);

    @Query("select c, v from Customer c left join c.vehicles v")
    public List<Object[]> getAllCustomersWithVehicles();
}
