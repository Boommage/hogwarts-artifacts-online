package edu.tcu.cs.hogwartsartifactsonline.artifact;

import edu.tcu.cs.hogwartsartifactsonline.system.Result;
import edu.tcu.cs.hogwartsartifactsonline.system.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArtifactController {

    private final ArtifactService artifactService;

    public ArtifactController(ArtifactService artifactService) {
        this.artifactService = artifactService;
    }


    @GetMapping("/api/v1/artifacts/{artifactId}")
    //This Result class is a wrapper class that defines the schema of the response
    //for each rest API endpoint we need to create a handler method - to process requests and return a response
    //@PathVariable - Automatically retrieve the url "artifactId" and bind it to the parameter of the same name
    public Result findArtifactById(@PathVariable String artifactId) {
        Artifact foundArtifact = this.artifactService.findById(artifactId);
        //Spring Mvc auto turns this obj into a JSON obj
        return new Result(true, StatusCode.SUCCESS, "Find One Success", foundArtifact);
    }
}
