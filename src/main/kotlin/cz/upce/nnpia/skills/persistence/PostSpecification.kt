package cz.upce.nnpia.skills.persistence

import cz.upce.nnpia.skills.api.SearchCriteria
import org.springframework.data.jpa.domain.Specification
import org.springframework.security.core.Authentication
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

class PostSpecification(
        private val searchCriteria: SearchCriteria?,
        private val email: String
) : Specification<PostEntity> {
    override fun toPredicate(root: Root<PostEntity>, cq: CriteriaQuery<*>, cb: CriteriaBuilder): Predicate? {
        val predicates = mutableListOf<Predicate>()

        searchCriteria?.let {
            it.location?.let {
                predicates.add(
                        cb.equal(root.get<String>("location"), it)
                )
            }
            it.description?.let {
                predicates.add(
                        cb.like(root.get("description"), "%$it%")
                )
            }
            it.title?.let {
                predicates.add(
                        cb.like(root.get("title"), "%$it%")
                )
            }
            it.type?.let {
                predicates.add(
                        cb.equal(root.get<Type>("type"), it)
                )
            }
            it.category?.let {
                val categoryJoin = root.join<PostEntity, CategoryEntity>("category")
                predicates.add(
                        cb.equal(categoryJoin.get<String>("name"), it)
                )
            }
            val userJoin = root.join<PostEntity, UserEntity>("user")
            predicates.add(
                    cb.notEqual(userJoin.get<String>("email"),email )
            )
        }
        return cb.and(*predicates.toTypedArray())
    }
}

