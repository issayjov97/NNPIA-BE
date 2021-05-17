package cz.upce.nnpia.skills.controller

import cz.upce.nnpia.skills.api.Post
import cz.upce.nnpia.skills.api.SearchCriteria
import cz.upce.nnpia.skills.service.PostService
import org.springframework.http.HttpStatus
import org.springframework.security.access.annotation.Secured
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*


@CrossOrigin
@RestController
@RequestMapping("api/v1/posts")
class PostController(
        val postService: PostService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    fun createPost(
            @RequestBody post: Post
    ) {
        postService.createPost(post)
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updatePost(
            @RequestBody post: Post,
            authentication: Authentication
    ) {

    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePost(
            @PathVariable postId: Long,
            authentication: Authentication
    ) {

    }

    @PostMapping("/search")
    @ResponseStatus(HttpStatus.OK)
   // @Secured("ROLE_ADMIN")
    fun getPosts(
            @RequestParam page: Int,
            @RequestParam size: Int,
            @RequestBody searchCriteria: SearchCriteria?,
            authentication: Authentication
    ) = postService.getPosts(page, size, searchCriteria, authentication)

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getPosts(
            @RequestParam username: String,
            authentication: Authentication
    ) = postService.getPosts(username)


    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    fun getPost(@PathVariable postId: Long): Post {
        return this.postService.getPost(postId)
    }

}