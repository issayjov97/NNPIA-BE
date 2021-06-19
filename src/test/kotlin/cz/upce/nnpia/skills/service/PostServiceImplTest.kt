package cz.upce.nnpia.skills.service

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import cz.upce.nnpia.skills.api.*
import cz.upce.nnpia.skills.persistence.*
import cz.upce.nnpia.skills.service.impl.PostServiceImpl
import io.mockk.every
import io.mockk.mockkStatic
import org.assertj.core.api.Assertions
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.context.ActiveProfiles
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@SpringBootTest
@ActiveProfiles("test")
internal class PostServiceImplTest {
    @MockBean
    lateinit var postRepository: PostRepository

    @MockBean
    lateinit var categoryRepository: CategoryRepository

    @MockBean
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var posrService: PostServiceImpl

    @Test
    fun testCreatePost() {
        val userEntity = UserEntity(
            username = "tester",
            firstname = "tester",
            lastname = "tester",
            password = "root",
            email = "tester@upce.cz",
            skillHoursEntity = SkillHoursEntity()
        )
        val categoryEntity = CategoryEntity(id = 1, name = "IT", image = "../default.png")
        val postEntity = PostEntity(
            title = "Spring boot",
            type = Type.SKILLS,
            description = "Java programming for beginners",
            details = "JSE",
            category = categoryEntity,
            user = userEntity
        )
        mockkStatic(CategoryEntity::toCategory)
        every {
            categoryEntity.toCategory()
        } returns Category(
            id = 1,
            name = "IT",
            image = "../default.png"
        )
        mockkStatic(UserEntity::toAuthor)
        every {
            userEntity.toAuthor()
        } returns Author(
            username = "tester",
            firstname = "tester",
            lastname = "tester",
            earned = 0,
            available = 0,
            rating = 0,
            used = 0
        )
        mockkStatic(PostEntity::toPost)
        every {
            postEntity.toPost()
        } returns Post(
            title = "Spring boot",
            type = Type.SKILLS,
            description = "Java programming for beginners",
            details = "JSE",
            category = categoryEntity.toCategory(),
            author = userEntity.toAuthor()
        )
        Mockito.lenient().`when`(userRepository.findByEmail(Mockito.anyString())).thenReturn(userEntity)
        Mockito.lenient().`when`(categoryRepository.findByName((Mockito.anyString()))).thenReturn(categoryEntity)
        Mockito.lenient().`when`(postRepository.save(postEntity)).thenReturn(postEntity)

        posrService.createPost(postEntity.toPost())

        verify(userRepository, times(1)).findByEmail(Mockito.anyString())
        verify(categoryRepository, times(1)).findByName(any())
        verify(postRepository, times(1)).save(any())
    }

    @Test
    fun testGetPosts() {
        val content = listOf(
            PostEntity(
                title = "Spring boot",
                type = Type.SKILLS,
                description = "Java programming for beginners",
                details = "JSE",
                category = CategoryEntity(id = 1, name = "IT", image = "../default.png"),
                user = UserEntity(
                    username = "tester",
                    firstname = "tester",
                    lastname = "tester",
                    password = "root",
                    email = "tester@upce.cz",
                    skillHoursEntity = SkillHoursEntity()
                )
            ),
            PostEntity(
                title = "Elasticsearch",
                type = Type.NEEDS,
                description = "Angular for begginers",
                details = "FE for BE developers",
                category = CategoryEntity(id = 2, name = "IT", image = "../default.png"),
                user = UserEntity(
                    username = "tester",
                    firstname = "tester",
                    lastname = "tester",
                    password = "root",
                    email = "tester@upce.cz",
                    skillHoursEntity = SkillHoursEntity()
                )
            )
        )
        val page =
            Mockito.lenient().`when`(
                postRepository.findAll(
                    Mockito.any(PostSpecification::class.java),
                    Mockito.any(PageRequest::class.java),
                )
            ).thenReturn(PageImpl(content, PageRequest.of(0, 2), 2))
        val posts = posrService.getPosts(
            0,
            2,
            SearchCriteria(category = "IT"),
            UsernamePasswordAuthenticationToken("tesrert@upce.cz", null)
        )
        assertEquals(2, posts.posts.size)
        assertTrue {  posts.posts.all{it.category?.name == "IT"}}

    }
}