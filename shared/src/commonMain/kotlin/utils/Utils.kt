package utils

fun <T, Y> Result<T>.toModelResult(converter: (T) -> Y): Result<Y> {
    this.onSuccess {
        return Result.success(converter(it))
    }.onFailure {
        return Result.failure<Y>(it)
    }
    return Result.failure<Y>(Exception("Something went wrong"))
}

fun Throwable.getMessage(): String {
    return this.message ?: "Something went wrong"
}
