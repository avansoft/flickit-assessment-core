package org.flickit.flickitassessmentcore.application.service.assessment;


import org.flickit.flickitassessmentcore.application.port.in.assessment.CreateAssessmentUseCase;
import org.flickit.flickitassessmentcore.application.port.in.assessment.CreateAssessmentUseCase.Param;
import org.flickit.flickitassessmentcore.application.port.out.assessment.CreateAssessmentPort;
import org.flickit.flickitassessmentcore.application.port.out.assessmentresult.CreateAssessmentResultPort;
import org.flickit.flickitassessmentcore.application.port.out.subject.LoadSubjectIdsAndQualityAttributeIdsPort;
import org.flickit.flickitassessmentcore.application.port.out.subjectvalue.CreateSubjectValuePort;
import org.flickit.flickitassessmentcore.domain.AssessmentColor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateAssessmentServiceTest {

    @Spy
    @InjectMocks
    private CreateAssessmentService service;

    @Mock
    private CreateAssessmentPort createAssessmentPort;

    @Mock
    private CreateAssessmentResultPort createAssessmentResultPort;

    @Mock
    private LoadSubjectIdsAndQualityAttributeIdsPort loadASIdsAndQAIdsPort;

    @Mock
    private CreateSubjectValuePort createSubjectValuePort;

    @Mock
    private org.flickit.flickitassessmentcore.application.port.out.qualityattributevalue.CreateQualityAttributeValuePort createQualityAttributeValuePort;


    @Test
    void createAssessment_ValidParam_PersistsAndReturnsId() {
        Param param = new Param(
            1L,
            "title example",
            1L,
            1
        );
        UUID expectedId = UUID.randomUUID();
        when(createAssessmentPort.persist(any(CreateAssessmentPort.Param.class))).thenReturn(expectedId);
        LoadSubjectIdsAndQualityAttributeIdsPort.ResponseParam expectedResponseParam =
            new LoadSubjectIdsAndQualityAttributeIdsPort.ResponseParam(Arrays.asList(), Arrays.asList());
        when(loadASIdsAndQAIdsPort.loadByAssessmentKitId(any())).thenReturn(expectedResponseParam);

        CreateAssessmentUseCase.Result result = service.createAssessment(param);
        assertEquals(expectedId, result.id());

        ArgumentCaptor<CreateAssessmentPort.Param> createPortParam = ArgumentCaptor.forClass(CreateAssessmentPort.Param.class);
        verify(createAssessmentPort).persist(createPortParam.capture());

        assertEquals(param.getTitle(), createPortParam.getValue().title());
        assertEquals(param.getAssessmentKitId(), createPortParam.getValue().assessmentKitId());
        assertEquals(param.getColorId(), createPortParam.getValue().colorId());
        assertEquals("title-example", createPortParam.getValue().code());
        assertNotNull(createPortParam.getValue().creationTime());
        assertNotNull(createPortParam.getValue().lastModificationDate());
    }

    @Test
    void createAssessment_ValidParam_PersistsAssessmentResult() {
        Param param = new Param(
            1L,
            "title example",
            1L,
            1
        );
        UUID assessmentId = UUID.randomUUID();
        when(createAssessmentPort.persist(any(CreateAssessmentPort.Param.class))).thenReturn(assessmentId);
        UUID expectedResultId = UUID.randomUUID();
        when(createAssessmentResultPort.persist(any(CreateAssessmentResultPort.Param.class))).thenReturn(expectedResultId);
        LoadSubjectIdsAndQualityAttributeIdsPort.ResponseParam expectedResponseParam =
            new LoadSubjectIdsAndQualityAttributeIdsPort.ResponseParam(Arrays.asList(), Arrays.asList());
        when(loadASIdsAndQAIdsPort.loadByAssessmentKitId(any())).thenReturn(expectedResponseParam);

        service.createAssessment(param);

        ArgumentCaptor<CreateAssessmentResultPort.Param> createPortParam = ArgumentCaptor.forClass(CreateAssessmentResultPort.Param.class);
        verify(createAssessmentResultPort).persist(createPortParam.capture());

        assertEquals(assessmentId, createPortParam.getValue().assessmentId());
        assertNotNull(createPortParam.getValue().lastModificationTime());
        assertFalse(createPortParam.getValue().isValid());
    }

    @Test
    void createAssessment_ValidParam_PersistsSubjectValues() {
        Long assessmentKitId = 1L;
        Param param = new Param(
            1L,
            "title example",
            assessmentKitId,
            1
        );
        List<Long> expectedSubjectIds = Arrays.asList(1L, 2L, 3L);
        List<Long> expectedQualityAttributeIds = Arrays.asList(1L, 2L, 3L, 4L, 5L);
        LoadSubjectIdsAndQualityAttributeIdsPort.ResponseParam expectedResponseParam =
            new LoadSubjectIdsAndQualityAttributeIdsPort.ResponseParam(expectedSubjectIds, expectedQualityAttributeIds);
        when(loadASIdsAndQAIdsPort.loadByAssessmentKitId(assessmentKitId)).thenReturn(expectedResponseParam);

        service.createAssessment(param);

        verify(createSubjectValuePort, times(1)).persistAll(anyList(), any());
    }

    @Test
    void createAssessment_ValidCommand_PersistsQualityAttributeValue() {
        Long assessmentKitId = 1L;
        Param param = new Param(
            1L,
            "title example",
            assessmentKitId,
            1
        );
        List<Long> expectedSubjectIds = Arrays.asList(1L, 2L, 3L);
        List<Long> expectedQualityAttributeIds = Arrays.asList(1L, 2L, 3L, 4L, 5L);
        LoadSubjectIdsAndQualityAttributeIdsPort.ResponseParam expectedResponseParam =
            new LoadSubjectIdsAndQualityAttributeIdsPort.ResponseParam(expectedSubjectIds, expectedQualityAttributeIds);
        when(loadASIdsAndQAIdsPort.loadByAssessmentKitId(assessmentKitId)).thenReturn(expectedResponseParam);

        service.createAssessment(param);

        verify(createQualityAttributeValuePort, times(1)).persistAll(anyList(), any());
    }

    @Test
    void createAssessment_NullColor_UseDefaultColor() {
        Param param = new Param(
            1L,
            "title example",
            1L,
            null
        );
        LoadSubjectIdsAndQualityAttributeIdsPort.ResponseParam expectedResponseParam =
            new LoadSubjectIdsAndQualityAttributeIdsPort.ResponseParam(Arrays.asList(), Arrays.asList());
        when(loadASIdsAndQAIdsPort.loadByAssessmentKitId(any())).thenReturn(expectedResponseParam);

        service.createAssessment(param);

        ArgumentCaptor<CreateAssessmentPort.Param> createPortParam = ArgumentCaptor.forClass(CreateAssessmentPort.Param.class);
        verify(createAssessmentPort).persist(createPortParam.capture());

        assertEquals(AssessmentColor.getDefault().getId(), createPortParam.getValue().colorId());
    }

    @Test
    void createAssessment_InvalidColor_UseDefaultColor() {
        Param param = new Param(
            1L,
            "title example",
            1L,
            7
        );
        LoadSubjectIdsAndQualityAttributeIdsPort.ResponseParam expectedResponseParam =
            new LoadSubjectIdsAndQualityAttributeIdsPort.ResponseParam(Arrays.asList(), Arrays.asList());
        when(loadASIdsAndQAIdsPort.loadByAssessmentKitId(any())).thenReturn(expectedResponseParam);

        service.createAssessment(param);

        ArgumentCaptor<CreateAssessmentPort.Param> createPortParam = ArgumentCaptor.forClass(CreateAssessmentPort.Param.class);
        verify(createAssessmentPort).persist(createPortParam.capture());

        assertEquals(AssessmentColor.getDefault().getId(), createPortParam.getValue().colorId());
    }

    @Test
    void generateSlugCode_NoWhitespace_ReturnsLowerCaseCode() {
        String title = "ExampleTitle";

        String code = ReflectionTestUtils.invokeMethod(service, "generateSlugCode", title);

        assertEquals("exampletitle", code);
    }

    @Test
    void generateSlugCode_WithWhitespace_ReturnsLowerCaseCodeWithHyphens() {
        String title = "Example Title with Whitespace";

        String code = ReflectionTestUtils.invokeMethod(service, "generateSlugCode", title);

        assertEquals("example-title-with-whitespace", code);
    }

    @Test
    void generateSlugCode_WithLeadingAndTrailingWhitespace_ReturnsLowerCaseCodeWithHyphens() {
        String title = "  Example Title with   Leading and Trailing   Whitespace  ";

        String code = ReflectionTestUtils.invokeMethod(service, "generateSlugCode", title);

        assertEquals("example-title-with-leading-and-trailing-whitespace", code);
    }

}
