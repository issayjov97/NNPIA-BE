package cz.upce.nnpia.skills.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import cz.upce.nnpia.skills.SpringSecurityTestConfig
import cz.upce.nnpia.skills.api.Category
import cz.upce.nnpia.skills.api.Post
import cz.upce.nnpia.skills.api.SearchCriteria
import cz.upce.nnpia.skills.api.toAuthor
import cz.upce.nnpia.skills.persistence.Type
import cz.upce.nnpia.skills.persistence.UserRepository
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import  org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest(classes = [SpringSecurityTestConfig::class],
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@AutoConfigureMockMvc
@ActiveProfiles("test")
class PostControllerTest(

) {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    @WithMockUser("testt@test.cz",roles = ["ADMIN"])
    internal fun getPost() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts/{postId}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("English"))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
    }

    @Test
    @WithUserDetails("testt@test.cz")
    internal fun createPost() {
        val post = Post(
                title = "Test",
                type = Type.NEEDS,
                author = userRepository.findByUsername("user1")!!.toAuthor(),
                description = "Test",
                category = Category(
                        name = "IT"
                )
        )
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/posts/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString
                                (
                                        Post(
                                                title = "Test",
                                                type = Type.NEEDS,
                                                author = userRepository.findByUsername("user1")!!.toAuthor(),
                                                description = "Test",
                                                category = Category(
                                                        name = "IT"
                                                )
                                        )
                                )
                        )
        )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andDo { MockMvcResultHandlers.print() }
    }


    @Test
    @WithUserDetails("testt@test.cz")
    internal fun getPosts() {
        val searchCriteria = SearchCriteria(
                type = Type.NEEDS
        )
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/posts/search", 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("page", "0")
                        .param("size", "5")
                        .content(objectMapper.writeValueAsString(searchCriteria))
        )
                .andExpect(
                        MockMvcResultMatchers.jsonPath(("$.posts.length()")).value(5)
                )
                .andExpect(
                        MockMvcResultMatchers.status().isOk
                )
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.posts[*].type",
                        Matchers.containsInAnyOrder(Type.NEEDS.name, Type.NEEDS.name, Type.NEEDS.name, Type.NEEDS.name, Type.NEEDS.name))
                )

                .andExpect(
                        MockMvcResultMatchers.content().contentType("application/json")
                )
    }
}