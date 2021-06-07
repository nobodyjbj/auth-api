package com.api.auth.events;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

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
    
    private static Stream<Arguments> provideForTestFree() {
        return Stream.of(
            Arguments.of(0, 0, true),
            Arguments.of(100, 0, false),
            Arguments.of(0, 100, false),
            Arguments.of(100, 200, false)
        );
    }
    
    @ParameterizedTest
    @MethodSource(value = "provideForTestFree")
    void testFree(int basePrice, int maxPrice, boolean isFree) {
        Event event = Event.builder()
            .basePrice(basePrice)
            .maxPrice(maxPrice)
            .build();
        
        event.update();
        
        assertThat(event.isFree()).isEqualTo(isFree);
    }
    
    private static Stream<Arguments> provideForTestOffline() {
        return Stream.of(
            Arguments.of("강남", true),
            Arguments.of(null, false),
            Arguments.of("", false)
        );
    }
    
    @ParameterizedTest
    @MethodSource(value = "provideForTestOffline")
    void testOffline(String location, boolean isOffline) {
        Event event = Event.builder()
            .location(location)
            .build();
        
        event.update();
        
        assertThat(event.isOffline()).isEqualTo(isOffline);
    }
}