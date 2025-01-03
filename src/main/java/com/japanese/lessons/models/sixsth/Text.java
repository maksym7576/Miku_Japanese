package com.japanese.lessons.models.sixsth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Text {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String kanji;

    @Column
    private String hiragana_or_katakana;

    @Column
    private String romanji;

    @Column
    private String translation;

    @JsonIgnore
    public boolean isComplete() {
        return kanji != null;
    }

    public void copyNonNullProperties(Text source) {
        if (source == null) {
            return;
        }
        if (source.getKanji() != null) {
            this.kanji = source.getKanji();
        }
        if (source.getHiragana_or_katakana() != null) {
            this.hiragana_or_katakana = source.getHiragana_or_katakana();
        }
        if (source.getRomanji() != null) {
            this.romanji = source.getRomanji();
        }
        if (source.getTranslation() != null) {
           this.translation = source.getTranslation();
        }
    }
}
