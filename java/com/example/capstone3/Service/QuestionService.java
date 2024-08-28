package com.example.capstone3.Service;

import com.example.capstone3.Api.ApiException;
import com.example.capstone3.Model.Question;
import com.example.capstone3.Repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> getAllQuestion()
    {
        return questionRepository.findAll();
    }

    public void updateQuestion(Integer id,Question question)
    {
        Question q = questionRepository.findQuestionById(id);
        if(q == null)
        {
            throw new ApiException("Question not found");
        }
        q.setTheQuestion(question.getTheQuestion());
        q.setAnswer(question.getAnswer());
        questionRepository.save(q);
    }
    public void deleteQuestion(Integer id)
    {
        if(questionRepository.findQuestionById(id) == null)
        {
            throw new ApiException("Question not found");
        }
        questionRepository.deleteById(id);
    }
}