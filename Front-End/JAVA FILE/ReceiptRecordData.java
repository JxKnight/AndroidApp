package com.example.chua.gogreen;

public class ReceiptRecordData {

    private String ReceiptId;
    private String ReceiverId;
    private String tvDate;

    public ReceiptRecordData(String ReceiptId, String ReceiverId, String tvDate) {
        this.ReceiptId = ReceiptId;
        this.ReceiverId = ReceiverId;
        this.tvDate = tvDate;
    }

    public String getReceiptId() {
        return ReceiptId;
    }

    public void setReceiptId(String ReceiptId) {
        this.ReceiptId = ReceiptId;
    }

    public String getReceiverId() {
        return ReceiverId;
    }

    public void setReceiverId(String ReceiverId) {
        this.ReceiverId = ReceiverId;
    }

    public String gettvDate() {
        return tvDate;
    }

    public void settvDate(String tvDate) {
        this.tvDate = tvDate;
    }

}
