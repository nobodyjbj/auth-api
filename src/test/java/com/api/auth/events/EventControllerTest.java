package com.api.auth.events;

import com.api.auth.common.BaseControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EventControllerTest extends BaseControllerTest {
    
    @Test
    void createEvent() throws Exception {
        // given
        Event event = Event.builder()
                .id(10)
                .name("spring")
                .description("spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2020, 06, 07, 12, 12))
                .closeEnrollmentDateTime(LocalDateTime.of(2020, 06, 07, 12, 12))
                .beginEventDateTime(LocalDateTime.of(2020, 06, 07, 12, 12))
                .endEventDateTime(LocalDateTime.of(2020, 06, 07, 12, 12))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("저 세상 강의실 A1")
                .build();
        
        // when & then test
        mockMvc.perform(post("/api/events/")
                    .contentType(MediaTypes.HAL_JSON_VALUE)
                    .accept(MediaTypes.HAL_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(event)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("id").exists())
            .andExpect(header().exists(HttpHeaders.LOCATION))
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE));
    }
}
