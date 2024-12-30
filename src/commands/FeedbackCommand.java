package commands;

import model.Feedback;
import service.FeedbackService;

public class FeedbackCommand implements Command {
    private final FeedbackService feedbackService;
    private final Feedback feedback;

    public FeedbackCommand(FeedbackService feedbackService, Feedback feedback) {
        this.feedbackService = feedbackService;
        this.feedback = feedback;
    }

    @Override
    public void execute() {
        feedbackService.submitFeedback(feedback);
        System.out.println("Feedback submitted: " + feedback);
    }

    @Override
    public void undo() {
        feedbackService.deleteFeedback(feedback);
        System.out.println("Feedback deleted: " + feedback);
    }
}
