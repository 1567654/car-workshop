package se.yrgo.workshop.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.yrgo.workshop.data.CustomerRepository;
import se.yrgo.workshop.data.VehicleRepository;
import se.yrgo.workshop.domain.Customer;
import se.yrgo.workshop.domain.Vehicle;
import se.yrgo.workshop.dto.CustomerDTO;
import se.yrgo.workshop.dto.VehicleDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CustomerController {
    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository, VehicleRepository vehicleRepository) {
        this.customerRepository = customerRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@RequestParam String name, @RequestParam String phone) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setPhoneNumber(phone);
        customerRepository.save(customer);

        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    @GetMapping("/customer-id")
    public ResponseEntity<Long> getCustomerIdByName(@RequestParam String name) {
        Customer customer = customerRepository.findByName(name);

        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(customer.getId());
    }

    @GetMapping("/customers")
    public List<CustomerDTO> getCustomersWithVehicle() {
        List<Object[]> all = customerRepository.getAllCustomersWithVehicles();
        Map<Long, CustomerDTO> customerMap = new HashMap<>();

        for (Object[] row : all) {
            Customer customer = (Customer) row[0];
            Vehicle vehicle = (Vehicle) row[1];

            CustomerDTO customerDTO = customerMap.getOrDefault(customer.getId(), new CustomerDTO());
            customerDTO.setId(customer.getId());
            customerDTO.setName(customer.getName());
            customerDTO.setPhoneNumber(customer.getPhoneNumber());

            if (vehicle != null) {
                VehicleDTO vehicleDTO = new VehicleDTO();
                vehicleDTO.setId(vehicle.getId());
                vehicleDTO.setBrand(vehicle.getBrand());
                vehicleDTO.setModel(vehicle.getModel());
                vehicleDTO.setProductionYear(vehicle.getProductionYear());
                vehicleDTO.setRegistrationNumber(vehicle.getRegistrationNumber());
                vehicleDTO.setCustomer(customer);

                customerDTO.getVehicles().add(vehicleDTO);
            }


            customerMap.put(customer.getId(), customerDTO);
        }
        return new ArrayList<>(customerMap.values());
    }

    @PostMapping("/vehicles")
    public ResponseEntity<Vehicle> createVehicle(
            @RequestParam String registrationNumber,
            @RequestParam String brand,
            @RequestParam String model,
            @RequestParam int productionYear) {

        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNumber(registrationNumber);
        vehicle.setBrand(brand);
        vehicle.setModel(model);
        vehicle.setProductionYear(productionYear);

        vehicleRepository.save(vehicle);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicle);
    }

    @GetMapping("/vehicles")
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @GetMapping("/vehicles-by-brand")
    public List<Vehicle> getVehiclesByBrand(@RequestParam String brand) {
        return vehicleRepository.findByBrand(brand);
    }
}
