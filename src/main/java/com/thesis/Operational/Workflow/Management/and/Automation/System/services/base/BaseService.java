package com.thesis.Operational.Workflow.Management.and.Automation.System.services.base;

import com.thesis.Operational.Workflow.Management.and.Automation.System.exceptions.ResourceNotFoundException;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.base.BaseEntity;
import com.thesis.Operational.Workflow.Management.and.Automation.System.repositories.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class BaseService<T extends BaseEntity, ID extends Serializable> {

    private final BaseRepository<T, ID> baseRepository;

    private final String resourceName;

    public BaseService(BaseRepository<T, ID> baseRepository, String resourceName) {
        this.baseRepository = baseRepository;
        this.resourceName = resourceName;
    }

    public T findById(ID id){

        Optional<T> t = baseRepository.findById(id);
        return t.orElseThrow(()-> new ResourceNotFoundException(resourceName, id));
    }

    public <S extends T> S save(S s){
        return baseRepository.save(s);
    }

    public <S extends T> List<S> saveAll(Iterable<S> iterable) {

        return baseRepository.saveAll(iterable);
    }

    public List<T> findAll() {

        return baseRepository.findAll();
    }

    public List<T> findAll(Specification<T> specification) {

        return baseRepository.findAll(specification);
    }

    public Page<T> findAll(int page, int size, String sortInput) {

        return findAll(getPageable(page, size, sortInput));
    }

    public Page<T> findAll(Pageable pageRequest) {

        return findAll(Specification.where(null), pageRequest);
    }

    public Page<T> findAll(Specification<T> spec, int page, int size, String sortInput) {

        return findAll(spec, getPageable(page, size, sortInput));
    }

    public Page<T> findAll(Specification<T> spec, Pageable pageable) {

        return baseRepository.findAll(spec, pageable);
    }

    private Sort parseSortInput(String sortInput) {

        Sort sort = Sort.unsorted();
        if (sortInput != null) {
            List<String> properties = new LinkedList<>(Arrays.asList(sortInput.split(",")));
            if (properties.contains("desc")) {
                properties.remove("desc");
                String[] arrProp = new String[properties.size()];
                sort = Sort.by(Sort.Direction.DESC, properties.toArray(arrProp));
            } else {
                properties.remove("asc");
                String[] arrProp = new String[properties.size()];
                sort = Sort.by(Sort.Direction.ASC, properties.toArray(arrProp));
            }
        }
        return sort;
    }

    public Pageable getPageable(int page, int size, String sortInput) {

        Sort sort = parseSortInput(sortInput);
        return PageRequest.of(page, size, sort);
    }

    public boolean deleteById(ID id) {

        baseRepository.deleteById(id);
        return true;
    }

    public boolean delete(T t) {

        baseRepository.delete(t);
        return true;
    }
}
