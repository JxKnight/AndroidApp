package com.example.chua.gogreen;

public class ReceiptStorageData {

    private String ReceiptId;
    private String SendDate;
    private String SenderId;

    public ReceiptStorageData(String ReceiptId, String SendDate, String SenderId) {
        this.ReceiptId = ReceiptId;
        this.SendDate = SendDate;
        this.SenderId = SenderId;
    }

    public String getSendDate() {
        return SendDate;
    }

    public void setSendDate(String SendDate) {
        this.SendDate = SendDate;
    }

    public String getReceiptId() {
        return ReceiptId;
    }

    public void setReceiptId(String ReceiptId) {
        this.ReceiptId = ReceiptId;
    }

    public String getSenderId() {
        return SenderId;
    }

    public void setSenderId(String SenderId) {
        this.SenderId = SenderId;
    }
}
