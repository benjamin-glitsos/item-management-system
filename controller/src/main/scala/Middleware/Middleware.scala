object Middleware {
    val middleware: HttpRoutes[F] => HttpApp[F] = {
        { http: HttpRoutes[F] =>
            AutoSlash(http)
        } andThen { http: HttpRoutes[F] =>
            HttpsRedirect(http)
        }
    }
}

