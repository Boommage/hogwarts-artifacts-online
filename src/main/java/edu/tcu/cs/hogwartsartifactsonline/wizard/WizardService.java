package edu.tcu.cs.hogwartsartifactsonline.wizard;

import edu.tcu.cs.hogwartsartifactsonline.artifact.Artifact;
import edu.tcu.cs.hogwartsartifactsonline.artifact.ArtifactRepository;
import edu.tcu.cs.hogwartsartifactsonline.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//This Annotation makes database transaction processing very easy in Spring Boot
//Puts every method im this class in its own transaction
//If a method throws an exception while executing - modification to a database will be abandoned - rollback
//This could be placed on the method but if we want every method to have it then place on class
@Transactional
public class WizardService {

    //injecting WizardRepo into Wizard Service
    private final WizardRepository wizardRepository;

    public WizardService(WizardRepository wizardRepository) {
        this.wizardRepository = wizardRepository;
    }

    //when we launch the Spring Boot Project:
    //The Spring IOC Container will inject an instance of wizard repo into wizard service
    //wizard service does not have to worry about how to maintain and create this wizard repo obj


    public Wizard findById(Integer wizardId) {
        return this.wizardRepository.findById(wizardId)
                .orElseThrow(() -> new ObjectNotFoundException("wizard",wizardId));
    }

    public List<Wizard> findAll() {
        return this.wizardRepository.findAll();
    }

    public Wizard save(Wizard newWizard) {
        return this.wizardRepository.save(newWizard);
    }

    //A fluent interface is an object-oriented API whose design relies extensively on method chaining. Its goal is to increase code legibility
    public Wizard update(Integer wizardId, Wizard update) {
        return this.wizardRepository.findById(wizardId)
                .map(oldWizard -> {
                    oldWizard.setName(update.getName());
                    return this.wizardRepository.save(oldWizard);
                })
                .orElseThrow(() -> new ObjectNotFoundException("wizard",wizardId));
    }

    public void delete(Integer wizardId) {
        Wizard wizardToBeDeleted = this.wizardRepository.findById(wizardId)
                .orElseThrow(() -> new ObjectNotFoundException("wizard",wizardId));

        //Before deletion, unassign this wizard's owned artifacts
        wizardToBeDeleted.removeAllArtifacts();
        this.wizardRepository.deleteById(wizardId);
    }
}
