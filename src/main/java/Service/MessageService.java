package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public Message createMessage(Message message) {
        if (message.getMessage_text().length() > 255 ||
                message.getMessage_text().equals("")) {
            return null;
        }
        return messageDAO.addMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessage(int message_id) {
        return messageDAO.getMessage(message_id);
    }

    public Message deleteMessage(int message_id) {
        return messageDAO.deleteMessage(message_id);
    }
}