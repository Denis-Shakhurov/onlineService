@import org.example.dto.BasePage
@param BasePage page

@template.layout.page(
content = @`
    <main class="flax-grow-1">
        <body>
        <nav class="navbar navbar-dark navbar-expand-md bg-dark py-3">
            <div class="container"><button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navcol-6"><span class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
                <div class="collapse navbar-collapse flex-grow-0 order-md-first" id="navcol-6">
                    <ul class="navbar-nav me-auto">
                        <li class="nav-item"><a class="nav-link active" href="${page.getStartPath()}">Главная</a></li>
                    </ul>
                </div>
                <div class="d-none d-md-block">
                    <a class="btn btn-light me-2" role="button" href="${page.getLoginUserPath()}">Войти</a>
                </div>
            </div>
        </nav>
        @if( page.getFlash() != null)
            <div class="rounded-0 m-0 alert alert-dismissible fade show alert-warning" role="alert">
                <p class="m-0">${page.getFlash()}</p>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        @endif
        <section class="py-4 py-xl-5">
            <div class="container">
                <div class="row mb-5">
                    <div class="col-md-8 col-xl-6 text-center mx-auto">
                        <h2>Зарегистрироваться как:  </h2>
                        <div class="d-none d-md-block">
                            <a class="btn btn-light me-2" role="button" href="${page.getRegistrationUserPath()}">Пользователь</a>
                            <a class="btn btn-light me-2" role="button" href="${page.getRegistrationMasterPath()}">Мастер</a>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        </body>
    </main>
`
)