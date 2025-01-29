@import org.example.dto.BasePage
@import org.example.model.OrderStatus
@import java.time.format.DateTimeFormatter
@param BasePage page

@template.layout.page(
content = @`
        <body class="min-vh-100">
        <nav class="navbar navbar-dark navbar-expand-md bg-dark py-3">
            <div class="container"><button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navcol-6"><span class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
                <div class="collapse navbar-collapse flex-grow-0 order-md-first" id="navcol-6">
                    <ul class="navbar-nav me-auto">
                        <li class="nav-item"><a class="nav-link active" href="${page.getStartPath()}">Главная</a></li>
                        @if(page.getUserInfo() != null && page.getUserInfo().getRole().equals("master"))
                            <li class="nav-item"><a class="nav-link" href="${page.getServicesByUserPath(page.getUserInfo().getId())}">Мои услуги</a></li>
                            <li class="nav-item"><a class="nav-link" href="">Статистика</a></li>
                        @endif
                    </ul>
                </div>
                @if(page.getUserInfo() == null)
                    <div class="d-none d-md-block">
                        <a class="btn btn-light me-2" role="button" href="${page.getRegistrationPath()}">Регистрация</a>
                        <a class="btn btn-light me-2" role="button" href="${page.getLoginUserPath()}">Войти</a>
                    </div>
                @else
                    <div class="d-none d-md-block">
                        <a class="btn btn-primary" role="button" href="${page.getUserPath(page.getUserInfo().getId())}">Мой профиль</a>
                        <a class="btn btn-primary" role="button" href="${page.getLogoutUserPath()}">Выйти</a>
                    </div>
                @endif
            </div>
        </nav>
        @if(page.getFlash() != null)
            @if(page.getFlash().equals("Необходимо авторизироваться"))
                <div class="rounded-0 m-0 alert alert-dismissible fade show alert-warning" role="alert">
                    <p class="m-0">${page.getFlash()}</p>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            @else
                <div class="rounded-0 m-0 alert alert-dismissible fade show alert-success" role="alert">
                    <p class="m-0">${page.getFlash()}</p>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            @endif
        @endif
        <div class="container-xxl mb-6">
            <div class="row">
                <div class="col-sm overflow-hidden">
                    <div class="mb-4">
                        <div class="row">
                        @if(page.getUserInfo() == null || page.getUserInfo().getRole().equals("user"))
                        <div class="mb-5 pt-4">
                            <h2 class="h4 mb-4">Мастера</h2>
                            <div class="row gy-3">
                                @for(var master : page.getMasters())
                                <div class="col-lg-4 col-md-6 col-12">
                                    <div class="card shadow-sm x-shadow-fade-in h-100 border-top">
                                        <div class="card-header p-1 bg-success"></div>
                                        <div class="card-body h-100 d-flex flex-column">
                                            <div class="mb-1">
                                                <div class="badge bg-body-tertiary text-body">
                                                    <h5 class="mb-5">${master.getFirstName()} ${master.getLastName()}</h5>
                                                </div>
                                            </div>
                                            <h5 class="mb-5"></h5>
                                            <div class="text-end mt-auto">
                                                <a class="stretched-link text-decoration-none text-body align-bottom" href="${page.getServicesByUserPath(master.getId())}">Выбрать</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                @endfor
                            </div>
                        </div>
                            @elseif(page.getUserInfo() != null && page.getUserInfo().getRole().equals("master"))
                            <div class="mb-5 pt-4">
                                <h2 class="h4 mb-4">Текущие заказы на мои услуги</h2>
                                <div class="row gy-3">
                                    @for(var order : page.getOrders())
                                        @if(order.getOrderStatus().equals(OrderStatus.CREATED))
                                        <div class="col-lg-4 col-md-6 col-12">
                                            <div class="card shadow-sm x-shadow-fade-in h-100 border-top">
                                                <div class="card-header p-1 bg-info"></div>
                                                <div class="card-body h-100 d-flex flex-column">
                                                    <div class="mb-1">
                                                        <div class="badge bg-body-tertiary text-body">
                                                            ${order.getService().getName()} - ${order.getPrice()}
                                                        </div>
                                                    </div>
                                                    <h5 class="mb-5">${order.getUser().getFirstName()} ${order.getUser().getLastName()}</h5>
                                                    <h5 class="mb-5">Запись на дату: ${order.getOrderDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))}</h5>
                                                    <div class="text-end mt-auto">
                                                        <a class="stretched-link text-decoration-none text-body align-bottom" href="">Отметить как исполнено</a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        @endif
                                    @endfor
                                </div>
                            </div>
                            @endif
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </body>
`
)