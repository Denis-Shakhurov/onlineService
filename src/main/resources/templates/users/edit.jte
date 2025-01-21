@import dto.BasePage
@param BasePage page

@template.layout.page(
content = @`
    <main class="flax-grow-1">
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
                        <a class="btn btn-light me-2" role="button" href="">Войти</a>
                    </div>
                @else
                    <div class="d-none d-md-block">
                        <a class="btn btn-primary" role="button" href="${page.getUserPath(page.getUserInfo().getId())}">Мой профиль</a>
                        <a class="btn btn-primary" role="button" href="">Выйти</a>
                    </div>
                @endif
            </div>
        </nav>
        @if(page.getFlash() != null)
            <div class="rounded-0 m-0 alert alert-dismissible fade show alert-warning" role="alert">
                <p class="m-0">${page.getFlash()}</p>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        @endif
        <section>
            <div class="container-lg mt-5">
                <h1>${page.getUserInfo().getFirstName()}</h1>
                <form class="form-horizontal" action="${page.getEditUserPath(page.getUserInfo().getId())}" method="post">
                    <div class="form-group">
                        <label class="mb-3 control-label" for="name-input">Новое имя</label>
                        <div class="mb-3">
                            <input class="form-control" id="name-input" type="text" name="firstName" placeholder="${page.getUserInfo().getFirstName()}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="mb-3 control-label" for="name-input">Новая фамилию</label>
                        <div class="mb-3">
                            <input class="form-control" id="name-input" type="text" name="lastName" placeholder="${page.getUserInfo().getLastName()}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="mb-3 control-label" for="name-input">Новый email</label>
                        <div class="mb-3">
                            <input class="form-control" type="email" name="email" placeholder="${page.getUserInfo().getEmail()}" style="margin-bottom: 12px;">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="mb-3 control-label" for="name-input">Введи пароль</label>
                        <div class="mb-3">
                            <input class="form-control" type="password" name="password" placeholder="">
                        </div>
                    </div>
                    <div class="mb-3"><button class="btn btn-primary d-block w-100" type="submit">Сохранить</button></div>
                </form>
            </div>
        </section>
    </main>
`
)