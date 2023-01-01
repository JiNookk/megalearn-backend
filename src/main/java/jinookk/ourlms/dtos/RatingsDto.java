package jinookk.ourlms.dtos;

import java.util.List;

public class RatingsDto {
    private List<RatingDto> ratings;

    public RatingsDto() {
    }

    public RatingsDto(List<RatingDto> ratings) {
        this.ratings = ratings;
    }

    public List<RatingDto> getRatings() {
        return ratings;
    }
}
