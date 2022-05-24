package cz.upce.nnpia.skills.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root


interface UserRepository : JpaRepository<UserEntity, Long> {
    @Query("from UserEntity user " +
            "left join fetch user.authorities " +
            "left join fetch user.skillHoursEntity " +
            "where user.username = ?1")
    fun findByUsername(username: String): UserEntity?

    fun findByEmail(email: String): UserEntity?

}

interface PostRepository : JpaRepository<PostEntity, Long>, JpaSpecificationExecutor<PostEntity> {
    fun findByType(type: Type): List<PostEntity>?

    @Query("from PostEntity p" +
            " inner join p.category c" +
            " inner join p.user u" +
            " where u.email = ?1")
    fun findByUsername(username: String): List<PostEntity>
}

interface CategoryRepository : JpaRepository<CategoryEntity, Long> {
    fun findByName(name: String): CategoryEntity
}

interface SkillHoursRepository : JpaRepository<SkillHoursEntity, Long> {
}

interface TransactionRepository : JpaRepository<TransactionsEntity, Long>, CustomTransactionRepository


interface CustomTransactionRepository {
    fun findTransactions(username: String, status: TransactionState?): List<TransactionsEntity>
}

interface WishListRepository : JpaRepository<WishListEntity, WishListId>{
    fun findByIdUserId(userId: Long): List<WishListEntity>
}

@Repository
class CustomTransactionRepositoryImpl(
        private val entityManager: EntityManager
) : CustomTransactionRepository {
    override fun findTransactions(username: String, status: TransactionState?): List<TransactionsEntity> {
        val criteriaBuilder = entityManager.criteriaBuilder
        val cq = criteriaBuilder.createQuery(TransactionsEntity::class.java)
        val root = cq.from(TransactionsEntity::class.java)
        val predicates = createPredicates(criteriaBuilder, root, username, status)
        cq.where(*predicates.toTypedArray())
        return entityManager.createQuery(cq).resultList;
    }

    private fun createPredicates(
            cb: CriteriaBuilder,
            root: Root<*>,
            username: String,
            status: TransactionState?
    ): List<Predicate> {
        val predicates = mutableListOf<Predicate>();
        val userPredicate = cb.equal(
                root.join<TransactionsEntity, UserEntity>("user").get<String>("email"), username
        )
        val postPredicate = cb.equal(
                root.join<TransactionsEntity, PostEntity>("post").get<UserEntity>("user").get<String>("email"), username
        )
        status?.let {
            when (it) {
                TransactionState.DELIVERED -> {
                    predicates.add(cb.and(cb.or(userPredicate, postPredicate), cb.equal(root.get<TransactionState>("status"), it)))
                }
                else -> null
            }

        } ?: run {
            predicates.add(cb.or(userPredicate, postPredicate))
        }
        return predicates
    }

}
