package com.saboo;

public class TimeTableModel {
    String teacher;
    String subject;
    String time;

    public TimeTableModel() {
    }

    public TimeTableModel(String teacher, String subject, String time) {
        this.teacher = teacher;
        this.subject = subject;
        this.time = time;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
