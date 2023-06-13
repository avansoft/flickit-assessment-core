package org.flickit.flickitassessmentcore.application.service.assessment;


import org.flickit.flickitassessmentcore.application.port.in.assessment.CreateAssessmentCommand;
import org.flickit.flickitassessmentcore.application.port.out.assessment.CheckAssessmentUniqueConstraintPort;
import org.flickit.flickitassessmentcore.application.port.out.assessment.CreateAssessmentPort;
import org.flickit.flickitassessmentcore.application.port.out.assessmentcolor.CheckAssessmentColorExistencePort;
import org.flickit.flickitassessmentcore.application.service.exception.ResourceNotFoundException;
import org.flickit.flickitassessmentcore.application.service.exception.UniqueConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateAssessmentServiceTest {

    @Spy
    @InjectMocks
    private CreateAssessmentService createAssessmentService;
    @Mock
    private CreateAssessmentPort createAssessmentPort;
    @Mock
    private CheckAssessmentColorExistencePort checkColorExistencePort;
    @Mock
    private CheckAssessmentUniqueConstraintPort assessmentUniqueConstraintPort;

    @Test
    void createAssessment_ValidCommand_PersistsAndReturnsId() {
        CreateAssessmentCommand command = createValidCommand();
        UUID expectedId = UUID.randomUUID();
        doReturn(expectedId).when(createAssessmentPort).persist(any(CreateAssessmentPort.Param.class));
        doReturn(true).when(checkColorExistencePort).isColorIdExist(anyLong());
        doReturn(false).when(assessmentUniqueConstraintPort).checkTitleAndSpaceIdUniqueConstraint(anyString(), anyLong());
        doReturn(false).when(assessmentUniqueConstraintPort).checkCodeAndSpaceIdUniqueConstraint(anyString(), anyLong());

        UUID actualId = createAssessmentService.createAssessment(command);

        assertEquals(expectedId, actualId);
        verify(createAssessmentPort, times(1)).persist(any(CreateAssessmentPort.Param.class));
        verify(checkColorExistencePort, times(1)).isColorIdExist(anyLong());
    }

    @Test
    void createAssessment_InvalidColor_ThrowsException() {
        CreateAssessmentCommand command = createValidCommand();
        doReturn(false).when(checkColorExistencePort).isColorIdExist(anyLong());
        doReturn(false).when(assessmentUniqueConstraintPort).checkTitleAndSpaceIdUniqueConstraint(anyString(), anyLong());

        assertThrows(ResourceNotFoundException.class, () -> {
            createAssessmentService.createAssessment(command);
        });
        verify(createAssessmentPort, never()).persist(any(CreateAssessmentPort.Param.class));
    }

    @Test
    void createAssessment_DuplicateTitleWithinSpace_ThrowsException() {
        CreateAssessmentCommand command = createValidCommand();
        doReturn(true).when(assessmentUniqueConstraintPort).checkTitleAndSpaceIdUniqueConstraint(anyString(), anyLong());

        assertThrows(UniqueConstraintViolationException.class, () -> {
            createAssessmentService.createAssessment(command);
        });
        verify(createAssessmentPort, never()).persist(any(CreateAssessmentPort.Param.class));
    }

    @Test
    void createAssessment_DuplicateCodeWithinSpace_ThrowsException() {
        CreateAssessmentCommand command = createValidCommand();
        doReturn(true).when(checkColorExistencePort).isColorIdExist(anyLong());
        doReturn(false).when(assessmentUniqueConstraintPort).checkTitleAndSpaceIdUniqueConstraint(anyString(), anyLong());
        doReturn(true).when(assessmentUniqueConstraintPort).checkCodeAndSpaceIdUniqueConstraint(anyString(), anyLong());

        assertThrows(UniqueConstraintViolationException.class, () -> {
            createAssessmentService.createAssessment(command);
        });
        verify(createAssessmentPort, never()).persist(any(CreateAssessmentPort.Param.class));
    }


    @Test
    void generateSlugCode_NoWhitespace_ReturnsLowerCaseCode() {
        String title = "ExampleTitle";

        String code = ReflectionTestUtils.invokeMethod(createAssessmentService, "generateSlugCode", title);

        assertEquals("exampletitle", code);
    }

    @Test
    void generateSlugCode_WithWhitespace_ReturnsLowerCaseCodeWithHyphens() {
        String title = "Example Title with Whitespace";

        String code = ReflectionTestUtils.invokeMethod(createAssessmentService, "generateSlugCode", title);

        assertEquals("example-title-with-whitespace", code);
    }

    @Test
    void generateSlugCode_WithLeadingAndTrailingWhitespace_ReturnsLowerCaseCodeWithHyphens() {
        String title = "  Example Title with   Leading and Trailing   Whitespace  ";

        String code = ReflectionTestUtils.invokeMethod(createAssessmentService, "generateSlugCode", title);

        assertEquals("example-title-with-leading-and-trailing-whitespace", code);
    }


    private static CreateAssessmentCommand createValidCommand() {
        return new CreateAssessmentCommand(
            "title example",
            1L,
            1L,
            1L
        );
    }

}