package com.lohika.jclub.storage.service;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "apartments")
public interface ApartmentRepository extends PagingAndSortingRepository<ApartmentRecord, String> {
}
