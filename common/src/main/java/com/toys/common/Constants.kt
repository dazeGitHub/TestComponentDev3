package com.toys.common

object Constants {
    const val ROUTER_SCHEME = "dm://"

    object RouterPath {
        const val LOGIN = ROUTER_SCHEME + "login/login"
        const val MEMBER = ROUTER_SCHEME + "member/member"

        const val LOGIN_TEST_PATH = "$LOGIN?username=user1&pwd=pwd1&id=1"
    }
}