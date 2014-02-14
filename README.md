## EnzymeDB Bio4j module

This is a Bio4j module representing [ExPASy Enzyme DB](http://enzyme.expasy.org/). Find more information in [bio4j/modules](https://github.com/bio4j/modules).

> The ENZYME database is a repository of information relative to the nomenclature of enzymes. It is primarily based on the recommendations of the Nomenclature Committee of the International Union of Biochemistry and Molecular Biology (IUBMB), and it contains the following data for each type of characterized enzyme for which an EC (Enzyme Commission) number has been provided:

* EC number
* Recommended name
* Alternative names (if any)
* Catalytic activity
* Cofactors (if any)
* Pointers to the Swiss-Prot protein sequence entrie(s) that correspond to the enzyme (if any)
* Pointers to human disease(s) associated with a deficiency of the enzyme (if any)


## Usage

To use it in you sbt-project, add this to you `build.sbt`:

```scala
resolvers += "Era7 maven releases" at "http://releases.era7.com.s3.amazonaws.com"

libraryDependencies += "bio4j" %% "enzymedb-module" % "0.1.0"
```
