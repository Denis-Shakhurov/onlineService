@import dto.BasePage
@param BasePage page

@template.layout.page(
content = @`
    <body class="min-vh-100">
    <nav class="navbar navbar-dark navbar-expand-md bg-dark py-3">
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
                <div class="card py-lg-3 mt-3 mb-5">
                    <div class="card-body">
                        <div class="row g-4 justify-content-between align-items-start">
                            <div class="col-12 col-md d-flex">
                                <div class="d-none d-md-block me-3">
                                    <picture><source type="image/webp" srcset="" loading="lazy"></picture>
                                </div>
                                <div class="overflow-hidden">
                                    <h1 class="h4 fw-light text-truncate"><a class="text-decoration-none text-body" href="">${page.getService().getName()}</a></h1>
                                    <div class="text-body-secondary">
                                        <span class="d-inline-block">${page.getService().getDescription()}</span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-12 col-md-auto d-flex flex-md-column flex-lg-row text-info">
                                <div class="d-flex">
                                    <span class="bi bi-reception-4 fs-2 lh-1 me-2 h-100"></span>
                                    <div class="my-auto">
                                        <div class="h3 mb-0">${page.getService().getPrice()}</div>
                                        <a target="_blank" rel="noopener" title="Как начисляются баллы" class="text-body-secondary text-decoration-none text-body" href="">стоимость</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="card shadow mb-3">
                        <div class="card-header py-3">
                            <p class="text-primary m-0 fw-bold">Редактирование услуги</p>
                        </div>
                        <div class="card-body">
                            <form action="${page.getEditServicesPath(page.getService().getId())}" method="post">
                                <div class="row">
                                    <div class="col">
                                        <div class="mb-3"><label class="form-label" for="name"><strong>Название</strong></label><input class="form-control" type="text" id="name" placeholder="${page.getService().getName()}" name="name"></div>
                                    </div>
                                    <div class="col">
                                        <div class="mb-3"><label class="form-label" for="price"><strong>Стоимость услуги</strong></label><input class="form-control" type="text" id="price" placeholder="${page.getService().getPrice()}" name="price"></div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        <div class="mb-3"><label class="form-label" for="description"><strong>Орисание</strong></label><input class="form-control" type="text" id="description" placeholder="${page.getService().getDescription()}" name="description"></div>
                                    </div>
                                </div>
                                <div class="mb-3"><button class="btn btn-primary btn-sm" type="submit">Сохранить</button></div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </body>
`
)