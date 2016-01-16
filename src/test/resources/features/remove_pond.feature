# Created by http://rhizomik.net/~roberto/

Feature: Remove pond
  In order to remove no longer needed data explorations
  As a data manager
  I want to delete a pond of graphs and all its associated classes and facets

  Background: Existing pond with classes and facets
    Given There is a pond "apollo13r" on a local server storing "data/nasa-apollo13.ttl" in graph "http://rhizomik.net/pond/apollo13r"
    When I extract the classes from pond "apollo13r"
    When I extract the facets for class "foaf:Person" in pond "apollo13r"

  Scenario: pond is deleted together with its classes and facets
    When I delete a pond with id "apollo13r"
    Then the response status is 200
    And There is no pond with id "apollo13r"
    And There is no class "foaf:Person" in pond "apollo13r"
    And There is no facet "foaf:name" for class "foaf:Person" in pond "apollo13r"