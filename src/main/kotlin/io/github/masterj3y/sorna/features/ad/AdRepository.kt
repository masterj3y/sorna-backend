package io.github.masterj3y.sorna.features.ad

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AdRepository : CrudRepository<Ad, UUID> {

    @Query("SELECT id, title, description, create_at, EXISTS(SELECT * FROM saved_ad saved JOIN users u ON u.id = saved.user_id AND saved.ad_id = ad.id WHERE u.id = :userId ) AS liked FROM advertisement ad;", nativeQuery = true)
    fun findAndLikes(@Param("userId") userId: UUID = UUID.randomUUID()): MutableIterable<Ad>
}