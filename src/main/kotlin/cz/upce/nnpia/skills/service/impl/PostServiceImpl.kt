package cz.upce.nnpia.skills.service.impl

import cz.upce.nnpia.skills.api.*
import cz.upce.nnpia.skills.persistence.*
import cz.upce.nnpia.skills.service.PostService
import cz.upce.nnpia.skills.util.SkillAppException
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostServiceImpl(
        private val postRepository: PostRepository,
        private val categoryRepository: CategoryRepository,
        private val userRepository: UserRepository
) : PostService {
    override fun createPost(post: Post) {
        val userEntity = userRepository.findByEmail(post.author.username)//TODO zmenit client id
                ?: throw SkillAppException("User not found")
        val categoryEntity = categoryRepository.findByName(post.category.name!!);
        postRepository.save(
                post.toPostEntity(categoryEntity, userEntity)
        )
    }

    override fun updatePost(post: Post) {
        TODO("Not yet implemented")
    }

    override fun deletePost(postId: Long) = postRepository.deleteById(postId)

    @Transactional(readOnly = true)
    override fun getPosts(
            page: Int,
            size: Int,
            searchCriteria: SearchCriteria?,
            authentication: Authentication
    ) = postRepository.findAll(PostSpecification(searchCriteria, authentication.name), PageRequest.of(page, size)).let {
                println(authentication.authorities)
                val posts = Posts(
                        totalCount = it.totalElements,
                        posts = it.content.map { it.toPost() }
                )
                posts
            }


    override fun getPosts(type: Type) = postRepository.findByType(type)?.map { it.toPost() }
            ?: throw SkillAppException("Nothing was found")

    override fun getPosts(email: String) = postRepository.findByUsername(email).map { it.toPost() }


    @Transactional(readOnly = true)
    override fun getPost(postId: Long) = postRepository.findById(postId).map {
        it.toPost()
    }.orElseThrow { throw SkillAppException("Nothing was found") }
}


