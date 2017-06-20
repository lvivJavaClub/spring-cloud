package com.lohika.jclub.storage;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by omuliarevych on 6/8/17.
 */
@RepositoryRestResource(path = "apartments")
public interface ApartmentRepository extends PagingAndSortingRepository<ApartmentRecord, String> {
}
