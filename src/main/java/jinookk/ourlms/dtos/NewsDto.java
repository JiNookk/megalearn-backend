package jinookk.ourlms.dtos;

import java.time.LocalDateTime;

public record NewsDto(String title,
                      LocalDateTime createdAt,
                      String content) {
}
