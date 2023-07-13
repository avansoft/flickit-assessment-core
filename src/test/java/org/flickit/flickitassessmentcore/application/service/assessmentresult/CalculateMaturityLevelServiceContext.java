package org.flickit.flickitassessmentcore.application.service.assessmentresult;

import lombok.Getter;
import org.flickit.flickitassessmentcore.Constants;
import org.flickit.flickitassessmentcore.Utils;
import org.flickit.flickitassessmentcore.domain.*;

@Getter
public class CalculateMaturityLevelServiceContext {

    private final Assessment assessment;
    private final AssessmentResult result;
    private final AssessmentKit kit;
    private final AssessmentSubject subject;
    private final SubjectValue subjectValue;
    private final Questionnaire questionnaire;
    private final QualityAttribute qualityAttribute;
    private final QualityAttributeValue qualityAttributeValue;
    private final Question question1;
    private final Question question2;
    private final QuestionImpact questionImpact1;
    private final QuestionImpact questionImpact2;
    private final Answer answer1;
    private final Answer answer2;
    private final AnswerOption option1Q1;
    private final AnswerOption option2Q1;
    private final AnswerOption option3Q1;
    private final AnswerOption option1Q2;
    private final AnswerOption option2Q2;
    private final AnswerOption option3Q2;
    private final AnswerOptionImpact optionImpact1Q1;
    private final AnswerOptionImpact optionImpact2Q1;
    private final AnswerOptionImpact optionImpact3Q1;
    private final AnswerOptionImpact optionImpact1Q2;
    private final AnswerOptionImpact optionImpact2Q2;
    private final AnswerOptionImpact optionImpact3Q2;
    private final MaturityLevel maturityLevel1;
    private final MaturityLevel maturityLevel2;
    private final MaturityLevel maturityLevel3;
    private final LevelCompetence levelCompetence1;
    private final LevelCompetence levelCompetence2;

    public CalculateMaturityLevelServiceContext() {
        assessment = Utils.createAssessment();
        kit = Utils.createAssessmentKit();
        result = Utils.createAssessmentResult();
        subject = Utils.createAssessmentSubject();
        subjectValue = Utils.createSubjectValue();
        questionnaire = Utils.createQuestionnaire();
        qualityAttribute = Utils.createQualityAttribute();
        qualityAttributeValue = Utils.createQualityAttributeValue();
        question1 = Utils.createQuestion(Constants.QUESTION_ID1, Constants.QUESTION_TITLE1, Constants.QUESTION_DESCRIPTION1);
        question2 = Utils.createQuestion(Constants.QUESTION_ID2, Constants.QUESTION_TITLE2, Constants.QUESTION_DESCRIPTION2);
        questionImpact1 = Utils.createQuestionImpact(Constants.QUESTION_IMPACT_ID1, Constants.QUESTION_IMPACT_LEVEL1, Constants.QUESTION_IMPACT_WEIGHT1);
        questionImpact2 = Utils.createQuestionImpact(Constants.QUESTION_IMPACT_ID2, Constants.QUESTION_IMPACT_LEVEL2, Constants.QUESTION_IMPACT_WEIGHT2);
        answer1 = Utils.createAnswer();
        answer2 = Utils.createAnswer();
        option1Q1 = Utils.createAnswerOption(Constants.ANSWER_OPTION_ID1, Constants.ANSWER_OPTION_CAPTION1, Constants.ANSWER_OPTION_VALUE1);
        option2Q1 = Utils.createAnswerOption(Constants.ANSWER_OPTION_ID2, Constants.ANSWER_OPTION_CAPTION2, Constants.ANSWER_OPTION_VALUE2);
        option3Q1 = Utils.createAnswerOption(Constants.ANSWER_OPTION_ID3, Constants.ANSWER_OPTION_CAPTION3, Constants.ANSWER_OPTION_VALUE3);
        option1Q2 = Utils.createAnswerOption(Constants.ANSWER_OPTION_ID4, Constants.ANSWER_OPTION_CAPTION4, Constants.ANSWER_OPTION_VALUE4);
        option2Q2 = Utils.createAnswerOption(Constants.ANSWER_OPTION_ID5, Constants.ANSWER_OPTION_CAPTION5, Constants.ANSWER_OPTION_VALUE5);
        option3Q2 = Utils.createAnswerOption(Constants.ANSWER_OPTION_ID6, Constants.ANSWER_OPTION_CAPTION6, Constants.ANSWER_OPTION_VALUE6);
        optionImpact1Q1 = Utils.createAnswerOptionImpact(Constants.ANSWER_OPTION_IMPACT_ID1, Constants.ANSWER_OPTION_IMPACT_VALUE1);
        optionImpact2Q1 = Utils.createAnswerOptionImpact(Constants.ANSWER_OPTION_IMPACT_ID2, Constants.ANSWER_OPTION_IMPACT_VALUE2);
        optionImpact3Q1 = Utils.createAnswerOptionImpact(Constants.ANSWER_OPTION_IMPACT_ID3, Constants.ANSWER_OPTION_IMPACT_VALUE3);
        optionImpact1Q2 = Utils.createAnswerOptionImpact(Constants.ANSWER_OPTION_IMPACT_ID4, Constants.ANSWER_OPTION_IMPACT_VALUE4);
        optionImpact2Q2 = Utils.createAnswerOptionImpact(Constants.ANSWER_OPTION_IMPACT_ID5, Constants.ANSWER_OPTION_IMPACT_VALUE5);
        optionImpact3Q2 = Utils.createAnswerOptionImpact(Constants.ANSWER_OPTION_IMPACT_ID6, Constants.ANSWER_OPTION_IMPACT_VALUE6);
        maturityLevel1 = Utils.createMaturityLevel(Constants.MATURITY_LEVEL_ID1, Constants.MATURITY_LEVEL_TITLE1, 1);
        maturityLevel2 = Utils.createMaturityLevel(Constants.MATURITY_LEVEL_ID2, Constants.MATURITY_LEVEL_TITLE2, 2);
        maturityLevel3 = Utils.createMaturityLevel(Constants.MATURITY_LEVEL_ID3, Constants.MATURITY_LEVEL_TITLE3, 3);
        levelCompetence1 = Utils.createLevelCompetence(Constants.LEVEL_COMPETENCE_ID1, Constants.LEVEL_COMPETENCE_VALUE1);
        levelCompetence2 = Utils.createLevelCompetence(Constants.LEVEL_COMPETENCE_ID2, Constants.LEVEL_COMPETENCE_VALUE2);
        this.completeInitiation();
    }

