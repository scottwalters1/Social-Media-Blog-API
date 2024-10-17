package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

/**
 * Service class for interaction between the controller and DAO.
 */
public class MessageService {
    private MessageDAO messageDAO;

    /**
     * No-args constructor.
     */
    public MessageService() {
        messageDAO = new MessageDAO();
    }

    /**
     * Create message method.
     * 
     * @param message
     * @return created message.
     */
    public Message createMessage(Message message) {
        if (message.getMessage_text().length() > 255 ||
                message.getMessage_text().equals("")) {
            return null;
        }
        return messageDAO.createMessage(message);
    }

    /**
     * Getter for all messages.
     * 
     * @return
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    /**
     * Getter for message given id.
     * 
     * @param message_id
     * @return
     */
    public Message getMessage(int message_id) {
        return messageDAO.getMessage(message_id);
    }

    /**
     * Method to delete a message.
     * 
     * @param message_id
     * @return deleted message
     */
    public Message deleteMessage(int message_id) {
        return messageDAO.deleteMessage(message_id);
    }

    /**
     * Method to update a message.
     * 
     * @param message_text
     * @param message_id
     * @return updated message.
     */
    public Message updateMessage(String message_text, int message_id) {
        if (message_text.length() > 255 ||
                message_text.equals("")) {
            return null;
        }
        return messageDAO.updateMessage(message_text, message_id);
    }

    /**
     * Getter for all messages from a particular user.
     * 
     * @param account_id
     * @return
     */
    public List<Message> getAllMessagesFromUser(int account_id) {
        return messageDAO.getAllMessagesFromUser(account_id);
    }
}