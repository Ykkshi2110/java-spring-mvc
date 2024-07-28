package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository){
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public Page<Order> findAllOrders(Pageable pageable){
        return this.orderRepository.findAll(pageable);
    }

    public List<Order> findOrdersByUser(User user){
        return this.orderRepository.findByUser(user);
    }

    public Optional<Order> findOrderById(long id){
        return this.orderRepository.findById(id);
    }

    public void handleDeleteOrder(Order order){
        List<OrderDetail> orderDetails = order.getOrderDetail();
        if(orderDetails != null){
            for(OrderDetail orderDetail : orderDetails){
                this.orderDetailRepository.deleteById(orderDetail.getId());
            }

            this.orderRepository.deleteById(order.getId());
        }
    }
}
