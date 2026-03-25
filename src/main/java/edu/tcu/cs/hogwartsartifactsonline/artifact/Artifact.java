package edu.tcu.cs.hogwartsartifactsonline.artifact;

import edu.tcu.cs.hogwartsartifactsonline.wizard.Wizard;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

//to make sure Spring data GPA picks up the two domain models:
    //artifact and wizard - we need to implement some annotations

@Entity
public class Artifact  implements Serializable {

    //This annotation tells the Spring GPA that id is the primary key - NEEDED
    @Id
    //based on the class diagram - artifact has these four fields
    private String id;

    private  String name;

    private String description;

    private String imgUrl;

    //one owner can own - zero to many artifacts - many artifacts to one owner
    @ManyToOne
    //To implement one-to-many - on many side
    //This class refers to an instance of wizard in which its role is as owner
    private Wizard owner;

    public Artifact() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Wizard getOwner() {
        return owner;
    }

    public void setOwner(Wizard owner) {
        this.owner = owner;
    }
}
