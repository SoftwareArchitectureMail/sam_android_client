package com.sam.teamd.samandroidclient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by david on 10/10/17.
 */

public class Mail implements Serializable {

    private String id;
    private String recipient;
    private String sender;
    private String cc;

    @SerializedName("distribution_list")
    private String distributionList;
    private String subject;

    @SerializedName("message_body")
    private String messageBody;


    @Expose(serialize = false, deserialize = false)
    private transient String attachment;

    @SerializedName("sent_date")
    private Date sentDate;

    @Expose(serialize = false, deserialize = false)
    private boolean hasAttachment;

    private boolean read;
    private boolean draft;
    private boolean urgent;
    private boolean confirmation;


    public Mail(String recipient, String sender, String cc, String subject, String messageBody,
                Date sentDate, boolean draft, boolean urgent, boolean confirmation) {
        this.recipient = recipient;
        this.sender = sender;
        this.cc = cc;
        this.subject = subject;
        this.messageBody = messageBody;
        this.sentDate = sentDate;
        this.draft = draft;
        this.urgent = urgent;
        this.confirmation = confirmation;
    }

    public Mail(String recipient, String sender, String cc, String subject, String messageBody,
                boolean draft, boolean urgent, boolean confirmation) {
        this.recipient = recipient;
        this.sender = sender;
        this.cc = cc;
        this.subject = subject;
        this.messageBody = messageBody;
        this.draft = draft;
        this.urgent = urgent;
        this.confirmation = confirmation;
    }



    public Mail(String id, String sender, String subject, String messageBody, String attachment,
                Date sentDate, boolean urgent) {
        this.id = id;
        this.sender = sender;
        this.subject = subject;
        this.messageBody = messageBody;
        this.attachment = attachment;
        this.sentDate = sentDate;
        this.urgent = urgent;
    }

    public Mail(String id, String sender, String subject, String messageBody, String attachment,
                Date sentDate, boolean read, boolean urgent) {
        this.id = id;
        this.sender = sender;
        this.subject = subject;
        this.messageBody = messageBody;
        this.attachment = attachment;
        this.sentDate = sentDate;
        this.read = read;
        this.urgent = urgent;
    }

    public String getCc() {
        return cc;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public String getId() {
        return id;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSender() {
        return sender;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public String getAttachment() {
        return attachment;
    }

    public boolean isHasAttachment() {
        return hasAttachment;
    }

    public boolean isDraft() {
        return draft;
    }

    public boolean isUrgent() {
        return urgent;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
