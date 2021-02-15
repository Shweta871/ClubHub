package com.nucleus.events.clubhub.Model;

public class Club_request_model {

String ccollage,cname,clogo,ccatagory;

    public Club_request_model() {
    }

    public Club_request_model(String ccollage, String cname, String clogo, String ccatagory) {
        this.ccollage = ccollage;
        this.cname = cname;
        this.clogo = clogo;
        this.ccatagory = ccatagory;
    }

    public String getCcollage() {
        return ccollage;
    }

    public void setCcollage(String ccollage) {
        this.ccollage = ccollage;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getClogo() {
        return clogo;
    }

    public void setClogo(String clogo) {
        this.clogo = clogo;
    }

    public String getCcatagory() {
        return ccatagory;
    }

    public void setCcatagory(String ccatagory) {
        this.ccatagory = ccatagory;
    }
}
