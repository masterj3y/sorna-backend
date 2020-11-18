package io.github.masterj3y.sorna.features.ad.category

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/category")
class CategoryController
@Autowired
constructor(private val service: CategoryService) {

    @PostMapping
    fun postCategory(category: Category): Category = service.save(category)

    @GetMapping
    fun getAllCategories(): MutableIterable<Category> = service.allCategories()

    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable("id") id: UUID) = service.deleteById(id)
}