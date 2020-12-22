package io.github.masterj3y.sorna.features.ad.saved_ad

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface SavedAdRepository : CrudRepository<SavedAd, UUID> {

    @Transactional
    fun deleteByUserIdAndAdId(userId: UUID, adId: UUID)
}