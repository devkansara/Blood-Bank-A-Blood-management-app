package lalitjain.com;

public class Donor {
    private String name,email,phone,aadhar,bloodamt,bg;

    public Donor(String name, String email, String phone, String aadhar, String bloodamt, String bg) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.aadhar = aadhar;
        this.bloodamt = bloodamt;
        this.bg = bg;
    }

    public Donor()
    {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getBloodamt() {
        return bloodamt;
    }

    public void setBloodamt(String bloodamt) {
        this.bloodamt = bloodamt;
    }

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }
    public String toString()
    {
        String data= name  + " : " + email + " : " + aadhar + phone +":" + bloodamt +":"+bg;
        return data;
    }
}
