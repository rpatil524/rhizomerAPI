package net.rhizomik.rhizomer.repository;

import net.rhizomik.rhizomer.model.Class;
import net.rhizomik.rhizomer.model.Dataset;
import net.rhizomik.rhizomer.model.id.DatasetClassId;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@RepositoryRestResource(exported = false)
public interface ClassRepository extends PagingAndSortingRepository<Class, DatasetClassId> {
    Class findByDatasetAndUri(Dataset dataset, String uri);
}
