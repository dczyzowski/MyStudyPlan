package com.agh.mystudyplan;

public class MySubject {

    String startHour;
    String endHour;
    String subject;
    String subjectType;

    MySubject(String startHour, String endHour, String subject, String subjectType){
        this.startHour = startHour;
        this.endHour = endHour;
        this.subject = subject;
        this.subjectType = subjectType;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public String getEndHour() {
        return endHour;
    }

    public String getStartHour() {
        return startHour;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
