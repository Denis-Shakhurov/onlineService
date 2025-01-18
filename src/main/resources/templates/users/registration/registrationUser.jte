@import dto.BasePage
@param BasePage page

@template.layout.page(
content = @`
    <main class="flax-grow-1">
        <body>
        @if( page.getFlash() != null)
            <div class="rounded-0 m-0 alert alert-dismissible fade show alert-warning" role="alert">
                <p class="m-0">${page.getFlash()}</p>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        @endif
        <nav class="navbar navbar-dark navbar-expand-md bg-dark py-3">
            <div class="container"><button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navcol-6"><span class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
                <div class="collapse navbar-collapse flex-grow-0 order-md-first" id="navcol-6">
                    <ul class="navbar-nav me-auto">
                        <li class="nav-item"><a class="nav-link active" href="${page.getStartPath()}">Главная</a></li>
                        <li class="nav-item"><a class="nav-link" href="">Статистика игр</a></li>
                    </ul>
                </div>
                <div class="d-none d-md-block">
                    <a class="btn btn-primary" role="button" href="${page.getLoginUserPath()}">Войти</a>
                </div>
            </div>
        </nav>
        <section class="py-4 py-xl-5">
            <div class="container">
                <div class="row mb-5">
                    <div class="col-md-8 col-xl-6 text-center mx-auto">
                        <h2>Регистрация</h2>
                    </div>
                </div>
                <div class="row d-flex justify-content-center">
                    <div class="col-md-6 col-xl-4">
                        <div class="card mb-5">
                            <div class="card-body d-flex flex-column align-items-center">
                                <div class="bs-icon-xl bs-icon-circle bs-icon-primary bs-icon my-4"><svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor" viewBox="0 0 16 16" class="bi bi-person">
                                        <path d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0zm4 8c0 1-1 1-1 1H3s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10z"></path>
                                    </svg></div>
                                <form class="form-horizontal" action="${page.getRegistrationUserPath()}" method="post">
                                    <div class="form-group">
                                        <label class="mb-3 control-label" for="name-input">Введи имя</label>
                                        <div class="mb-3">
                                            <input class="form-control" id="name-input" type="text" name="firstName" placeholder="не менее 4 символов">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="mb-3 control-label" for="name-input">Введи фамилию</label>
                                        <div class="mb-3">
                                            <input class="form-control" id="name-input" type="text" name="lastName" placeholder="не менее 4 символов">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="mb-3 control-label" for="name-input">Введи email</label>
                                        <div class="mb-3">
                                            <input class="form-control" type="email" name="email" placeholder="email" style="margin-bottom: 12px;">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="mb-3 control-label" for="name-input">Введи пароль</label>
                                        <div class="mb-3">
                                            <input class="form-control" type="password" name="password" placeholder="не менее 6 символов">
                                        </div>
                                    </div>
                                    <div class="mb-3"><button class="btn btn-primary d-block w-100" type="submit">Ренгистрация</button></div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        </body>
    </main>
`
)