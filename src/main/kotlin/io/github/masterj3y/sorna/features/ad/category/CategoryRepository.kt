package io.github.masterj3y.sorna.features.ad.category

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CategoryRepository : CrudRepository<Category, UUID>