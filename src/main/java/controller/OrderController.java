package controller;

import dto.BasePage;
import dto.UserPage;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;
import model.Order;
import model.OrderStatus;
import model.Service;
import model.User;
import service.OrderService;
import service.ServiceService;
import service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static io.javalin.rendering.template.TemplateUtil.model;

public class OrderController extends BaseController {
    private final OrderService orderService;
    private final UserService userService;
    private final ServiceService serviceService;

    public OrderController(OrderService orderService, UserService userService, ServiceService serviceService) {
        this.orderService = orderService;
        this.userService = userService;
        this.serviceService = serviceService;
    }

    public void index(Context ctx) {
        int userId = Integer.parseInt(Objects.requireNonNull(ctx.cookie(USER_ID)));

        List<Order> orders = orderService.findAllByUserId(userId);
        User user = userService.findById(userId)
                .orElseThrow(() -> new NotFoundResponse("User Not Found"));

        UserPage userPage = new UserPage();
        userPage.setUser(user);
        userPage.setOrders(orders);

        ctx.status(HttpStatus.OK);
        ctx.render("orders/index.jte", model(PAGE, userPage));
    }

    public void indexCreate(Context ctx) {
        BasePage basePage = new BasePage();

        String userId = ctx.cookie(USER_ID);
        Integer id = userId != null && !userId.equals("") ? Integer.parseInt(userId) : null;

        if (id != null) {
            userService.findById(id).ifPresent(user -> addUserInfoInBasePage(basePage, user));
        }
        int serviceId = Integer.parseInt(ctx.pathParam(ID));

        Service service = serviceService.findById(serviceId)
                        .orElseThrow(() -> new NotFoundResponse("Service Not Found"));
        basePage.setService(service);

        ctx.status(HttpStatus.OK);
        ctx.render("orders/create.jte", model(PAGE, basePage));
    }

    public void create(Context ctx) {
        int userId = Integer.parseInt(Objects.requireNonNull(ctx.cookie(USER_ID)));
        int serviceId = Integer.parseInt(ctx.pathParam(ID));
        LocalDateTime date = LocalDateTime.parse(ctx.formParam("date"));

        Service service = serviceService.findById(serviceId)
                .orElseThrow(() -> new NotFoundResponse("Service Not Found"));
        User user = userService.findById(userId)
                .orElseThrow(() -> new NotFoundResponse("User Not Found"));

        Order order = new Order();
        order.setUser(user);
        order.setService(service);
        order.setOrderStatus(OrderStatus.CREATED);
        order.setPrice(service.getPrice());
        order.setOrderDate(date);

        orderService.save(order);

        ctx.status(HttpStatus.CREATED);
        ctx.redirect(namedRoutes.getUserPath(userId));
    }
}
