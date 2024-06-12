package app.repositories;

import app.models.PasswordResetToken;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Transactional
@ActiveProfiles("test")
class PasswordTokenRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private PasswordTokenRepository passwordTokenRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSave() {
        PasswordResetToken token = new PasswordResetToken();

        PasswordResetToken savedToken = passwordTokenRepository.save(token);

        assertNotNull(savedToken);
        verify(entityManager, times(1)).persist(eq(token));
        verifyNoMoreInteractions(entityManager);
    }

    @Test
    void testDeleteByUuid() {
        String uuid = "test_uuid";
        PasswordResetToken token = new PasswordResetToken();
        when(entityManager.find(PasswordResetToken.class, uuid)).thenReturn(token);

        ResponseEntity<PasswordResetToken> responseEntity = passwordTokenRepository.deleteByUuid(uuid);

        assertNotNull(responseEntity);
        assertEquals(token, responseEntity.getBody());
        verify(entityManager, times(1)).remove(token);
    }

    @Test
    void testFindByUuid() {
        String uuid = "test_uuid";
        PasswordResetToken token = new PasswordResetToken();
        when(entityManager.find(PasswordResetToken.class, uuid)).thenReturn(token);

        PasswordResetToken foundToken = passwordTokenRepository.findByUuid(uuid);

        assertNotNull(foundToken);
        assertEquals(token, foundToken);
    }
}
