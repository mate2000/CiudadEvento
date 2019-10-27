package com.example.appciudadpromocionaeventos.Class;


public class UserServiceProvider {

    private String user;
    private String identificationCard;
    private String email;
    private String fullName;
    private String cellphone;
    private boolean block;
    private String Rut;
    private String namePic;

    public UserServiceProvider(String user, String identificationCard, String email,
                               String fullName, String cellphone, boolean block, String Rut, String namePic) {
        setUser(user);
        setIdentificationCard(identificationCard);
        setEmail(email);
        setFullName(fullName);
        setCellphone(cellphone);
        setBlock(block);
        setRut(Rut);
        setNamePic(namePic);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getIdentificationCard() {
        return identificationCard;
    }

    public void setIdentificationCard(String identificationCard) {
        this.identificationCard = identificationCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public String getRut() {
        return Rut;
    }

    public void setRut(String Rut) {
        this.Rut = Rut;
    }

    public String getNamePic() {
        return namePic;
    }

    public void setNamePic(String namePic) {
        this.namePic = namePic;
    }
}
