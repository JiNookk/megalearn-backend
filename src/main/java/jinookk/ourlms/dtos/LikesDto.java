package jinookk.ourlms.dtos;

import java.util.List;

public class LikesDto {
    private List<LikeDto> likes;

    public LikesDto() {
    }

    public LikesDto(List<LikeDto> likes) {
        this.likes = likes;
    }

    public List<LikeDto> getLikes() {
        return likes;
    }
}
