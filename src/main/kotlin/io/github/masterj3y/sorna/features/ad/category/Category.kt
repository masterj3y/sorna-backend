package io.github.masterj3y.sorna.features.ad.category

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Category(
        @Id
        val id: UUID = UUID.randomUUID(),
        @Column(name = "title", length = 32)
        val title: String
)