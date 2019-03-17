package com.saboo;

public class DoubtModal {
    private String id;
    private boolean solved;
    private String text;

    public DoubtModal() {
    }

    public DoubtModal(String id, boolean solved, String text) {
        this.id = id;
        this.solved = solved;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
