package service;

import model.Order;
import repository.OrderRepository;

import java.util.List;
import java.util.Optional;

public class OrderService {
    private final OrderRepository orderRepository = new OrderRepository();

    public List<Order> findAllByUserId(int userId) {
        return orderRepository.findAllByUserId(userId);
    }

    public Optional<Order> findById(int orderId) {
        return orderRepository.findById(orderId);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getAllByServicesByUserId(int userId) {
        return orderRepository.getAllByServicesByUserId(userId);
    }
 }
