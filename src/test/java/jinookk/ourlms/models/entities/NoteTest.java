package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.LectureTimeDto;
import jinookk.ourlms.dtos.NoteRequestDto;
import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.LectureTime;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.LectureId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class NoteTest {
    @Test
    void createdFromDto() {
        String content = "content";
        Long lectureId = 1L;
        LectureTimeDto lectureTime = new LectureTime(1, 31).toDto();

        NoteRequestDto noteRequestDto = new NoteRequestDto(content, lectureId, lectureTime);
        AccountId accountId = new AccountId(1L);

        Note note = Note.of(noteRequestDto, accountId);

        assertThat(note.id()).isEqualTo(null);
        assertThat(note.content()).isEqualTo(new Content("content"));
        assertThat(note.lectureId()).isEqualTo(new LectureId(1L));
        assertThat(note.lectureTime()).isEqualTo(new LectureTime(1, 31));
        assertThat(note.accountId()).isEqualTo(new AccountId(1L));
    }
}