package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.OrderRepository;
import vn.hoidanit.laptopshop.service.OrderService;

@Controller
public class OrderController {

    // Không có Dependency Injection vì phải tạo OrderService nữa 
    
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    public OrderController (OrderRepository orderRepository, OrderService orderService){
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    @GetMapping("/admin/order")
    public String getOrderForAdminPage(Model model){
        List<Order> orders = this.orderService.findAllOrders();
        model.addAttribute("orders", orders);
        return "admin/order/show";
    }

    @GetMapping("/admin/order/{id}")
    public String getOrderDetailPage(Model model, @PathVariable("id") long id){
        Optional<Order> order = this.orderService.findOrderById(id);
        if(order.isPresent()){
            Order otherOptional = order.get();
            List<OrderDetail> orderDetails = otherOptional.getOrderDetail();
            model.addAttribute("orderDetails", orderDetails);
        }
        return "admin/order/detail";
    }

    @GetMapping("/admin/order/update/{id}")
    public String getOrderUpdatePage(Model model, @PathVariable("id") long id){
        Optional<Order> order = this.orderService.findOrderById(id);
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
        Optional<Order> currentOrderOptional = this.orderService.findOrderById(order.getId());
        if(currentOrderOptional.isPresent()){
            Order currentOrder = currentOrderOptional.get();
           this.orderService.handleDeleteOrder(currentOrder);
        }
        return"redirect:/admin/order";
    }

    @GetMapping("/history-checkout")
    public String getHictoryCheckoutPage(Model model,HttpServletRequest request){
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        User user = new User();
        user.setId(id);

        List<Order> orders = this.orderService.findOrdersByUser(user);
        if(orders != null){
            model.addAttribute("orders", orders);
            for(Order order : orders){
                List<OrderDetail> orderDetails = order.getOrderDetail();
                if(orderDetails != null){
                    model.addAttribute("orderDetails", orderDetails);
                }
            }
        }

        return "client/cart/history-checkout";
    }
}
