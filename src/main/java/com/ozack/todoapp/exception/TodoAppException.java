package com.ozack.todoapp.exception;

/* アプリケーション固有のエラーに対する例外処理を集約するクラス */
public class TodoAppException extends Exception {
    
    public TodoAppException(String message) {
        super(message);
    }

    public TodoAppException(String message, Throwable course) {
        super(message, course);
    }

}
