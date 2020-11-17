package io.github.masterj3y.sorna.features.ad.saved_ad

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class SavedAd(
        @Id
        val id: UUID = UUID.randomUUID(),
        @Column(name = "user_id")
        val userId: UUID,
        @Column(name = "ad_id")
        val adId: UUID
)