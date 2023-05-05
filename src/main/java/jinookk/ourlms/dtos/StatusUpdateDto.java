package jinookk.ourlms.dtos;

public class StatusUpdateDto {
    private String status;

    public StatusUpdateDto() {
    }

    public StatusUpdateDto(String status) {
        this.status = status.toUpperCase();
    }

    public String getStatus() {
        return status;
    }
}
