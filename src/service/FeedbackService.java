package service;

import model.Feedback;

public interface FeedbackService {
    void submitFeedback(Feedback feedback);

    void deleteFeedback(Feedback feedback);
}
