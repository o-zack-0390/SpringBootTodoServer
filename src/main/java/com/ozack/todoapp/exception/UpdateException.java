package com.ozack.todoapp.exception;

/* データの更新に失敗した際に発生する例外 */
public class UpdateException extends TodoAppException {
    
    public UpdateException(String message) {
        super(message);
    }

    public UpdateException(String message, Throwable course) {
        super(message, course);
    }

}
