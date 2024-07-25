package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;

@Controller
public class OrderController {

    // Không có Dependency Injection vì phải tạo OrderService nữa 
    
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderController (OrderRepository orderRepository, OrderDetailRepository orderDetailRepository){
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    @GetMapping("/admin/order")
    public String getOrderForAdminPage(Model model){
        List<Order> orders = this.orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "admin/order/show";
    }

    @GetMapping("/admin/order/{id}")
    public String getOrderDetailPage(Model model, @PathVariable("id") long id){
        Optional<Order> order = this.orderRepository.findById(id);
        if(order.isPresent()){
            Order otherOptional = order.get();
            List<OrderDetail> orderDetails = otherOptional.getOrderDetail();
            model.addAttribute("orderDetails", orderDetails);
        }
        return "admin/order/detail";
    }

    @GetMapping("/admin/order/update/{id}")
    public String getOrderUpdatePage(Model model, @PathVariable("id") long id){
        Optional<Order> order = this.orderRepository.findById(id);
        Order orderOptional = order.get();
        model.addAttribute("updateOrder", orderOptional);
        return "admin/order/update";
    }

    @PostMapping("/admin/order/update")
    public String postUpdateOrder(@ModelAttribute("updateOrder") Order order){
        Optional<Order> currentOrder = this.orderRepository.findById(order.getId());
        if(currentOrder.isPresent()){
            Order curOrderOptional = currentOrder.get();
            curOrderOptional.setStatus(order.getStatus());
            this.orderRepository.save(curOrderOptional);
        }
        return"redirect:/admin/order";
    }

    @GetMapping("/admin/order/delete/{id}")
    public String getDeleteOrderPage(Model model, @PathVariable("id") long id){
        model.addAttribute("deleteOrder", new Order());
        return "admin/order/delete";
    }

    @PostMapping("/admin/order/delete")
    public String postDeleteOrder(@ModelAttribute("deleteOrder") Order order){
        Optional<Order> currentOrderOptional = this.orderRepository.findById(order.getId());
        if(currentOrderOptional.isPresent()){
            Order currentOrder = currentOrderOptional.get();
            List<OrderDetail> orderDetails = currentOrder.getOrderDetail();
            if(orderDetails != null){
                for(OrderDetail cd : orderDetails){
                    this.orderDetailRepository.deleteById(cd.getId());
                }
                this.orderRepository.deleteById(order.getId());
            }
        }
        return"redirect:/admin/order";
    }
}
