package edu.tcu.cs.hogwartsartifactsonline.artifact;

import edu.tcu.cs.hogwartsartifactsonline.wizard.Wizard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
//ideally there should be at least one test class for each class in the main dir
class ArtifactServiceTest {

    @Mock //tells mockito that this is the obj that we want to simulate - dont call the real artifact repo
    ArtifactRepository artifactRepository;

    @InjectMocks //when the test case is started mockito will inject the mock obj into the following obj
    ArtifactService artifactService;


    @BeforeEach //before each test method gets executed by JUNIT 5 - this gets executed first
    void setUp() {
    }

    @AfterEach  //this method gets after each test method finished
    void tearDown() {
    }

    //good idea to put cleanup in either setUp or tearDown


    //mocking - creating objs that simulate the behavior of real objs - here we use a mock framework - Mockito
    //Test Method - A method with a test annotation
    @Test
    void testFindByIdSuccess() {
        //1. Given. Arrange inputs and targets. Define the behavior of Mock obj artifactRepo
        /*
          "id": "1250808601744904192",
          "name": "Invisibility Cloak",
          "description": "An invisibility cloak is used to make the wearer invisible.",
          "imageUrl": "ImageUrl",
         */
        Artifact a = new Artifact();
        a.setId("1250808601744904192");
        a.setName("Invisibility Cloak");
        a.setDescription("An invisibility cloak is used to make the wearer invisible.");
        a.setImgUrl("ImageUrl");

        Wizard w = new Wizard();
        w.setId(2);
        w.setName("Harry Potter");

        a.setOwner(w);

        //when the findById method of repo gets called w/ this specific id - a will be returned
        given(artifactRepository.findById("1250808601744904192")).willReturn(Optional.of(a)); //Defines the behavior of the mock obj

        //2. When. Act on the target behavior. When steps should cover the method to be tested.
        Artifact returnedArtifact =  artifactService.findById("1250808601744904192");

        //3. Then. Assert expected outcomes.
        assertThat(returnedArtifact.getId()).isEqualTo(a.getId());
        assertThat(returnedArtifact.getName()).isEqualTo(a.getName());
        assertThat(returnedArtifact.getDescription()).isEqualTo(a.getDescription());
        assertThat(returnedArtifact.getImgUrl()).isEqualTo(a.getImgUrl());

        //verify that this mock objs method is called exactly once inside the service obj
        verify(artifactRepository, times(1)).findById("1250808601744904192");
    }

    @Test
    void testFindByIdNotFound() {
        //1. Given
        //if an invalid id is passed - return an empty obj
        given(artifactRepository.findById(Mockito.any(String.class))).willReturn(Optional.empty());

        //2. When
        //if artifact service throws an execption - catch it and assign it to a thrown var
        Throwable thrown = catchThrowable(() -> {
            Artifact returnedArtifact =  artifactService.findById("1250808601744904192");
        });

        //3. Then
        assertThat(thrown)
                .isInstanceOf(ArtifactNotFoundException.class)
                .hasMessage("Could not find artifact with Id: 1250808601744904192 :(");
    }


}