package com.ozack.todoapp.exception;

/* データの削除に失敗した際に発生する例外 */
public class DeleteException extends TodoAppException {
    
    public DeleteException(String message) {
        super(message);
    }

    public DeleteException(String message, Throwable course) {
        super(message, course);
    }

}
