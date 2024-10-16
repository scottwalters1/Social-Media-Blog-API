package Service;

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
}
