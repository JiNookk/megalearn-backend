package jinookk.ourlms.dtos;

public class InquiryStatusDto {
    private String value;
    private String replied;
    private String solved;

    public InquiryStatusDto() {
    }

    public InquiryStatusDto(String value, String replied, String solved) {
        this.value = value;
        this.replied = replied;
        this.solved = solved;
    }

    public String getValue() {
        return value;
    }

    public String getReplied() {
        return replied;
    }

    public String getSolved() {
        return solved;
    }
}
