package tms.petstore.resource;


import com.sun.org.glassfish.gmbal.ParameterNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tms.petstore.entity.ApiResponse;
import tms.petstore.entity.Pet;
import tms.petstore.entity.Status;
import tms.petstore.service.PetService;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping(path = "/pet")
public class PetResource {

    private final PetService petService;

    @Autowired
    public PetResource(PetService petService) {
        this.petService = petService;
    }


    @PostMapping
    public ResponseEntity<ApiResponse> createPet(@RequestBody Pet pet) {
        boolean b = petService.setPet(pet);
        if (b) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.CREATED.value(), "pet", "successful operation"), HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND.value(), "pet", "Invalid input"), HttpStatus.NOT_FOUND);
    }


    @PutMapping
    public ResponseEntity<ApiResponse> updateExistingPet(@RequestBody Pet pet) {
        boolean b = petService.updatePet(pet, 0);
        if (b) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.CREATED.value(), "pet", "successful operation"), HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND.value(), "pet", "Invalid input"), HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/findByStatus")
    public ResponseEntity<List<Pet>> findByStatus(Status status) {
        boolean b = petService.checkStatus(status);
        if (b) {
            List<Pet> pets = petService.returnPerByStatus(status);
            return new ResponseEntity<>(pets, HttpStatus.CREATED);
        }
        else
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Pet> findPet(@PathVariable(name = "id") int id){
            Pet pet = petService.returnPet(id);
            return new ResponseEntity<>(pet, HttpStatus.CREATED);


    }

    @PostMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> updatePetInDB (@PathVariable(name = "id") int id, @RequestBody Pet pet) {
        String name = pet.getName();
        Status status = pet.getStatus();
        boolean b = petService.updateNameAndStatusById(id, name, status);
        if (b) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.CREATED.value(), "pet", "successful operation"), HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(new ApiResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), "pet", "Invalid input"), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> findByStatus(@PathVariable(name = "id") int id) {
        boolean b = petService.deletedPetById(id);
        if (b) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.CREATED.value(), "pet", "successful operation"), HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND.value(), "pet", "Invalid input"), HttpStatus.NOT_FOUND);

    }


}
