package com.opentext.dmg.target.eventlistener.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HipChatMessageCard {

    private String style;
    private String url;
    private String format;
    private String id;
    private String title;
    private String description;
    private CardIcon icon;
    private CardAttribute[] attributes;

    @Setter
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public class CardIcon {
        private String url;
    }

    @Setter
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public class CardAttribute {
        private String label;
        private CardAttributeValue value;
    }

    @Setter
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public class CardAttributeValue {
        private String label;
        private String style;
    }
}