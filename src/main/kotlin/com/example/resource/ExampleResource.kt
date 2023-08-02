package com.example.resource

import io.quarkus.hibernate.reactive.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheEntityBase
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepositoryBase
import io.smallrye.mutiny.coroutines.awaitSuspending
import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.*
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path

@Path("/user")
class ExampleResource(
    private val userRepository: UserRepository
) {

    @GET
    @Path("/{id}")
    suspend fun getSingle(id: Long): User {
        return userRepository.getById(id).awaitSuspending()
    }

}

@ApplicationScoped
class UserRepository : PanacheRepositoryBase<User, Long> {
    fun getById(id: Long) = findById(id)
}

@Entity
@Cacheable
class User : PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null

    @Column(length = 25, unique = true)
    lateinit var name: String

    companion object : PanacheCompanion<User>
}
