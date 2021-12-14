package mk.ukim.finki.wp.lab.repository.inmem;

import mk.ukim.finki.wp.lab.model.Type;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TypeRepository {
    private final List<Type> types;

    public TypeRepository() {
        this.types = new ArrayList<>(List.of(Type.values()));
    }

    public List<Type> allTypes(){
        return this.types;
    }
}
