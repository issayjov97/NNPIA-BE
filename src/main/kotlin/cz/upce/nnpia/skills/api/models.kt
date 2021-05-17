package cz.upce.nnpia.skills.api

import cz.upce.nnpia.skills.persistence.*
import java.time.LocalDateTime

data class AuthenticationRequest(
        val email: String,
        val password: String
)

data class User(
        val username: String,
        val token: String,
        val authorities: List<String>,
        val firstname: String,
        val lastname: String,
        val rating: Int,
        val available: Int,
        val earned: Int,
        val used: Int,
)

data class SignupRequest(
        val firstname: String,
        val lastname: String,
        val username: String,
        val email: String,
        val password: String
)

data class Post(
        val id: Long? = null,
        val title: String,
        val type: Type,
        val description: String,
        val details: String? = null,
        val category: Category,
        val author: Author
)

data class Author(
        val firstname: String,
        val lastname: String,
        val username: String,
        val rating: Int,
        val available: Int,
        val earned: Int,
        val used: Int
)


data class Posts(
        val totalCount: Long,
        val posts: List<Post>
)

data class Category(
        val id: Long? = null,
        val name: String? = null,
        val image: String? = null
)


data class SearchCriteria(
        val category: String? = null,
        val location: String? = null,
        val title: String? = null,
        val description: String? = null,
        val type: Type? = null
)

data class ChangePasswordRequest(
        val currentPassword: String,
        val newPassword: String
)

data class TransactionResponseApi(
        val id: Long? = null,

        val requestedAt: LocalDateTime,

        val approvedByAuthorAt: LocalDateTime? = null,

        val approvedByRequesterAt: LocalDateTime? = null,

        val deliveredByAuthorAt: LocalDateTime? = null,

        val deliveredByRequesterAt: LocalDateTime? = null,

        val agreedSkillHours: Int? = null,

        val status: TransactionState,

        val user: Author,

        val post: Post
)

data class TransactionRequestApi(
        val username: String,
        val status: TransactionState? = null
)

fun PostEntity.toPost() = Post(
        id = id,
        title = title,
        type = type,
        description = description,
        details = details,
        category = category.toCategory(),
        author = user.toAuthor()
)

fun UserEntity.toAuthor() = Author(
        firstname = firstname,
        lastname = lastname,
        rating = rating,
        earned = skillHoursEntity.earned,
        available = skillHoursEntity.available,
        used = skillHoursEntity.used,
        username = email
)

fun CategoryEntity.toCategory() = Category(
        id = id,
        name = name,
        image = image
)

fun TransactionsEntity.toTransaction() = TransactionResponseApi(
        id = id,
        requestedAt = requestedAt,
        approvedByAuthorAt = approvedByAuthorAt,
        approvedByRequesterAt = approvedByRequesterAt,
        deliveredByAuthorAt = deliveredByAuthorAt,
        deliveredByRequesterAt = deliveredByRequesterAt,
        agreedSkillHours = agreedSkillHours,
        status = status,
        user = user.toAuthor(),
        post = post.toPost()
)

fun Post.toPostEntity(
        categoryEntity: CategoryEntity,
        userEntity: UserEntity
) = PostEntity(
        title = title,
        type = type,
        description = description,
        details = details,
        category = categoryEntity,
        user = userEntity
)
