package org.example.aad_gymnest;

import org.example.aad_gymnest.dto.ClassDTO;
import org.example.aad_gymnest.entity.ClassEntity;
import org.example.aad_gymnest.repo.ClassRepository;
import org.example.aad_gymnest.service.impl.ClassServiceImpl;
import org.example.aad_gymnest.util.VarList;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClassServiceImplTests {

    @InjectMocks
    private ClassServiceImpl classService;

    @Mock
    private ClassRepository classRepository;

    private ClassEntity classEntity;
    private ClassDTO classDTO;

    @BeforeEach
    void setUp() {
        classEntity = new ClassEntity();
        classEntity.setId(1L);
        classEntity.setName("Yoga");
        classEntity.setTrainer("John");
        classEntity.setDay("Monday");
        classEntity.setTime("08:00");
        classEntity.setCapacity(20);
        classEntity.setEnrolled(0);
        classEntity.setStatus("AVAILABLE");

        classDTO = new ClassDTO();
        classDTO.setName("Yoga");
        classDTO.setTrainer("John");
        classDTO.setDay("Monday");
        classDTO.setTime("08:00");
        classDTO.setCapacity(20);
        classDTO.setEnrolled(0);
        classDTO.setStatus("AVAILABLE");
    }

    @Test
    void shouldSaveClassSuccessfully() {
        when(classRepository.findByName("Yoga")).thenReturn(Optional.empty());
        when(classRepository.save(any(ClassEntity.class))).thenReturn(classEntity);

        int result = classService.saveClass(classDTO);

        assertEquals(VarList.Created, result);
        verify(classRepository, times(1)).save(any(ClassEntity.class));
    }

    @Test
    void shouldNotSaveWhenClassAlreadyExists() {
        when(classRepository.findByName("Yoga")).thenReturn(Optional.of(classEntity));

        int result = classService.saveClass(classDTO);

        assertEquals(VarList.Not_Acceptable, result);
        verify(classRepository, never()).save(any(ClassEntity.class));
    }

    @Test
    void shouldUpdateClassSuccessfully() {
        when(classRepository.findById(1L)).thenReturn(Optional.of(classEntity));
        when(classRepository.save(any(ClassEntity.class))).thenReturn(classEntity);

        int result = classService.updateClass(1L, classDTO);

        assertEquals(VarList.Created, result);
        verify(classRepository, times(1)).save(classEntity);
    }

    @Test
    void shouldNotUpdateWhenClassNotFound() {
        when(classRepository.findById(99L)).thenReturn(Optional.empty());

        int result = classService.updateClass(99L, classDTO);

        assertEquals(VarList.Not_Found, result);
        verify(classRepository, never()).save(any(ClassEntity.class));
    }

    @Test
    void shouldDeleteClassSuccessfully() {
        when(classRepository.findById(1L)).thenReturn(Optional.of(classEntity));

        int result = classService.deleteClass(1L);

        assertEquals(VarList.Created, result);
        verify(classRepository, times(1)).delete(classEntity);
    }

    @Test
    void shouldNotDeleteWhenClassNotFound() {
        when(classRepository.findById(99L)).thenReturn(Optional.empty());

        int result = classService.deleteClass(99L);

        assertEquals(VarList.Not_Found, result);
        verify(classRepository, never()).delete(any(ClassEntity.class));
    }

    @Test
    void shouldReturnAllClasses() {
        when(classRepository.findAll()).thenReturn(List.of(classEntity));

        var result = classService.getAllClasses();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Yoga", result.get(0).getName());
    }

    @Test
    void shouldReturnAvailableClassesOnly() {
        ClassEntity fullClass = new ClassEntity(2L, "Zumba", "Anna", "Tuesday", "10:00", 10, 10, "FULL");
        when(classRepository.findAll()).thenReturn(List.of(classEntity, fullClass));

        var result = classService.getAvailableClasses();

        assertEquals(1, result.size());
        assertEquals("Yoga", result.get(0).getName());
    }
}
