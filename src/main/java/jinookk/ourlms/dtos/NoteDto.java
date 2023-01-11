package jinookk.ourlms.dtos;

import jinookk.ourlms.models.dtos.LogDto;
import jinookk.ourlms.models.vos.Log;
import jinookk.ourlms.models.vos.ids.CourseId;

import java.time.LocalDateTime;
import java.util.List;

public class NoteDto {
    private Long id;
    private Long courseId;
    private List<LogDto> logs;
    private LocalDateTime publishTime;
    private LocalDateTime updatedAt;


    public NoteDto() {
    }

    public NoteDto(Long id, CourseId courseId, List<Log> logs, LocalDateTime publishTime, LocalDateTime updatedAt) {
        this.id = id;
        this.courseId = courseId.value();
        this.logs = logs.stream().map(Log::toDto).toList();
        this.publishTime = publishTime;
        this.updatedAt = updatedAt;
    }

    public Long getCourseId() {
        return courseId;
    }

    public Long getId() {
        return id;
    }

    public List<LogDto> getLogs() {
        return logs;
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
