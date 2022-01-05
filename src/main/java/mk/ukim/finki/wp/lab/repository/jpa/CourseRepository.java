package mk.ukim.finki.wp.lab.repository.jpa;

import mk.ukim.finki.wp.lab.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    void deleteByCourseId(Long Id);
    @Query(value = "SELECT * from COURSE c " +
            "left join TEACHER t on t.id = c.teacher_id " +
            "left join course_students cs on c.course_id = cs.course_course_id " +
            "left join student s on cs.students_username = s.username " +
            "where c.name ilike %?1% or c.description ilike %?1% or t.teacher_full_name ilike %?1% or c.type ilike %?1% or (s.name is not null and s.name ilike %?1%) or (s.surname is not null and s.surname ilike %?1%)", nativeQuery = true)
    List<Course> allCoursesByFilter(String filter);
}
