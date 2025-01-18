@import dto.UserPage
@param UserPage page

@template.layout.page(
content = @`
    <main class="flax-grow-1">
        <nav class="navbar navbar-dark navbar-expand-md bg-dark py-3">
            <div class="container"><button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navcol-6"><span class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
                <div class="collapse navbar-collapse flex-grow-0 order-md-first" id="navcol-6">
                    <ul class="navbar-nav me-auto">
                        <li class="nav-item"><a class="nav-link active" href="${page.getStartPath()}">Главная</a></li>
                        <li class="nav-item"><a class="nav-link" href="">Статистика игр</a></li>
                    </ul>
                </div>
                @if(page.getUserInfo() == null)
                    <div class="d-none d-md-block">
                        <a class="btn btn-light me-2" role="button" href="${page.getRegistrationPath()}">Регистрация</a>
                        <a class="btn btn-primary" role="button" href="${page.getLoginUserPath()}">Войти</a>
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
        <section>
            <div class="container-lg mt-5">
                <h1>${page.getUser().getFirstName()}</h1>
                @if(page.getUserInfo() != null)
                    <h1>${page.getUserInfo().getRole()}</h1>
                @else
                    <h1>${page.getUserInfo().getRole()}</h1>
                @endif
            </div>
        </section>
    </main>
`
)