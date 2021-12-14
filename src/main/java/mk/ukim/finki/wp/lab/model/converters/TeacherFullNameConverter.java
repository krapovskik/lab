package mk.ukim.finki.wp.lab.model.converters;

import mk.ukim.finki.wp.lab.model.TeacherFullName;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class TeacherFullNameConverter implements AttributeConverter<TeacherFullName,String> {

    private static final String SEPARATOR = ", ";

    @Override
    public String convertToDatabaseColumn(TeacherFullName teacherFullName) {
        if (teacherFullName == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        if (teacherFullName.getSurname() != null && !teacherFullName.getSurname()
                .isEmpty()) {
            sb.append(teacherFullName.getSurname());
            sb.append(SEPARATOR);
        }

        if (teacherFullName.getName() != null
                && !teacherFullName.getName().isEmpty()) {
            sb.append(teacherFullName.getName());
        }

        return sb.toString();
    }

    @Override
    public TeacherFullName convertToEntityAttribute(String dbTeacherFullName) {
        if (dbTeacherFullName == null || dbTeacherFullName.isEmpty()) {
            return null;
        }

        String[] pieces = dbTeacherFullName.split(SEPARATOR);

        if (pieces == null || pieces.length == 0) {
            return null;
        }

        TeacherFullName teacherFullName = new TeacherFullName();
        String firstPiece = !pieces[0].isEmpty() ? pieces[0] : null;
        if (dbTeacherFullName.contains(SEPARATOR)) {
            teacherFullName.setSurname(firstPiece);

            if (pieces.length >= 2 && pieces[1] != null
                    && !pieces[1].isEmpty()) {
                teacherFullName.setName(pieces[1]);
            }
        } else {
            teacherFullName.setName(firstPiece);
        }

        return teacherFullName;
    }
}
