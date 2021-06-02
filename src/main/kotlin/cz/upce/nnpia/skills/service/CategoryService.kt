package cz.upce.nnpia.skills.service

import cz.upce.nnpia.skills.api.Category

interface CategoryService {
    fun getAll(): List<Category>
}