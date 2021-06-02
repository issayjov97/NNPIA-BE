package cz.upce.nnpia.skills.api

import cz.upce.nnpia.skills.persistence.*
import org.springframework.web.multipart.MultipartFile
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
        val used: Int
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
        val title: String? = null,
        val type: Type? = null,
        val description: String? = null,
        val details: String? = null,
        val category: Category? = null,
        val author: Author? = null
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

data class ChangeUserInfoRequest(
        val firstname: String? = null,
        val lastname: String? = null,
        val location: String? = null
)

data class ChangePasswordRequest(
        val currentPassword: String,
        val newPassword: String
)


data class TransactionResponseApi(
        val id: Long? = null,

        val requestedAt: LocalDateTime? = null,

        val approvedByAuthorAt: LocalDateTime? = null,

        val approvedByRequesterAt: LocalDateTime? = null,

        val deliveredByAuthorAt: LocalDateTime? = null,

        val deliveredByRequesterAt: LocalDateTime? = null,

        val agreedSkillHours: Int? = null,

        val status: TransactionState? = null,

        val user: Author? = null,

        val post: Post? = null
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