package lalitjain.com;

public class Hospital {
    private String un,name,city,pw;

public Hospital(){

 }

 public Hospital(String un,String name,String city,String pw)
 {
     this.un = un;
     this.name=name;
     this.city=city;
     this.pw=pw;

 }

    public String getUn() {
        return un;
    }

    public void setUn(String un) {
        this.un = un;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String toString()
    {
        String data= un  + " : " + name + " : " + city;
        return data;
    }
}
