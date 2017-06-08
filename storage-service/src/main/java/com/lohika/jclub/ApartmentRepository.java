package com.lohika.jclub;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by omuliarevych on 6/8/17.
 */
@RepositoryRestResource
public interface ApartmentRepository extends CrudRepository<ApartmentRecord, String> {
}
