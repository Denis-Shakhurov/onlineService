package org.example.service;

import org.example.model.Service;
import org.example.repository.ServiceRepository;

import java.util.List;
import java.util.Optional;

public class ServiceService {
    private final ServiceRepository serviceRepository = new ServiceRepository();

    public Service save(Service service) {
        return serviceRepository.save(service);
    }

    public Optional<Service> findById(int id) {
        return serviceRepository.findById(id);
    }

    public List<Service> findAllForUser(int userId) {
        return serviceRepository.findAllForUser(userId);
    }

    public Service update(Service service) {
        return serviceRepository.update(service);
    }
}
