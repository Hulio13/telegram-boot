package hulio13.telegramBoot.entity

data class Result<T>(val isSuccess: Boolean = true, val obj: T? = null, val error: String = "",
                     val message: String = "") {
    constructor(obj: T?) : this(obj = obj, isSuccess = true)
    constructor(error: String, message: String)
            : this(isSuccess = false, error = error, message = message)
}
