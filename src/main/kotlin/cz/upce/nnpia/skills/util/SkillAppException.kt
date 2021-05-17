package cz.upce.nnpia.skills.util

class SkillAppException: RuntimeException {
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}