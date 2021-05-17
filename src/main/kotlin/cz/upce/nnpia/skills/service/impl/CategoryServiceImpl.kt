package cz.upce.nnpia.skills.service.impl

import cz.upce.nnpia.skills.api.Category
import cz.upce.nnpia.skills.api.Posts
import cz.upce.nnpia.skills.persistence.CategoryRepository
import cz.upce.nnpia.skills.persistence.SkillHoursEntity
import cz.upce.nnpia.skills.persistence.SkillHoursRepository
import cz.upce.nnpia.skills.persistence.UserRepository
import cz.upce.nnpia.skills.service.CategoryService
import cz.upce.nnpia.skills.service.UserService
import cz.upce.nnpia.skills.util.SkillAppException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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