package com.example.chua.gogreen;

public class ReceiptInboxData {

    private String receiptID;
    private String sendDate;
    private String senderID;

    public ReceiptInboxData(String receiptID, String senderID, String sendDate) {
        this.receiptID = receiptID;
        this.sendDate = sendDate;
        this.senderID = senderID;
    }

    public String getsendDate() {
        return sendDate;
    }

    public void setsendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getreceiptID() {
        return receiptID;
    }

    public void setreceiptID(String receiptID) {
        this.receiptID = receiptID;
    }

    public String getsenderID() {
        return senderID;
    }

    public void setsenderID(String senderID) {
        this.senderID = senderID;
    }

}
