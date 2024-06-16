package app.models;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Map;

@Entity
public class TipText {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(length = 36)
    @JsonView(view.Id.class)
    private String uuid;

    @Enumerated(EnumType.STRING)
    private TipType tip_type;

    @Enumerated(EnumType.STRING)
    private TipTextPart tip_text_part;

    @Column(columnDefinition = "text")
    private String text;

    public TipText() {
    }

    public TipText(String text) {
        this.text = text;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public TipType getTip_type() {
        return tip_type;
    }

    public void setTip_type(TipType tip_type){
        this.tip_type = tip_type;
    }

    public TipTextPart getTip_text_part() {
        return tip_text_part;
    }

    public void setTip_text_part(TipTextPart tip_text_part) {
        this.tip_text_part = tip_text_part;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextWithReplacedPlaceholders(Map<String, String> placeholder_mapping){
        String text_template = this.text;
        for (Map.Entry<String, String> entry : placeholder_mapping.entrySet()) {
            text_template = text_template.replaceAll("\\{" + entry.getKey() + "\\}", entry.getValue());
        }
        return text_template;
    }
}
