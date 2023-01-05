package jinookk.ourlms.practice;

public class Numbers {
    private Integer number1;
    private Integer number2;
    private Integer number3;

    public Numbers(Integer number1, Integer number2, Integer number3) {
        this.number1 = number1;
        this.number2 = number2;
        this.number3 = number3;
    }

    public Numbers(Integer number1, Integer number2) {
        this.number1 = number1;
        this.number2 = number2;
    }

    public Integer getNumber1() {
        return number1;
    }

    public Integer getNumber2() {
        return number2;
    }

    public Integer getNumber3() {
        return number3;
    }
}
