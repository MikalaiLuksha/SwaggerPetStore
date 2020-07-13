package tms.petstore.service;

import org.springframework.stereotype.Service;
import tms.petstore.entity.Pet;
import tms.petstore.entity.Status;
import tms.petstore.storage.PetStorage;

import javax.el.MethodNotFoundException;
import java.util.List;

@Service
public class PetService {

    private final PetStorage petStorage;


    public PetService(PetStorage petStorage) {
        this.petStorage = petStorage;
    }

    public void setPet(Pet pet) {
        Integer[] arrayTags = serArrayTags(pet);
        boolean b = petStorage.addPet(pet);
        boolean b3 = petStorage.addDataCategory(pet);
        boolean b4 = petStorage.addDataTags(pet, arrayTags);
        boolean bb = b && b3 && b4;
        if (!bb) {
            throw new MethodNotFoundException("Invalid input");
        }
    }

    public void updatePet(Pet pet, int id) {
        checkPet(id);
        Integer[] arrayTags = serArrayTags(pet);
        boolean b = petStorage.updatePetById(pet, id);
        boolean b1 = petStorage.updateCategoryById(pet, id);
        boolean b2 = petStorage.updateTagsById(pet, arrayTags, id);
        boolean bb = b && b1 && b2;
        if (!bb) {
            throw new MethodNotFoundException("Validation exception");
        }
    }

    public List<Pet> returnPerByStatus(Status status) {
        checkStatus(status);
        return petStorage.getPetFindByStatus(status);
    }

    public Pet returnPet(int id) {
        checkPet(id);
        return petStorage.getPetById(id);
    }

    public void updateNameAndStatusById(int id, String name, Status status) {
        boolean b = petStorage.updateNameAndStatusById(id, name, status);
        if (!b) {
            throw new MethodNotFoundException("Invalid input");
        }
    }

    public void deletedPetById(int id) {
        checkPet(id);
        petStorage.removePetById(id);
        petStorage.removeDataCategoryById(id);
        petStorage.removeDataTagById(id);
    }

    public void checkStatus(Status status) {
        boolean e = status.equals(Status.available);
        boolean e1 = status.equals(Status.pending);
        boolean e2 = status.equals(Status.sold);
        boolean f = e || e1 || e2;
        if (!f) {
            throw new IllegalArgumentException("Invalid status value");
        }
    }

    private void checkPet(int id) {
        boolean b = petStorage.checkPet(id);
        if (!b) {
            throw new NullPointerException("Pet not found");
        }
    }

    private Integer[] serArrayTags(Pet pet) {
        int sizeArray = pet.getTags().size();
        Integer[] arrayTags = new Integer[sizeArray];
        for (int i = 0; i < sizeArray; i++) {
            arrayTags[i] = pet.getTags().get(i).getId();
        }
        return arrayTags;
    }

}
