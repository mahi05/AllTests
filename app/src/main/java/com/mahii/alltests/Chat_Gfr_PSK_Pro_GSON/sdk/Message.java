package com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.sdk;

import java.util.List;

//TODO: Message response listener class for reading from Message API
@SuppressWarnings("unused")
public class Message {
    public String messageId;
    public int userId;
    public String parentMessageId;
    public String previousMessageId;
    public String text;
    public String createTimestamp;
    public String userFeedbackCorrectFlags;
    public String goferReplyText;
    public int sourceId;
    public int typeId;
    public List<MessageResponse> messageResponseList;
    public MessageIntent messageIntent;
    public List<MessageEntity> messageEntityList;
}