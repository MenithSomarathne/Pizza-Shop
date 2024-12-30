package service.impl;

import model.Feedback;

public class FeedbackServiceImpl {

    public void submitFeedback(Feedback feedback) {
        System.out.println("Feedback submitted: " + feedback);
    }

    public void deleteFeedback(Feedback feedback) {
        System.out.println("Feedback deleted: " + feedback);
    }
}
