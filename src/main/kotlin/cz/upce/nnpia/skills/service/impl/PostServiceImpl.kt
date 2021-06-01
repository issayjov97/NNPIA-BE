package cz.upce.nnpia.skills.service.impl

import cz.upce.nnpia.skills.api.Post
import cz.upce.nnpia.skills.api.Posts
import cz.upce.nnpia.skills.api.SearchCriteria
import cz.upce.nnpia.skills.api.toPost
import cz.upce.nnpia.skills.persistence.*
import cz.upce.nnpia.skills.service.PostService
import cz.upce.nnpia.skills.util.SkillAppException
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
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
        val userEntity = userRepository.findByEmail(post.author!!.username)
                ?: throw SkillAppException("User was not found", HttpStatus.NOT_FOUND)
        val categoryEntity = categoryRepository.findByName(post.category?.name!!);
        postRepository.save(
                PostEntity(
                        title = post.title!!,
                        type = post.type!!,
                        description = post.description!!,
                        details = post.details,
                        category = categoryEntity,
                        user = userEntity
                )
        )
    }

    override fun updatePost(id: Long, post: Post) {
        val postEntity = postRepository.findById(id).orElseThrow {
            throw SkillAppException("Post does not exist", HttpStatus.NOT_FOUND)
        }
        post.title?.let {
            postEntity.title = it
        }
        post.description?.let {
            postEntity.description = it
        }
        post.details?.let {
            postEntity.details = it
        }
        postRepository.saveAndFlush(postEntity)
    }

    override fun deletePost(postId: Long) = postRepository.deleteById(postId)

    @Transactional(readOnly = true)
    override fun getPosts(
            page: Int,
            size: Int,
            searchCriteria: SearchCriteria?,
            authentication: Authentication
    ) = postRepository.findAll(PostSpecification(searchCriteria, authentication.principal as String), PageRequest.of(page, size)).let {
        val posts = Posts(
                totalCount = it.totalElements,
                posts = it.content.map { it.toPost() }
        )
        posts
    }

    override fun getPosts(email: String) = postRepository.findByUsername(email).map { it.toPost() }

    @Transactional(readOnly = true)
    override fun getPost(postId: Long) = postRepository.findById(postId).map {
        it.toPost()
    }.orElseThrow { throw SkillAppException("Nothing was found", HttpStatus.NOT_FOUND) }
}


