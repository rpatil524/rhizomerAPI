package net.rhizomik.rhizomer.controller;

/**
 * Created by http://rhizomik.net/~roberto/
 */
import com.google.common.base.Preconditions;
import net.rhizomik.rhizomer.model.Dataset;
import net.rhizomik.rhizomer.repository.DatasetRepository;
import net.rhizomik.rhizomer.service.SPARQLService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Set;

@RepositoryRestController
public class OntologiesController {
    final Logger logger = LoggerFactory.getLogger(OntologiesController.class);

    @Autowired private DatasetRepository datasetRepository;
    @Autowired private SPARQLService sparqlService;

    @RequestMapping(value = "/datasets/{datasetId}/ontologies", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody List<String> addDatasetOntology(@RequestBody List<String> ontologies, @PathVariable String datasetId) throws Exception {
        Dataset dataset = datasetRepository.findOne(datasetId);
        Preconditions.checkNotNull(dataset, "Dataset with id {} not found", datasetId);
        ontologies.forEach(ontology -> {
            sparqlService.loadData(dataset.getSparqlEndPoint(), dataset.getDatasetOntologiesGraph().toString(), ontology);
            dataset.addDatasetOntology(ontology);
        });
        logger.info("Added ontologies {} to Dataset {}", ontologies.toString(), datasetId);
        return datasetRepository.save(dataset).getDatasetOntologies();
    }

    @RequestMapping(value = "/datasets/{datasetId}/ontologies", method = RequestMethod.GET)
    public @ResponseBody List<String> retrieveDatasetOntologies(@PathVariable String datasetId) throws Exception {
        Dataset dataset = datasetRepository.findOne(datasetId);
        Preconditions.checkNotNull(dataset, "Dataset with id {} not found", datasetId);
        logger.info("Retrieved ontologies for Dataset {}", datasetId);
        return dataset.getDatasetOntologies();
    }

    @RequestMapping(value = "/datasets/{datasetId}/ontologies", method = RequestMethod.PUT)
    public @ResponseBody List<String> updateDatasetOntologies(@Valid @RequestBody Set<String> updatedOntologies, @PathVariable String datasetId) throws Exception {
        Dataset dataset = datasetRepository.findOne(datasetId);
        Preconditions.checkNotNull(dataset, "Dataset with id {} not found", datasetId);
        dataset.setDatasetOntologies(updatedOntologies);
        URI datasetOntologiesGraph = dataset.getDatasetOntologiesGraph();
        sparqlService.clearGraph(dataset.getSparqlEndPoint(), datasetOntologiesGraph);
        updatedOntologies.forEach(ontologyUriStr ->
                sparqlService.loadData(dataset.getSparqlEndPoint(), datasetOntologiesGraph.toString(), ontologyUriStr));
        logger.info("Updated Dataset {} ontologies", datasetId);
        return datasetRepository.save(dataset).getDatasetOntologies();
    }
}
