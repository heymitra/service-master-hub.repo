package com.example.serviceprovider;

import com.example.serviceprovider.model.Expert;
import com.example.serviceprovider.service.dto.ExpertDTO;
import com.example.serviceprovider.model.enumeration.ExpertStatusEnum;
import com.example.serviceprovider.repository.ExpertRepository;
import com.example.serviceprovider.service.ExpertService;
import com.example.serviceprovider.service.impl.ExpertServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Transactional
public class ExpertServiceTest {

    @Mock
    private ExpertRepository expertRepository;

    private ExpertService expertService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        expertService = new ExpertServiceImpl(expertRepository);
    }

    @Test
    void testSaveOrUpdate_FirstTimeSave_ValidExpert() {
        Expert expert = createValidExpert(true);

        when(expertRepository.save(expert)).thenReturn(expert);

        Expert savedExpert = expertService.saveOrUpdate(expert);

        assertNotNull(savedExpert);
        assertEquals(expert.getEmail(), savedExpert.getEmail());
        assertEquals(expert.getPassword(), savedExpert.getPassword());
        assertEquals(LocalDateTime.now().getDayOfYear(), savedExpert.getRegistrationDateTime().getDayOfYear());
        assertEquals(0, savedExpert.getScore());
        assertEquals(ExpertStatusEnum.NEW, savedExpert.getExpertStatus());
    }

    @Test
    void testSaveOrUpdate_UpdateValidExpert() {
        Expert expert = createValidExpert(false);

        when(expertRepository.save(expert)).thenReturn(expert);

        Expert savedExpert = expertService.saveOrUpdate(expert);

        assertNotNull(savedExpert);
        assertEquals(expert.getEmail(), savedExpert.getEmail());
        assertEquals(expert.getPassword(), savedExpert.getPassword());
    }

    @Test
    void testSaveOrUpdate_InvalidEmail() {
        Expert expert = createValidExpert(true);
        expert.setEmail("invalid.email");

        Expert savedExpert = expertService.saveOrUpdate(expert);

        assertNull(savedExpert);
    }

    @Test
    void testSaveOrUpdate_InvalidPassword() {
        Expert expert = createValidExpert(true);
        expert.setPassword("invalidpassword");

        Expert savedExpert = expertService.saveOrUpdate(expert);

        assertNull(savedExpert);
    }

    @Test
    void testSaveOrUpdate_InvalidPhoto_Null() {
        Expert expert = createValidExpert(true);
        expert.setPersonalPhoto(new byte[]{0, 0, 0});

        Expert savedExpert = expertService.saveOrUpdate(expert);

        assertNull(savedExpert);
    }

    @Test
    void testSaveOrUpdate_InvalidPhoto_ExceedsSize() {
        Expert expert = createValidExpert(true);
        byte[] largePhoto = new byte[310 * 1024];
        expert.setPersonalPhoto(largePhoto);

        Expert savedExpert = expertService.saveOrUpdate(expert);

        assertNull(savedExpert);
    }

    private Expert createValidExpert(boolean isFirstTimeSave) {
        Expert expert = new Expert();
        expert.setEmail("valid.email@example.com");
        expert.setPassword("ValidPassword1");
        expert.setPersonalPhoto(new byte[]{1, 2, 3});

        if (isFirstTimeSave) {
            expert.setId(null);
        } else {
            expert.setId(1L);
        }

        return expert;
    }

    @Test
    void testFindAllExperts() {
        List<Expert> mockedExperts = new ArrayList<>();
        Expert expert1 = new Expert();
        expert1.setName("John");
        expert1.setSurname("Doe");
        expert1.setEmail("john.doe@example.com");
        expert1.setScore(100);
        expert1.setExpertStatus(ExpertStatusEnum.APPROVED);
        mockedExperts.add(expert1);

        Expert expert2 = new Expert();
        expert2.setName("Jane");
        expert2.setSurname("Smith");
        expert2.setEmail("jane.smith@example.com");
        expert2.setScore(200);
        expert2.setExpertStatus(ExpertStatusEnum.NEW);
        mockedExperts.add(expert2);

        when(expertRepository.findAllExperts()).thenReturn(mockedExperts);

        List<ExpertDTO> expertDTOs = expertService.findAllExperts();

        assertNotNull(expertDTOs);
        assertEquals(2, expertDTOs.size());

        ExpertDTO expertDTO1 = expertDTOs.get(0);
        assertEquals("John", expertDTO1.getName());
        assertEquals("Doe", expertDTO1.getSurname());
        assertEquals("john.doe@example.com", expertDTO1.getEmail());
        assertEquals(100, expertDTO1.getScore());
        assertEquals(ExpertStatusEnum.APPROVED, expertDTO1.getExpertStatus());

        ExpertDTO expertDTO2 = expertDTOs.get(1);
        assertEquals("Jane", expertDTO2.getName());
        assertEquals("Smith", expertDTO2.getSurname());
        assertEquals("jane.smith@example.com", expertDTO2.getEmail());
        assertEquals(200, expertDTO2.getScore());
        assertEquals(ExpertStatusEnum.NEW, expertDTO2.getExpertStatus());
    }

    @Test
    public void testApproveExpert() {
        Long expertId = 1L;
        Expert expert = new Expert();
        expert.setId(expertId);
        expert.setExpertStatus(ExpertStatusEnum.NEW);

        when(expertRepository.findById(expertId)).thenReturn(Optional.of(expert));
        when(expertRepository.save(any(Expert.class))).thenReturn(expert);

        Expert approvedExpert = expertService.approveExpert(expertId);

        assertNotNull(approvedExpert);
        assertEquals(ExpertStatusEnum.APPROVED, approvedExpert.getExpertStatus());
    }

    @Test
    public void testSaveOrUpdateWithDuplicatedEmail() {
        // Arrange
        Expert expert = new Expert();
        expert.setId(1L);
        expert.setEmail("duplicate@example.com");

        when(expertRepository.save(Mockito.any(Expert.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolationException.class, () -> expertService.saveOrUpdate(expert));
    }

    @Test
    public void testDisplayImage() throws IOException {
        ExpertDTO expertDTO = new ExpertDTO();

        byte[] imageBytes = Files.readAllBytes(Paths.get("C:\\Users\\Mitra\\Downloads\\photo-test\\correct.jpg"));

        expertDTO.displayImage(imageBytes);
    }
}
