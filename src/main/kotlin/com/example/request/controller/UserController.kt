package com.example.request.controller

import com.example.request.model.JSendResponse
import com.example.request.model.User
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController {

    // Simulación de una base de datos
    private val users = mutableMapOf<Int, User>()

    // GET para obtener un usuario por ID
    @GetMapping("/{id}")
    fun getUser(@PathVariable("id") id: Int): JSendResponse<Any> {
        val user = users[id]
        return if (user != null) {
            // Respuesta JSend en caso de éxito
            JSendResponse(
                status = "success",
                data = user,
                message = "User retrieved successfully"
            )
        } else {
            // Respuesta JSend en caso de fallo, devolviendo un mapa
            JSendResponse(
                status = "fail",
                data = mapOf("id" to "User not found"),
                message = "No user found with ID $id"
            )
        }
    }

    // POST para crear un nuevo usuario
    @PostMapping()
    fun createUser(@RequestBody user: User): JSendResponse<Any> {
        // Validación del correo electrónico
        if (!isValidEmail(user.email)) {
            return JSendResponse(
                status = "fail",
                data = mapOf("email" to "Invalid email format"),
                message = "The email provided is not valid"
            )
        }

        // Simular la creación del usuario
        if (users.containsKey(user.id)) {
            // Si el ID ya existe, enviar respuesta de fallo, devolviendo un mapa
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

    // DELETE para eliminar un usuario por ID
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable("id") id: Int): JSendResponse<Any> {
        val user = users.remove(id)
        return if (user != null) {
            // Respuesta JSend en caso de éxito
            JSendResponse(
                status = "success",
                data = null,
                message = "User deleted successfully"
            )
        } else {
            // Respuesta JSend en caso de fallo, devolviendo un mapa
            JSendResponse(
                status = "fail",
                data = mapOf("id" to "User not found"),
                message = "No user found with ID $id"
            )
        }
    }

    // Simulación de un error interno
    @GetMapping("/error")
    fun generateError(): JSendResponse<Any> {
        return JSendResponse(
            status = "error",
            message = "An internal server error occurred"
        )
    }

    // Método para validar el formato del correo electrónico
    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$".toRegex()
        return emailRegex.matches(email)
    }
}
