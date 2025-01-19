@import dto.BasePage
@param BasePage page

@template.layout.page(
content = @`
<%--    <main class="flax-grow-1">--%>
    <body class="min-vh-100">
    <nav class="navbar navbar-dark navbar-expand-md bg-primary py-3">
        <div class="container"><button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navcol-6"><span class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
            <div class="collapse navbar-collapse flex-grow-0 order-md-first" id="navcol-6">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item"><a class="nav-link active" href="${page.getStartPath()}">Главная</a></li>
                    @if(page.getUserInfo().getRole().equals("master"))
                        <li class="nav-item"><a class="nav-link" href="${page.getServicesByUserPath(page.getUserInfo().getId())}">Мои услуги</a></li>
                        <li class="nav-item"><a class="nav-link" href="${page.getServicesPath()}">Добавить услугу</a></li>
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
        <div class="rounded-0 m-0 alert alert-dismissible fade show alert-success" role="alert">
            <p class="m-0">${page.getFlash()}</p>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    @endif
    <div class="container-xxl mb-6">
        <div class="col-lg-8">
            <div class="row">
                <div class="col">
                    <div class="card shadow mb-3">
                        <div class="card-header py-3">
                            <p class="text-primary m-0 fw-bold">Добавление услуги</p>
                        </div>
                        <div class="card-body">
                            <form action="${page.getServicesPath()}" method="post">
                                <div class="row">
                                    <div class="col">
                                        <div class="mb-3"><label class="form-label" for="name"><strong>Название</strong></label><input class="form-control" type="text" id="name" placeholder="" name="name"></div>
                                    </div>
                                    <div class="col">
                                        <div class="mb-3"><label class="form-label" for="description"><strong>Орисание</strong></label><input class="form-control" type="text" id="description" placeholder="" name="description"></div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        <div class="mb-3"><label class="form-label" for="price"><strong>Стоимость услуги</strong></label><input class="form-control" type="text" id="price" placeholder="" name="price"></div>
                                    </div>
                                </div>
                                <div class="mb-3"><button class="btn btn-primary btn-sm" type="submit">Добавить</button></div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </body>
<%--    </main>--%>
`
)