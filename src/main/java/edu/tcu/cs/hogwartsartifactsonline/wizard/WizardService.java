package edu.tcu.cs.hogwartsartifactsonline.wizard;
import edu.tcu.cs.hogwartsartifactsonline.artifact.Artifact;
import edu.tcu.cs.hogwartsartifactsonline.artifact.ArtifactRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import edu.tcu.cs.hogwartsartifactsonline.system.exception.ObjectNotFoundException;
import java.util.List;
@Service
@Transactional
public class WizardService {

    private final WizardRepository wizardRepository;
    private final ArtifactRepository artifactRepository;
    public WizardService(WizardRepository wizardRepository, ArtifactRepository artifactRepository) {
        this.wizardRepository = wizardRepository;
        this.artifactRepository = artifactRepository;
    }

    public void delete(Integer wizardId) {
        Wizard wizardToBeDeleted= this.wizardRepository.findById(wizardId)
                .orElseThrow(()-> new ObjectNotFoundException("wizard",wizardId));
        // must un-link all artifacts to this wizard
        wizardToBeDeleted.removeAllArtifacts();
        this.wizardRepository.deleteById(wizardId);
    }

    public Wizard update(Integer wizardId, Wizard update) {
        return this.wizardRepository.findById(wizardId).map(oldWizard-> {
            oldWizard.setName(update.getName());
            return this.wizardRepository.save(oldWizard);
        }).orElseThrow(()-> new ObjectNotFoundException("wizard",wizardId));
    }

    public Wizard save(Wizard newWizard) {
        return this.wizardRepository.save(newWizard);
    }

    public List<Wizard> findAll() {
        return this.wizardRepository.findAll();
    }

    public Wizard findById(Integer wizardId) {
        return this.wizardRepository.findById(wizardId)
                .orElseThrow(() ->new ObjectNotFoundException("wizard",wizardId));
    }
    public void assignArtifact(Integer wizardId, String artifactId){
        Artifact artifact = this.artifactRepository.findById(artifactId)
                .orElseThrow(()->
                        new ObjectNotFoundException("artifact",artifactId));

        Wizard wizard = this.wizardRepository.findById(wizardId)
                .orElseThrow(()->
                        new ObjectNotFoundException("wizard",wizardId));
        if(artifact.getOwner()!=null){
            artifact.getOwner().removeArtifact(artifact);
        }
        wizard.addArtifact(artifact);
    }
}
