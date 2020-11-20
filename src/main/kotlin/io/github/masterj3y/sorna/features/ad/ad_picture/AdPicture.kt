package io.github.masterj3y.sorna.features.ad.ad_picture

import io.github.masterj3y.sorna.features.ad.Ad
import java.util.*
import javax.persistence.*

@Entity
data class AdPicture(
        @Id
        val id: UUID = UUID.randomUUID(),
        @Column(name = "pic_url", length = 20)
        val picUrl: String,
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "ad_id", nullable = false)
        private val ad: Ad
)