package com.ozack.todoapp.exception;

/* データの登録に失敗した際に発生する例外 */
public class InsertException extends TodoAppException {
    
    public InsertException(String message) {
        super(message);
    }

    public InsertException(String message, Throwable course) {
        super(message, course);
    }

}
