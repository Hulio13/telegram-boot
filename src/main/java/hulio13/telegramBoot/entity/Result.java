package hulio13.telegramBoot.entity;

import java.util.Objects;

public final class Result<T> {
    private final boolean isSuccess;
    private final T obj;
    private final String error;
    private final String message;

    public Result(boolean isSuccess, T obj, String error, String message) {
        this.isSuccess = isSuccess;
        this.obj = obj;
        this.error = error;
        this.message = message;
    }

    /**
     * Use if result is successful.
     */
    public Result(T obj) {
        this(true, obj, "", "");
    }

    /**
     * Use if result is not successful.
     */
    public Result(String error, String message) {
        this(false, null, error, message);
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public T object() {
        return obj;
    }

    public String error() {
        return error;
    }

    public String message() {
        return message;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Result<T>) obj;
        return this.isSuccess == that.isSuccess &&
                Objects.equals(this.obj, that.obj) &&
                Objects.equals(this.error, that.error) &&
                Objects.equals(this.message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isSuccess, obj, error, message);
    }

    @Override
    public String toString() {
        return "Result[" +
                "isSuccess=" + isSuccess + ", " +
                "obj=" + obj + ", " +
                "error=" + error + ", " +
                "message=" + message + ']';
    }
}
