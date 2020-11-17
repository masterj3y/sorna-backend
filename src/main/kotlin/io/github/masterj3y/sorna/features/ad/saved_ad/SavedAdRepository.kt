package io.github.masterj3y.sorna.features.ad.saved_ad

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SavedAdRepository : CrudRepository<SavedAd, UUID>