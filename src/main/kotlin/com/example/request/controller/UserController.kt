package com.example.request.controller

import com.example.request.model.JSendResponse
import com.example.request.model.User
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController {

    private val users = mutableMapOf<Int, User>()

    @GetMapping("/{id}")
    fun getUser(@PathVariable("id") id: Int): JSendResponse<Any> {
        val user = users[id]
        return if (user != null) {
            JSendResponse(
                status = "success",
                data = user,
                message = "User retrieved successfully"
            )
        } else {
            JSendResponse(
                status = "fail",
                data = mapOf("id" to "User not found"),
                message = "No user found with ID $id"
            )
        }
    }
    @PostMapping()
    fun createUser(@RequestBody user: User): JSendResponse<Any> {
        if (!isValidEmail(user.email)) {
            return JSendResponse(
                status = "fail",
                data = mapOf("email" to "Invalid email format"),
                message = "The email provided is not valid"
            )
        }
        if (users.containsKey(user.id)) {
            return JSendResponse(
                status = "fail",
                data = mapOf("id" to "User ID already exists"),
                message = "User with ID ${user.id} already exists"
            )
        }

        users[user.id] = user

        return JSendResponse(
            status = "success",
            data = user,
            message = "User created successfully"
        )
    }
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable("id") id: Int): JSendResponse<Any> {
        val user = users.remove(id)
        return if (user != null) {
            JSendResponse(
                status = "success",
                data = null,
                message = "User deleted successfully"
            )
        } else {
            JSendResponse(
                status = "fail",
                data = mapOf("id" to "User not found"),
                message = "No user found with ID $id"
            )
        }
    }
    @GetMapping("/error")
    fun generateError(): JSendResponse<Any> {
        return JSendResponse(
            status = "error",
            message = "An internal server error occurred"
        )
    }


    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$".toRegex()
        return emailRegex.matches(email)
    }
}
