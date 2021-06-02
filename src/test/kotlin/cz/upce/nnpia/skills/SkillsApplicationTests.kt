package cz.upce.nnpia.skills

import cz.upce.nnpia.skills.persistence.UserRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest()
class SkillsApplicationTests
{
	@Autowired
	lateinit var repo: UserRepository
	@Test
	fun contextLoads() {
		println(repo.findByUsername("jovkhar.issayev@addai.cz")?.email)
	}

}
