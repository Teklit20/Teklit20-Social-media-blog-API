package Service;
import DAO.MessageDAO;
import Model.Message;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO = new MessageDAO();

    public Message postMessage(Message message) {
        if (message.getMessage_text() != null && !message.getMessage_text().isEmpty() &&
            message.getMessage_text().length() < 255) {
            return messageDAO.createMessage(message);
        }
        return null;
    }

 
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int messageId) {
        return messageDAO.getMessageById(messageId);
    }
    

    public Boolean deleteMessageById(int messageId) {
        return messageDAO.deleteMessageById(messageId);
    }

    public Message updateMessageById(int messageId, Message updatedMessage) {
        return messageDAO.updateMessageById(messageId, updatedMessage);
    }

    public List<Message> getMessagesByUserId(int userId) {
        return messageDAO.getMessagesByUserId(userId);
    }

    
}
