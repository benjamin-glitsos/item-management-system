object UsersDAO
    extends UsersListDAO
    with UsersOpenDAO
    with UsersCreateDAO
    with UsersEditDAO
    with UsersDeleteDAO
    with UsersAuthenticateDAO {}
