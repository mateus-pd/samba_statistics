package com.mbhosts

class RestInterceptor {

    RestInterceptor() {
        matchAll().excludes(controller: "application")
    }

    boolean before() {
        if (!validateToken(request.getHeader("Authorization"))){
            response.status = 401
            return false
        }
        return true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }

    private static boolean validateToken(String token) {
        try {
            if (!token) return false

            token = token.replace("Basic ", "")
            def authHeader = new String(token.decodeBase64())
            def credentials = authHeader.split(":")

            if (!credentials || credentials.size() != 2) return false


            def username = credentials[0]
            def password = credentials[1]

            def user = new User()

            if (user.user == username && user.pass == password) return true

            return false
        }
        catch (Exception ex) { return false }
    }

}