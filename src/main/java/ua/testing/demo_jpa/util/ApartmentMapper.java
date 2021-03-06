package ua.testing.demo_jpa.util;

import lombok.extern.slf4j.Slf4j;
import ua.testing.demo_jpa.entity.Apartment;
import ua.testing.demo_jpa.entity.ApartmentTimeSlotView;
import ua.testing.demo_jpa.entity.ApartmentTimetable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class ApartmentMapper {
    private ApartmentMapper() {

    }

    public static Apartment map(ApartmentTimeSlotView slot) {
        List<ApartmentTimetable> schedule;

        if (slot.getStartsAt() != null && slot.getEndsAt() != null) {
            schedule = Collections.singletonList(ApartmentTimetable
                    .builder()
                    .id(slot.getSlotId())
                    .startsAt(slot.getStartsAt())
                    .endsAt(slot.getEndsAt())
                    .status(slot.getStatus())
                    .build());
        } else {
            schedule = new ArrayList<>();
        }

        return Apartment
                .builder()
                .id(slot.getId())
                .price(slot.getPrice())
                .type(slot.getType())
                .bedsCount(slot.getBedsCount())
                .schedule(schedule)
                .build();
    }
}
