package com.api.auth.events;

import com.api.auth.common.BaseControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EventControllerTest extends BaseControllerTest {
    
    @Test
    void createEvent() throws Exception {
        // given
        EventDto eventDto = EventDto.builder()
                .name("spring")
                .description("spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2020, 06, 07, 12, 12))
                .closeEnrollmentDateTime(LocalDateTime.of(2020, 06, 07, 12, 12))
                .beginEventDateTime(LocalDateTime.of(2020, 06, 07, 12, 12))
                .endEventDateTime(LocalDateTime.of(2020, 06, 07, 12, 12))
                .location("저 세상 강의실 A1")
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .build();
        
        // when & then
        mockMvc.perform(post("/api/events/")
                    .contentType(MediaTypes.HAL_JSON_VALUE)
                    .accept(MediaTypes.HAL_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(eventDto)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("id").exists())
            .andExpect(header().exists(HttpHeaders.LOCATION))
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
            .andExpect(jsonPath("free").value(false))
            .andExpect(jsonPath("offline").value(true))
            .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()))
        ;
    }
    
    @Test
    void createEventBadRequestEmptyInput() throws Exception {
        EventDto eventDto = EventDto.builder().build();
    
        mockMvc.perform(post("/api/events/")
                    .contentType(MediaTypes.HAL_JSON_VALUE)
                    .accept(MediaTypes.HAL_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(eventDto)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }
    
    @Test
    void createEventBadRequestWrongInput() throws Exception {
        // given
        EventDto event = EventDto.builder()
            .name("spring")
            .description("spring")
            .beginEnrollmentDateTime(LocalDateTime.of(2020, 07, 07, 12, 12))
            .closeEnrollmentDateTime(LocalDateTime.of(2020, 06, 07, 12, 12))
            .beginEventDateTime(LocalDateTime.of(2020, 06, 07, 12, 12))
            .endEventDateTime(LocalDateTime.of(2020, 06, 07, 12, 12))
            .basePrice(10000)
            .maxPrice(200)
            .limitOfEnrollment(100)
            .location("저 세상 강의실 A1")
            .build();
        
        // when & then
        mockMvc.perform(post("/api/events/")
                    .contentType(MediaTypes.HAL_JSON_VALUE)
                    .accept(MediaTypes.HAL_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(event)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$[0].objectName").exists())
            .andExpect(jsonPath("$[0].field").exists())
            .andExpect(jsonPath("$[0].defaultMessage").exists())
            .andExpect(jsonPath("$[0].code").exists())
            .andExpect(jsonPath("$[0].rejectedValue").exists());
    }
}
