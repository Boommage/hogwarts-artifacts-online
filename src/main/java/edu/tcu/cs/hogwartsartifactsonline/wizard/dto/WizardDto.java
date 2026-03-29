package edu.tcu.cs.hogwartsartifactsonline.wizard.dto;

import jakarta.validation.constraints.NotEmpty;

//Java records to implement Dto
public record WizardDto(Integer id,
                       @NotEmpty(message = "name is required")
                       String name,
                       Integer numOfArtifacts){
}
