package io.github.masterj3y.sorna.features.ad.ad_picture

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class AdPicture(
        @Id
        val id: UUID = UUID.randomUUID(),
        @Column(name = "ad_id")
        val adId: UUID,
        @Column(name = "pic_url", length = 20)
        val picUrl: String
)