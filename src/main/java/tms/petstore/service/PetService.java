package tms.petstore.service;

import org.springframework.stereotype.Service;
import tms.petstore.entity.Pet;
import tms.petstore.entity.Status;
import tms.petstore.storage.PetStorage;

import java.util.List;

@Service
public class PetService {

        private final PetStorage petStorage;


    public PetService(PetStorage petStorage) {
        this.petStorage = petStorage;
    }

    public boolean setPet(Pet pet){
        Integer[] arrayTags = serArrayTags(pet);
        boolean b = petStorage.addPet(pet);
        boolean b3 = petStorage.addDataCategory(pet);
        boolean b4 = petStorage.addDataTags(pet, arrayTags);
        return b && b3 && b4;
    }

    public boolean updatePet(Pet pet, int id){
        Integer[] arrayTags = serArrayTags(pet);
        boolean b = petStorage.updatePetById(pet, id);
        boolean b1 = petStorage.updateCategoryById(pet, id);
        boolean b2 = petStorage.updateTagsById(pet, arrayTags, id);
        return b && b1 && b2;
    }


    public List<Pet> returnPerByStatus(Status status) {
            return petStorage.getPetFindByStatus(status);
     }

     public Pet returnPet(int id){
        return petStorage.getPetById(id);
     }

     public boolean updateNameAndStatusById(int id, String name, Status status){
        return petStorage.updateNameAndStatusById(id, name, status);
     }

     public boolean deletedPetById ( int id ){
         boolean b = petStorage.removePetById(id);
         boolean b1 = petStorage.removeDataCategoryById(id);
         boolean b2 = petStorage.removeDataTagById(id);
         return  b && b1 && b2;
     }

    public boolean checkStatus (Status status){
        boolean equals = status.equals(Status.available);
        boolean equals1 = status.equals(Status.pending);
        boolean equals2 = status.equals(Status.sold);
        return equals || equals1 || equals2;
    }







    private Integer [] serArrayTags(Pet pet){
        int sizeArray = pet.getTags().size();
        Integer [] arrayTags = new Integer[sizeArray];
        for (int i = 0; i < sizeArray; i++) {
            arrayTags [i] = pet.getTags().get(i).getId();
        }
        return arrayTags;
    }

}
