package edu.tcu.cs.hogwartsartifactsonline.artifact;

import edu.tcu.cs.hogwartsartifactsonline.artifact.utils.IdWorker;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//This Annotation makes database transaction processing very easy in Spring Boot
//Puts every method im this class in its own transaction
    //If a method throws an exception while executing - modification to a database will be abandoned - rollback
//This could be placed on the method but if we want every method to have it then place on class
@Transactional
public class ArtifactService {

    //injecting ArtifactRepo into Artifact Service
    private final ArtifactRepository artifactRepository;

    private final IdWorker idWorker;

    public ArtifactService(ArtifactRepository artifactRepository, IdWorker idWorker) {
        this.artifactRepository = artifactRepository;
        this.idWorker = idWorker;
    }

    //when we launch the Spring Boot Project:
    //The Spring IOC Container will inject an instance of artifact repo into artifact service
    //artifact service does not have to worry about how to maintain and create this artifact repo obj


    public Artifact findById(String artifactId) {
        return this.artifactRepository.findById(artifactId)
                .orElseThrow(() -> new ArtifactNotFoundException(artifactId));
    }

    public List<Artifact> findAll() {
        return this.artifactRepository.findAll();
    }

    public Artifact save(Artifact newArtifact) {
        newArtifact.setId(idWorker.nextId() + "");
        return this.artifactRepository.save(newArtifact);
    }

    //A fluent interface is an object-oriented API whose design relies extensively on method chaining. Its goal is to increase code legibility
    public Artifact update(String artifactId, Artifact update) {
        return this.artifactRepository.findById(artifactId)
                .map(oldArtifact-> {
                    oldArtifact.setName(update.getName());
                    oldArtifact.setDescription(update.getDescription());
                    oldArtifact.setImgUrl(update.getImgUrl());
                    Artifact updatedArtifact = this.artifactRepository.save(oldArtifact);
                    return updatedArtifact;
                }).orElseThrow(() -> new ArtifactNotFoundException(artifactId));
    }

    public void delete(String artifactId) {
        this.artifactRepository.findById(artifactId)
                .orElseThrow(() -> new ArtifactNotFoundException(artifactId));
        this.artifactRepository.deleteById(artifactId);
    }
}
