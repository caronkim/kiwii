package org.example.kiwii.dto;

public class CheolHwanApiResponse<T> {
    private int status;  // HTTP 상태 코드
    private String message; // 응답 메시지
    private T data; // 실제 데이터 (제네릭)

    public CheolHwanApiResponse() {
    }

    public CheolHwanApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
    public CheolHwanApiResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}