package io.github.masterj3y.sorna.features.ad.category

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class CategoryService
@Autowired
constructor(private val repository: CategoryRepository) {

    fun save(category: Category): Category = repository.save(category)

    fun allCategories(): MutableIterable<Category> = repository.findAll()

    fun deleteById(id: UUID) = repository.deleteById(id)
}