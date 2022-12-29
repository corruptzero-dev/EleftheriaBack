package ru.corruptzero.eleftheriaback.unit.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import ru.corruptzero.eleftheriaback.exception.*;
import ru.corruptzero.eleftheriaback.exception.handler.EntityExceptionHandler;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EntityExceptionHandlerTest {

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private EntityExceptionHandler exceptionHandler;

    @Test
    public void testHandleNotFoundEntity() {
        // Arrange
        EntityNotFoundException ex = new EntityNotFoundException("Entity not found");

        // Act
        ResponseEntity<ApiError> response = exceptionHandler.handleNotFoundEntity(ex);

        // Assert
        ApiError apiError = response.getBody();
        assert apiError != null;
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, apiError.getStatus());
        assertEquals("Entity not found", apiError.getMessage());
    }

    @Test
    public void testHandleInvalidEntity() {
        // Arrange
        when(bindingResult.getAllErrors()).thenReturn(Collections.emptyList());
        InvalidEntityException ex = new InvalidEntityException("Invalid entity");

        // Act
        ResponseEntity<ApiError> response = exceptionHandler.handleInvalidEntity(ex, bindingResult);

        // Assert
        ApiError apiError = response.getBody();
        assert apiError != null;
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, apiError.getStatus());
        assertEquals("Invalid entity", apiError.getMessage());
    }

    @Test
    public void testHandleEntityDelete() {
        // Arrange
        EntityDeleteException ex = new EntityDeleteException("Error deleting entity");

        // Act
        ResponseEntity<ApiError> response = exceptionHandler.handleEntityDelete(ex);

        // Assert
        ApiError apiError = response.getBody();
        assert apiError != null;
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, apiError.getStatus());
        assertEquals("Error deleting entity", apiError.getMessage());
    }

    @Test
    public void testHandleEmptyEntityList() {
        // Arrange
        EmptyEntityListException ex = new EmptyEntityListException("No entities found");

        // Act
        ResponseEntity<ApiError> response = exceptionHandler.handleEmptyEntityList(ex);

        // Assert
        ApiError apiError = response.getBody();
        assert apiError != null;
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(HttpStatus.NO_CONTENT, apiError.getStatus());
        assertEquals("No entities found", apiError.getMessage());
    }

    @Test
    public void testHandleAllExceptions() {
        // Arrange
        Exception ex = new Exception("An unhandled exception occurred");

        // Act
        ResponseEntity<ApiError> response = exceptionHandler.handleAllExceptions(ex);

        // Assert
        ApiError apiError = response.getBody();
        assert apiError != null;
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, apiError.getStatus());
        assertEquals("An unhandled exception occurred", apiError.getMessage());
    }
}
