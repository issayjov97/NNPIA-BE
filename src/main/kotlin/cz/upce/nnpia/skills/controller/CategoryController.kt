package cz.upce.nnpia.skills.controller

import cz.upce.nnpia.skills.service.CategoryService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@CrossOrigin
@RestController
@RequestMapping("/api/v1/categories")
class CategoryController(
        val categoryService: CategoryService
) {
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    fun getAll() = categoryService.getAll()
}