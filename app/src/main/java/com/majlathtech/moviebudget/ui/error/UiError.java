package com.majlathtech.moviebudget.ui.error;

import androidx.annotation.StringRes;

public class UiError {
    Type type;
    String text;
    int textId;
    String code;

    public UiError(Type type) {
        this.type = type;
    }

    public UiError(Type type, String text) {
        this.type = type;
        this.text = text;
    }

    public UiError(Type type, String text, String code) {
        this.type = type;
        this.text = text;
        this.code = code;
    }

    public UiError(Type type, @StringRes int textId, String code) {
        this.type = type;
        this.textId = textId;
        this.code = code;
    }

    public Type getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public int getTextId() {
        return textId;
    }

    public String getCode() {
        return code;
    }

    public enum Type {
        Network,
        General,
        ErrorWithCode
    }
}
