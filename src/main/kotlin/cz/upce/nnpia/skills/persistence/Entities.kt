package cz.upce.nnpia.skills.persistence

import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
class UserEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        val username: String,
        var password: String,
        val enabled: Boolean = true,
        val firstname: String,
        val lastname: String,
        val email: String,
        val rating: Int = 0,
        @OneToOne(cascade = [CascadeType.ALL],
                fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "skill_hours_id")
        val skillHoursEntity: SkillHoursEntity,

        @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
        @JoinColumn(name = "user_id")
        val authorities: Set<AuthorityEntity> = mutableSetOf()


)

@Entity
@Table(name = "authorities")
class AuthorityEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        @Column(name = "authority")
        val authority: String
)

@Entity
@Table(name = "posts")
class PostEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        val title: String,
        @Enumerated(EnumType.STRING)
        val type: Type,
        val description: String,
        val details: String? = null,
        @ManyToOne
        @JoinColumn(name = "category_id")
        val category: CategoryEntity,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        val user: UserEntity

)

@Entity
@Table(name = "categories")
class CategoryEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,
        val name: String,
        val image: String,
        @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = [CascadeType.ALL], orphanRemoval = true)
        val posts: MutableList<PostEntity> = mutableListOf()
)

@Entity
@Table(name = "skill_hours")
class SkillHoursEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        val available: Int = 0,
        val earned: Int = 0,
        val used: Int = 0
)

@Entity
@Table(name = "transactions")
data class TransactionsEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @Column(name = "requested_at")
        val requestedAt: LocalDateTime,

        @Column(name = "approved_by_author_at")
        var approvedByAuthorAt: LocalDateTime? = null,

        @Column(name = "approved_by_requester_at")
        var approvedByRequesterAt: LocalDateTime? = null,

        @Column(name = "delivered_by_author_at")
        var deliveredByAuthorAt: LocalDateTime? = null,

        @Column(name = "delivered_by_requester_at")
        var deliveredByRequesterAt: LocalDateTime? = null,

        @Column(name = "agreed_skill_hours")
        var agreedSkillHours: Int? = null,

        @Column(name = "status")
        @Enumerated(EnumType.STRING)
        var status: TransactionState,

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "author")
        val user: UserEntity,
        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "post_id")
        val post: PostEntity
)


@Entity
@Table(name = "wishlist")
data class WishListEntity(
        @EmbeddedId
        val id: WishListId

)

@Embeddable
data class WishListId(
        @Column(name = "user_id")
        val userId: Long,
        @ManyToOne
        @JoinColumn(name = "post_id")
        val post: PostEntity
) : Serializable

enum class Type {
    SKILLS,
    NEEDS
}

enum class TransactionState {
    REQUESTED,
    APPROVED,
    ACTIVE,
    DELIVERED
}