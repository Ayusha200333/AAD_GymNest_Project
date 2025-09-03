package org.example.aad_gymnest.controller;

import org.example.aad_gymnest.dto.ClassDTO;
import org.example.aad_gymnest.dto.ResponseDTO;
import org.example.aad_gymnest.service.ClassService;
import org.example.aad_gymnest.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("api/v1/class")
public class ClassController {

        @Autowired
        private ClassService classService;

        @PostMapping("/save")
        public ResponseEntity<ResponseDTO> saveClass(@RequestBody ClassDTO classDTO) {
            try {
                int res = classService.saveClass(classDTO);
                if(res == VarList.Created) {
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "Class Saved Successfully", classDTO));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable, "Class Already Exists", null));
                }
            } catch(Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
            }
        }

        @PutMapping("/update/{id}")
        public ResponseEntity<ResponseDTO> updateClass(@PathVariable Long id, @RequestBody ClassDTO classDTO) {
            try {
                int res = classService.updateClass(id, classDTO);
                if(res == VarList.Created) {
                    return ResponseEntity.ok(new ResponseDTO(VarList.Created, "Class Updated Successfully", classDTO));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ResponseDTO(VarList.Not_Found, "Class Not Found", null));
                }
            } catch(Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
            }
        }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<ResponseDTO> deleteClass(@PathVariable Long id) {
            try {
                int res = classService.deleteClass(id);
                if(res == VarList.Created) {
                    return ResponseEntity.ok(new ResponseDTO(VarList.Created, "Class Deleted Successfully", null));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ResponseDTO(VarList.Not_Found, "Class Not Found", null));
                }
            } catch(Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
            }
        }

        @GetMapping("/getAll")
        public ResponseEntity<ResponseDTO> getAllClasses() {
            try {
                List<ClassDTO> classes = classService.getAllClasses();
                return ResponseEntity.ok(new ResponseDTO(VarList.Created, "All Classes Retrieved Successfully", classes));
            } catch(Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
            }
        }

        @GetMapping("/available")
        public ResponseEntity<ResponseDTO> getAvailableClasses() {
            try {
                List<ClassDTO> classes = classService.getAvailableClasses();
                return ResponseEntity.ok(new ResponseDTO(VarList.Created, "Available Classes Retrieved Successfully", classes));
            } catch(Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
            }
        }
}
