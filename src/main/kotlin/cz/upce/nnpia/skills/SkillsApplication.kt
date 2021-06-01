package cz.upce.nnpia.skills

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
class SkillsApplication

fun main(args: Array<String>) {
    runApplication<SkillsApplication>(*args)
}
