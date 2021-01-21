package ua.testing.demo_jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.testing.demo_jpa.entity.Apartment;
import ua.testing.demo_jpa.entity.ApartmentTimeSlot;

import java.time.LocalDateTime;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
    @Query("SELECT ap FROM Apartment ap WHERE ap.isAvailable = true")
    Page<Apartment> findAllAvailable(Pageable pageable);

    @Query(value = "SELECT id, beds_count AS bedsCount," +
            " price, type, starts_at AS startsAt," +
            " ends_at AS endsAt, status, description, " +
            " slotId " +
            "FROM apartment ap " +
            "LEFT JOIN " +
            "    (SELECT starts_at, ends_at, status, apartment_id, t.id as slotId " +
            "     FROM apartment a " +
            "     LEFT JOIN apartment_timetable t " +
            "     ON a.id = t.apartment_id " +
            "     WHERE is_available = true AND (" +
            "       t.starts_at <= :startsAt AND t.ends_at >= :endsAt OR " +
            "       t.starts_at BETWEEN :startsAt AND :endsAt OR " +
            "       t.ends_at BETWEEN :startsAt AND :endsAt" +
            "     )" +
            ") AS r " +
            "ON ap.id = r.apartment_id",
            countQuery = "SELECT COUNT(*) " +
                    "FROM apartment ap " +
                    "LEFT JOIN " +
                    "    (SELECT starts_at, ends_at, status, apartment_id, t.id as slotId " +
                    "     FROM apartment a " +
                    "     LEFT JOIN apartment_timetable t " +
                    "     ON a.id = t.apartment_id " +
                    "     WHERE is_available = true AND (" +
                    "       t.starts_at <= :startsAt AND t.ends_at >= :endsAt OR " +
                    "       t.starts_at BETWEEN :startsAt AND :endsAt OR " +
                    "       t.ends_at BETWEEN :startsAt AND :endsAt" +
                    "     )" +
                    ") AS r " +
                    "ON ap.id = r.apartment_id",
            nativeQuery = true)
    Page<ApartmentTimeSlot> findAllAvailableByDate(LocalDateTime startsAt, LocalDateTime endsAt, Pageable pageable);
}
