package com.example.demoauthentication.user

import com.example.demoauthentication.exception.NoResultException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController {

    private var userList: List<UserModel> = mutableListOf(
            UserModel(1, "Eduardo Brandes"),
            UserModel(2, "Andriell Aragão Salvador")
    )

    @GetMapping("/")
    fun getUser() = userList

    @GetMapping("/{id}")
    fun getUserById(@PathVariable("id") id: String): UserModel? {
        val user = userList.firstOrNull { it.id == Integer.parseInt(id) }
        return user ?: throw NoResultException("Usuário não encontrado")
    }

}