package io.github.masterj3y.sorna.features.user

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class ApplicationUser(
        @Id
        val id: UUID = UUID.randomUUID(),
        @Column(name = "first_name", length = 32)
        val firstName: String,
        @Column(name = "last_name", length = 32)
        val lastName: String,
        @Column(name = "profile_pic", length = 22)
        val profilePic: String,
        @Column(name = "google_id")
        val googleId: String,
        @Column(name = "email")
        val email: String
)