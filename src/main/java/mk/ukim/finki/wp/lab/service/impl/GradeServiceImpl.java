package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.repository.jpa.GradeRepository;
import mk.ukim.finki.wp.lab.service.GradeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;

    public GradeServiceImpl(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @Override
    public void save(Character grade, Student student, Course course, LocalDateTime timestamp) {
        gradeRepository.save(new Grade(grade,student,course,timestamp));
    }

    @Override
    public List<Grade> findAllBetween(LocalDateTime from, LocalDateTime to) {
        return gradeRepository.findAllByTimestampBetween(from,to);
    }
}
