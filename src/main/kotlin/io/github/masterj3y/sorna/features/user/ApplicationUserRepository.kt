package io.github.masterj3y.sorna.features.user

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ApplicationUserRepository : CrudRepository<ApplicationUser, UUID>