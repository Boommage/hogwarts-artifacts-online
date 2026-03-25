package edu.tcu.cs.hogwartsartifactsonline.artifact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//this is optional but is here for consistency
@Repository
//JpaRepo - is an interface that requires two generic types:
    //1. The domain type the repo manages - In this case Artifact
    //2. The type of the ID that the entity manages - In this case String
//This enables Spring Data to find this interface and automatically create an implementation for it
//We get the most relevant CRUD methods for standard data access available in a standard repository
public interface ArtifactRepository extends JpaRepository<Artifact, String> {
}