    private void completeInitiation() {
        assessment.setAssessmentKitId(kit.getId());

        result.setAssessment(assessment);

        subject.setAssessmentKit(kit);
        subject.getQuestionnaires().add(questionnaire);

        subjectValue.setAssessmentSubject(subject);

        questionnaire.setAssessmentKit(kit);

        qualityAttribute.setAssessmentSubject(subject);

        qualityAttributeValue.setQualityAttribute(qualityAttribute);

        question1.getQualityAttributes().add(qualityAttribute);
        question2.getQualityAttributes().add(qualityAttribute);

        questionImpact1.setQuestion(question1);
        questionImpact1.setMaturityLevelId(maturityLevel1.getId());
        questionImpact2.setQuestion(question2);
        questionImpact2.setMaturityLevelId(maturityLevel2.getId());

        answer1.setAssessmentResultId(result.getId());
        answer1.setQuestionId(question1.getId());
        answer1.setOptionId(option2Q1.getId());

        option1Q1.setQuestion(question1);
        option2Q1.setQuestion(question1);
        option3Q1.setQuestion(question1);

        optionImpact1Q1.setOptionId(option1Q1.getId());
        optionImpact1Q1.setQuestionImapctId(questionImpact1.getId());
        optionImpact2Q1.setOptionId(option2Q1.getId());
        optionImpact2Q1.setQuestionImapctId(questionImpact1.getId());
        optionImpact3Q1.setOptionId(option3Q1.getId());
        optionImpact3Q1.setQuestionImapctId(questionImpact1.getId());

        answer2.setAssessmentResultId(result.getId());
        answer2.setQuestionId(question2.getId());
        answer2.setOptionId(option1Q2.getId());

        option1Q2.setQuestion(question2);
        option2Q2.setQuestion(question2);
        option3Q2.setQuestion(question2);

        optionImpact1Q2.setOptionId(option1Q2.getId());
        optionImpact1Q2.setQuestionImapctId(questionImpact2.getId());
        optionImpact2Q2.setOptionId(option2Q2.getId());
        optionImpact2Q2.setQuestionImapctId(questionImpact2.getId());
        optionImpact3Q2.setOptionId(option3Q2.getId());
        optionImpact3Q2.setQuestionImapctId(questionImpact2.getId());

        maturityLevel1.setAssessmentKit(kit);
        maturityLevel2.setAssessmentKit(kit);

        levelCompetence1.setMaturityLevelId(maturityLevel2.getId());
        levelCompetence1.setMaturityLevelCompetenceId(maturityLevel1.getId());
        levelCompetence2.setMaturityLevelId(maturityLevel2.getId());
        levelCompetence2.setMaturityLevelCompetenceId(maturityLevel2.getId());
    }

}
