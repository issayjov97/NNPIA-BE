package cz.upce.nnpia.skills.controller

import cz.upce.nnpia.skills.api.Post
import cz.upce.nnpia.skills.api.SearchCriteria
import cz.upce.nnpia.skills.service.PostService
import org.springframework.http.HttpStatus
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
    ) = postService.createPost(post)


    @PutMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updatePost(
            @PathVariable postId: Long,
            @RequestBody post: Post,
            authentication: Authentication
    ) = postService.updatePost(postId, post)


    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePost(
            @PathVariable postId: Long,
            authentication: Authentication
    ) = postService.deletePost(postId)


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
    fun getPost(@PathVariable postId: Long) = postService.getPost(postId)

}