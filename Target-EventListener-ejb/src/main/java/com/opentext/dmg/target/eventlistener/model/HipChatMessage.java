package com.opentext.dmg.target.eventlistener.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@ToString
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HipChatMessage {

    @JsonProperty("color")
    private String color;
    @JsonProperty("message")
    private String message;
    @JsonProperty("notify")
    private boolean notify;
    @JsonProperty("message_format")
    private String messageFormat;
    @JsonProperty("card")
    private HipChatMessageCard card;

    private HipChatMessage(String message, String color, String messageFormat, boolean notify, HipChatMessageCard
            card) {
        this.message = message;
        this.color = color;
        this.notify = notify;
        this.messageFormat = messageFormat;
        this.card = card;
    }

    public static class HipChatMessageBuilder {
        private String color = "gray";
        private String messageFormat = "text";
        private boolean notify = true;
        private String style = "application";
        private String url = "http://assethub.target.com";
        private String iconUrl = "https://saml.pf.target.com/assets/resources/images/responsive-sign-in.png";
        private String format = "medium";
        private String id = UUID.randomUUID().toString();
        private String title;
        private String description;
        private String priority = "High";
        private String status = "Open";
        private HipChatMessageCard.CardIcon icon;
        private String priorityLabel = "Priority";
        private String statusLabel = "Status";
        private String statusStyle = "lozenge-complete";

        public HipChatMessageBuilder(String title) {
            this.title = title;
        }

        public HipChatMessageBuilder color(String color) {
            this.color = color;
            return this;
        }

        public HipChatMessageBuilder notify(boolean notify) {
            this.notify = notify;
            return this;
        }

        public HipChatMessageBuilder style(String style) {
            this.style = style;
            return this;
        }

        public HipChatMessageBuilder url(String url) {
            this.url = url;
            return this;
        }

        public HipChatMessageBuilder iconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
            return this;
        }

        public HipChatMessageBuilder format(String format) {
            this.format = format;
            return this;
        }

        public HipChatMessageBuilder id(String id) {
            this.id = id;
            return this;
        }

        public HipChatMessageBuilder priority(String priority) {
            this.priority = priority;
            return this;
        }

        public HipChatMessageBuilder status(String status) {
            this.status = status;
            return this;
        }

        public HipChatMessageBuilder description(String description) {
            this.description = description;
            return this;
        }

        public HipChatMessageBuilder messageFormat(String messageFormat) {
            this.messageFormat = messageFormat;
            return this;
        }

        public HipChatMessageBuilder priorityLabel(String priorityLabel) {
            this.priorityLabel = priorityLabel;
            return this;
        }

        public HipChatMessageBuilder statusStyle(String statusStyle) {
            this.statusStyle = statusStyle;
            return this;
        }

        public HipChatMessageBuilder statusLabel(String statusLabel) {
            this.statusLabel = statusLabel;
            return this;
        }

        public HipChatMessage build() {
            HipChatMessageCard card = new HipChatMessageCard();
            card.setStyle(style);
            card.setUrl(url);
            card.setDescription(description);
            card.setTitle(title);
            card.setFormat(format);
            card.setId(id);
            icon = card.new CardIcon();
            icon.setUrl(iconUrl);
            card.setIcon(icon);
            HipChatMessageCard.CardAttribute priorityAttr = card.new CardAttribute();
            priorityAttr.setLabel(priorityLabel);
            HipChatMessageCard.CardAttributeValue priorityAttrVal = card.new CardAttributeValue();
            priorityAttrVal.setLabel(priority);
            priorityAttr.setValue(priorityAttrVal);

            HipChatMessageCard.CardAttribute statusAttr = card.new CardAttribute();
            statusAttr.setLabel(statusLabel);
            HipChatMessageCard.CardAttributeValue statusAttrVal = card.new CardAttributeValue();
            statusAttrVal.setLabel(status);
            statusAttrVal.setStyle(statusStyle);
            statusAttr.setValue(statusAttrVal);

            HipChatMessageCard.CardAttribute[] attributes = {priorityAttr, statusAttr};
            card.setAttributes(attributes);
            return new HipChatMessage(title, color, messageFormat, notify, card);
        }
    }
}