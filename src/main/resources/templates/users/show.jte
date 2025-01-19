@import dto.UserPage
@param UserPage page

@template.layout.page(
content = @`
    <body class="min-vh-100">
    <nav class="navbar navbar-dark navbar-expand-md bg-primary py-3">
        <div class="container"><button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navcol-6"><span class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
            <div class="collapse navbar-collapse flex-grow-0 order-md-first" id="navcol-6">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item"><a class="nav-link active" href="${page.getStartPath()}">Главная</a></li>
                @if(page.getUser().getRole().equals("master"))
                    <li class="nav-item"><a class="nav-link" href="">Мои услуги</a></li>
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
                    <a class="btn btn-primary" role="button" href="${page.getEditUserPath(page.getUser().getId())}">Редактировать профиль</a>
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
        <div class="row">
            <div class="col-sm overflow-hidden">
                <div class="mb-4">
                    <div class="card py-lg-3 mt-3 mb-5">
                        <div class="card-body">
                            <div class="row g-4 justify-content-between align-items-start">
                                <div class="col-12 col-md d-flex">
                                    <div class="d-none d-md-block me-3">
                                        <picture><source type="image/webp" srcset="" loading="lazy"></picture>
                                    </div>
                                    <div class="overflow-hidden">
                                        <h1 class="h4 fw-light text-truncate"><a class="text-decoration-none text-body" href="${page.getUserPath(page.getUser().getId())}">${page.getUser().getFirstName()} ${page.getUser().getLastName()}</a></h1>
                                        <div class="text-body-secondary">
                                            <span class="d-inline-block">Обучение в группе с наставником.</span>
                                            <span class="d-inline-block">Доступ — до 28 февраля 2025</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12 col-md-auto d-flex flex-md-column flex-lg-row text-info">
                                    <div class="d-flex">
                                        <span class="bi bi-reception-4 fs-2 lh-1 me-2 h-100"></span>
                                        <div class="my-auto">
                                            <div class="h3 mb-0">2191</div>
                                            <a target="_blank" rel="noopener" title="Как начисляются баллы" class="text-body-secondary text-decoration-none text-body" href="">всего заказов</a>
                                        </div>
                                        <div class="d-flex ms-4 ms-xl-5 me-xl-3">
                                            <span class="bi bi-trophy-fill fs-3 lh-1 me-2 h-100"></span>
                                            <div class="my-auto">
                                                <div class="h3 mb-0">2125</div>
                                                <a class="text-body-secondary text-decoration-none text-body" href="/ratings">на сумму</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">

                        <div class="mb-5 pt-4">
                            <h2 class="h4 mb-4">Последнее заказы</h2>
                            <div class="row gy-3">
                                <div class="col-lg-4 col-md-6 col-12">
                                    <div class="card shadow-sm x-shadow-fade-in h-100 border-top">
                                        <div class="card-header p-1 bg-success"></div>
                                        <div class="card-body h-100 d-flex flex-column">
                                            <div class="mb-1">
                                                <div class="badge bg-body-tertiary text-body">
                                                    Основные
                                                </div>
                                            </div>
                                            <h5 class="mb-5">Трудоустройство для разработчиков</h5>
                                            <div class="text-end mt-auto">
                                                <a class="stretched-link text-decoration-none text-body align-bottom" href="/courses/employment-for-developers/goto">Повторить</a>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                                <div class="col-lg-4 col-md-6 col-12">
                                    <div class="card shadow-sm x-shadow-fade-in h-100 border-top">
                                        <div class="card-header p-1 bg-success"></div>
                                        <div class="card-body h-100 d-flex flex-column">
                                            <div class="mb-1">
                                                <div class="badge bg-body-tertiary text-body">
                                                    Основные
                                                </div>
                                            </div>
                                            <h5 class="mb-5">Основы SQL</h5>
                                            <div class="text-end mt-auto">
                                                <a class="stretched-link text-decoration-none text-body align-bottom" href="/courses/sql-basics/goto">Повторить</a>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                                <div class="col-lg-4 col-md-6 col-12">
                                    <div class="card shadow-sm x-shadow-fade-in h-100 border-top">
                                        <div class="card-header p-1 bg-success"></div>
                                        <div class="card-body h-100 d-flex flex-column">
                                            <div class="mb-1">
                                                <div class="badge bg-body-tertiary text-body">
                                                    Основные
                                                </div>
                                            </div>
                                            <h5 class="mb-5">Java: Веб-технологии</h5>
                                            <div class="text-end mt-auto">
                                                <a class="stretched-link text-decoration-none text-body align-bottom" href="/courses/java-web/goto">Повторить</a>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                                <div class="col-lg-4 col-md-6 col-12">
                                    <div class="card shadow-sm x-shadow-fade-in h-100 border-top">
                                        <div class="card-header p-1 bg-info"></div>
                                        <div class="card-body h-100 d-flex flex-column">
                                            <div class="mb-1">
                                                <div class="badge bg-body-tertiary text-body">
                                                    Основные
                                                </div>
                                            </div>
                                            <h5 class="mb-5">Bootstrap 5: Основы верстки</h5>
                                            <div class="text-end mt-auto">
                                                <a class="stretched-link text-decoration-none text-body align-bottom" href="/courses/bootstrap-basic/goto">Продолжить</a>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                                <div class="col-lg-4 col-md-6 col-12">
                                    <div class="card shadow-sm x-shadow-fade-in h-100 border-top">
                                        <div class="card-header p-1 bg-success"></div>
                                        <div class="card-body h-100 d-flex flex-column">
                                            <div class="mb-1">
                                                <div class="badge bg-body-tertiary text-body">
                                                    Испытание
                                                </div>
                                            </div>
                                            <h5 class="mb-5">Кастомные аннотации</h5>
                                            <div class="text-end mt-auto">
                                                <a class="stretched-link text-decoration-none text-body align-bottom" href="/challenges/java_spring_custom_annotations_exercise/instance">Повторить</a>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                                <div class="col-lg-4 col-md-6 col-12">
                                    <div class="card shadow-sm x-shadow-fade-in h-100 border-top">
                                        <div class="card-header p-1 bg-success"></div>
                                        <div class="card-body h-100 d-flex flex-column">
                                            <div class="mb-1">
                                                <div class="badge bg-body-tertiary text-body">
                                                    Испытание
                                                </div>
                                            </div>
                                            <h5 class="mb-5">Жизненный цикл</h5>
                                            <div class="text-end mt-auto">
                                                <a class="stretched-link text-decoration-none text-body align-bottom" href="/challenges/java_spring_lifecycle_exercise/instance">Повторить</a>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>

                        </div>



                    </div>

                </div>
            </div>
        </div>
    </div>
    </body>
`
)