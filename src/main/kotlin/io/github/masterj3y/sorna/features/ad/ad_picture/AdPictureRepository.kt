package io.github.masterj3y.sorna.features.ad.ad_picture

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AdPictureRepository : CrudRepository<AdPicture, UUID>