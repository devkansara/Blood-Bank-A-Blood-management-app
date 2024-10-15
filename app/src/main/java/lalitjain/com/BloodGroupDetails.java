package lalitjain.com;


public class BloodGroupDetails
{
    private String A_negative, A_positive, B_negative, B_positive, AB_negative, AB_positive, O_negative, O_positive;

    public BloodGroupDetails()
    {

    }

    public BloodGroupDetails(String a_negative, String a_positive, String b_negative,
                             String b_positive, String AB_negative, String AB_positive, String o_negative, String o_positive) {
        A_negative = a_negative;
        A_positive = a_positive;
        B_negative = b_negative;
        B_positive = b_positive;
        this.AB_negative = AB_negative;
        this.AB_positive = AB_positive;
        O_negative = o_negative;
        O_positive = o_positive;
    }

    public String getA_negative() {
        return A_negative;
    }

    public void setA_negative(String a_negative) {
        A_negative = a_negative;
    }

    public String getA_positive() {
        return A_positive;
    }

    public void setA_positive(String a_positive) {
        A_positive = a_positive;
    }

    public String getB_negative() {
        return B_negative;
    }

    public void setB_negative(String b_negative) {
        B_negative = b_negative;
    }

    public String getB_positive() {
        return B_positive;
    }

    public void setB_positive(String b_positive) {
        B_positive = b_positive;
    }

    public String getAB_negative() {
        return AB_negative;
    }

    public void setAB_negative(String AB_negative) {
        this.AB_negative = AB_negative;
    }

    public String getAB_positive() {
        return AB_positive;
    }

    public void setAB_positive(String AB_positive) {
        this.AB_positive = AB_positive;
    }

    public String getO_negative() {
        return O_negative;
    }

    public void setO_negative(String o_negative) {
        O_negative = o_negative;
    }

    public String getO_positive() {
        return O_positive;
    }

    public void setO_positive(String o_positive) {
        O_positive = o_positive;
    }

    @Override
    public String toString() {
        return "BloodGroupDetails{" +
                "A_negative='" + A_negative + '\'' +
                ", A_positive='" + A_positive + '\'' +
                ", B_negative='" + B_negative + '\'' +
                ", B_positive='" + B_positive + '\'' +
                ", AB_negative='" + AB_negative + '\'' +
                ", AB_positive='" + AB_positive + '\'' +
                ", O_negative='" + O_negative + '\'' +
                ", O_positive='" + O_positive + '\'' +
                '}';
    }
}