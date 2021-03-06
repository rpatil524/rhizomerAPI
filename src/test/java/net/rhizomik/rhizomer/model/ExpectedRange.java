package net.rhizomik.rhizomer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by http://rhizomik.net/~roberto/
 */
public class ExpectedRange {
    public String id;
    public String uri;
    public String label;
    public String curie;
    public int timesUsed;
    public int differentValues;
    public boolean isRelation;

    public ExpectedRange() {}

    public ExpectedRange(Range datasetRange) {
        this.uri = datasetRange.getId().toString();
        this.curie = id.split("/")[4];
        this.label = datasetRange.getLabel();
        this.timesUsed = datasetRange.getTimesUsed();
        this.differentValues = datasetRange.getDifferentValues();
        this.isRelation = datasetRange.isRelation();
    }

    @JsonIgnore
    public String getId() { return id; }

    public void setId(String datasetId, String classCurie, String facetCurie) {
        this.id = "/datasets/"+datasetId+
                  "/classes/"+classCurie+
                  "/facets/"+facetCurie+
                  "/ranges/"+Curie.uriStrToCurie(uri);
    }
}
