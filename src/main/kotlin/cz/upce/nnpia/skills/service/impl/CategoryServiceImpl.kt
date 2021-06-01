package cz.upce.nnpia.skills.service.impl

import cz.upce.nnpia.skills.api.Category
import cz.upce.nnpia.skills.persistence.CategoryRepository
import cz.upce.nnpia.skills.service.CategoryService
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl(
        val categoryRepository: CategoryRepository
) : CategoryService {
    override fun getAll(): List<Category> = categoryRepository.findAll().map {
        Category(
                id = it.id,
                name = it.name,
                image = it.image
        )
    }
}