package com.api.auth.events;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class EventTest {
    
    @Test
    void builder() {
        Event event = Event.builder()
            .name("Rest Api Training")
            .description("Rest Api, development training")
            .build();
        assertThat(event).isNotNull();
    }
    
    @Test
    void javaBean() {
        // java bean spec 이란? 기본생성자와 필드에 대한 게터 세터 가 있어야 함
        // setter를 사용하면 데이터를 쉽게 변경할 수 있기 때문에 실제 코드에서는 세터의 존재는 고려해봐야 함
        
        // given
        String name = "event";
        String description = "spring";
        
        // when
        Event event = Event.builder()
            .name(name)
            .description(description)
            .build();
        
        // then
        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);
    }
    
    @Test
    void testFree() {
        Event event = Event.builder()
            .basePrice(0)
            .maxPrice(0)
            .build();
        
        event.update();
        
        assertThat(event.isFree()).isTrue();
    
        event = Event.builder()
            .basePrice(100)
            .maxPrice(0)
            .build();
    
        event.update();
    
        assertThat(event.isFree()).isFalse();
    
        event = Event.builder()
            .basePrice(0)
            .maxPrice(100)
            .build();
    
        event.update();
    
        assertThat(event.isFree()).isFalse();
    }
    
    @Test
    void testOffline() {
        Event event = Event.builder()
            .location("아무튼 장소가 있어.")
            .build();
        
        event.update();
        
        assertThat(event.isOffline()).isTrue();
        
        event = Event.builder()
            .build();
        
        event.update();
        
        assertThat(event.isOffline()).isFalse();
    }
}