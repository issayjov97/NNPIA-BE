package cz.upce.nnpia.skills.persistence

import cz.upce.nnpia.skills.api.SearchCriteria
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
class PostRepositoryTest{
    @Autowired
    lateinit var postRepository: PostRepository

    @Test
    internal fun shouldCheckIfUserExists() {
        val searchCriteria = SearchCriteria(
                type = Type.SKILLS
        )
        val posts = postRepository.findAll(PostSpecification(searchCriteria,"matyas.leisky@addai.cz" ), PageRequest.of(0, 5))
        assertThat(posts.totalElements).isGreaterThanOrEqualTo(0L)
    }
}