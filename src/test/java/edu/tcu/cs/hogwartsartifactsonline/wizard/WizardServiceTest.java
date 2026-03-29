package edu.tcu.cs.hogwartsartifactsonline.wizard;

import edu.tcu.cs.hogwartsartifactsonline.artifact.Artifact;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
//ideally there should be at least one test class for each class in the main dir
class WizardServiceTest {

    @Mock //tells mockito that this is the obj that we want to simulate - dont call the real wizard repo
    WizardRepository wizardRepository;

    @InjectMocks //when the test case is started mockito will inject the mock obj into the following obj
    WizardService wizardService;

    List<Wizard> wizards;


    @BeforeEach
        //before each test method gets executed by JUNIT 5 - this gets executed first
    void setUp() {
        Wizard w1 = new Wizard();
        w1.setId(1);
        w1.setName("Albus Dumbledore");

        Wizard w2 = new Wizard();
        w2.setId(2);
        w2.setName("Harry Potter");

        Wizard w3 = new Wizard();
        w3.setId(3);
        w3.setName("Neville Longbottom");

        this.wizards = new ArrayList<>();
        this.wizards.add(w1);
        this.wizards.add(w2);
        this.wizards.add(w3);
    }

    @AfterEach
        //this method gets after each test method finished
    void tearDown() {
    }

    //good idea to put cleanup in either setUp or tearDown


    //mocking - creating objs that simulate the behavior of real objs - here we use a mock framework - Mockito
    //Test Method - A method with a test annotation
    @Test
    void testFindByIdSuccess() {
        // Given. Arrange inputs and targets. Define the behavior of Mock object wizardRepository.
        Wizard w = new Wizard();
        w.setId(1);
        w.setName("Albus Dumbledore");

        given(this.wizardRepository.findById(1)).willReturn(Optional.of(w)); // Define the behavior of the mock object.

        // When. Act on the target behavior. Act steps should cover the method to be tested.
        Wizard returnedWizard = this.wizardService.findById(1);

        // Then. Assert expected outcomes.
        assertThat(returnedWizard.getId()).isEqualTo(w.getId());
        assertThat(returnedWizard.getName()).isEqualTo(w.getName());
        verify(this.wizardRepository, times(1)).findById(1);
    }

    @Test
    void testFindByIdNotFound() {
        // Given
        given(this.wizardRepository.findById(Mockito.any(Integer.class))).willReturn(Optional.empty());

        // When
        Throwable thrown = catchThrowable(() -> {
            Wizard returnedWizard = this.wizardService.findById(1);
        });

        // Then
        assertThat(thrown)
                .isInstanceOf(WizardNotFoundException.class)
                .hasMessage("Could not find wizard with Id: 1 :(");
        verify(this.wizardRepository, times(1)).findById(Mockito.any(Integer.class));
    }

    @Test
    void testFindAllSuccess() {
        //Given
        given(wizardRepository.findAll()).willReturn(this.wizards);

        //When
        List<Wizard> actualWizards =  wizardService.findAll();

        //Then
        assertThat(actualWizards.size()).isEqualTo(this.wizards.size());

        verify(wizardRepository, times(1)).findAll();
    }

    @Test
    void testSaveSuccess() {
        // Given
        Wizard newWizard = new Wizard();
        newWizard.setName("Hermione Granger");

        given(this.wizardRepository.save(newWizard)).willReturn(newWizard);

        // When
        Wizard returnedWizard = this.wizardService.save(newWizard);

        // Then
        assertThat(returnedWizard.getName()).isEqualTo(newWizard.getName());
        verify(this.wizardRepository, times(1)).save(newWizard);
    }

    @Test
    void testUpdateSuccess() {
        // Given
        Wizard oldWizard = new Wizard();
        oldWizard.setId(1);
        oldWizard.setName("Albus Dumbledore");

        Wizard update = new Wizard();
        update.setName("Albus Dumbledore - update");

        given(this.wizardRepository.findById(1)).willReturn(Optional.of(oldWizard));
        given(this.wizardRepository.save(oldWizard)).willReturn(oldWizard);

        // When
        Wizard updatedWizard = this.wizardService.update(1, update);

        // Then
        assertThat(updatedWizard.getId()).isEqualTo(1);
        assertThat(updatedWizard.getName()).isEqualTo(update.getName());
        verify(this.wizardRepository, times(1)).findById(1);
        verify(this.wizardRepository, times(1)).save(oldWizard);
    }

    @Test
    void testUpdateNotFound() {
        //Given
        Wizard update = new Wizard();
        update.setName("Invisibility Cloak");

        given(this.wizardRepository.findById(1)).willReturn(Optional.empty());

        //When
        assertThrows(WizardNotFoundException.class, ()->
                wizardService.update(1, update));

        //Then
        verify(this.wizardRepository, times(1)).findById(1);
    }
    @Test
    void testDeleteSuccess() {
        //Given
        Wizard wizard = new Wizard();
        wizard.setName("Invisibility Cloak");

        given(wizardRepository.findById(1)).willReturn(Optional.of(wizard));
        doNothing().when(wizardRepository).deleteById(1); //once this method is called - do nothing

        //When
        wizardService.delete(1);

        //Then
        verify(wizardRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteNotFound() {
        // Given
        given(this.wizardRepository.findById(1)).willReturn(Optional.empty());

        // When
        assertThrows(WizardNotFoundException.class, () -> {
            this.wizardService.delete(1);
        });

        // Then
        verify(this.wizardRepository, times(1)).findById(1);
    }

}
