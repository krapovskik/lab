package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Type;
import mk.ukim.finki.wp.lab.repository.inmem.TypeRepository;
import mk.ukim.finki.wp.lab.service.TypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;

    public TypeServiceImpl(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public List<Type> getAllTypes() {
        return this.typeRepository.allTypes();
    }
}
