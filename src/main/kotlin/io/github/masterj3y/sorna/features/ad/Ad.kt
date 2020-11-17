package io.github.masterj3y.sorna.features.ad

import io.github.masterj3y.sorna.features.ad.ad_picture.AdPicture
import java.util.*
import javax.persistence.*

@Entity
data class Ad(
        @Id
        val id: UUID = UUID.randomUUID(),
        @Column(name = "user_id")
        val userId: UUID,
        @Column(name = "category_id")
        val categoryId: UUID,
        @Column(name = "title", length = 50)
        val title: String,
        @Column(name = "description", length = 512)
        val description: String,
        @Column(name = "created_at")
        val createdAt: Long,
        @Column(insertable = false, updatable = false)
        val liked: Boolean = false
) {
        @OneToMany(mappedBy = "ad", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        var pics: Set<AdPicture> = setOf()
}