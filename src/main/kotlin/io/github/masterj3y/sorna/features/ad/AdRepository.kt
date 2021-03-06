package io.github.masterj3y.sorna.features.ad

import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AdRepository : CrudRepository<Ad, UUID> {

    @Query("SELECT *, EXISTS(SELECT * FROM saved_ad saved JOIN application_user u ON u.id = saved.user_id AND saved.ad_id = ad.id WHERE u.id = :userId) AS saved FROM ad ORDER BY created_at DESC", nativeQuery = true)
    fun findAndLikes(@Param("userId") userId: UUID = UUID.randomUUID()): MutableIterable<Ad>

    fun findAllByCategoryId(categoryId: UUID): MutableIterable<Ad>

    @Query("SELECT *, EXISTS(SELECT * FROM saved_ad saved JOIN application_user u ON u.id = saved.user_id AND saved.ad_id = ad.id WHERE u.id = :userId) AS saved FROM ad WHERE user_id = :userId ORDER BY created_at DESC", nativeQuery = true)
    fun getUserAds(@Param("userId") userId: UUID): MutableIterable<Ad>

    @Query("SELECT id, user_id ,category_id, title, description, phone_number, price, created_at, 'true' AS saved FROM ad WHERE EXISTS(SELECT * FROM saved_ad saved JOIN application_user u ON u.id = saved.user_id AND saved.ad_id = ad.id WHERE u.id = :userId) = true ORDER BY created_at DESC", nativeQuery = true)
    fun getUserSavedAds(@Param("userId") userId: UUID): MutableIterable<Ad>

    @Query("SELECT *, EXISTS(SELECT * FROM saved_ad saved JOIN application_user u ON u.id = saved.user_id AND saved.ad_id = ad.id WHERE u.id = :userId) AS saved FROM ad WHERE LOWER(title) LIKE LOWER(CONCAT('%',:keyword,'%')) OR LOWER(description) LIKE LOWER(CONCAT('%',:keyword,'%')) ORDER BY created_at DESC;", nativeQuery = true)
    fun searchAds(
            @Param("userId") userId: UUID = UUID.randomUUID(),
            @Param("keyword") keyword: String): MutableIterable<Ad>

    @Modifying
    @Query("DELETE FROM ad WHERE user_id = :userId AND id = :adId", nativeQuery = true)
    fun deleteAdById(@Param("userId") userId: UUID, @Param("adId") adId: UUID)
}