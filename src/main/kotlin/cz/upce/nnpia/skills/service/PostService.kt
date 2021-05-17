package cz.upce.nnpia.skills.service

import cz.upce.nnpia.skills.api.Post
import cz.upce.nnpia.skills.api.Posts
import cz.upce.nnpia.skills.api.SearchCriteria
import cz.upce.nnpia.skills.persistence.Type
import org.springframework.security.core.Authentication

interface PostService {
    fun createPost(post: Post)
    fun updatePost(post: Post)
    fun deletePost(postId: Long)
    fun getPosts(page: Int, size: Int, searchCriteria: SearchCriteria?, authentication: Authentication): Posts
    fun getPosts(type: Type): List<Post>
    fun getPosts(email: String): List<Post>
    fun getPost(postId: Long): Post
}