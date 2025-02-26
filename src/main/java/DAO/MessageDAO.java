package DAO;
import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class MessageDAO {
    public Message createMessage(Message message) {
        String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, message.getPosted_by());
            stmt.setString(2, message.getMessage_text());
            stmt.setLong(3, message.getTime_posted_epoch());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    message.setMessage_id(rs.getInt(1)); //auto-generated message_id
                    return message;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }

    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM message";
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                messages.add(new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public Message getMessageById(int id) {
        try {
            Connection conn = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                        rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean deleteMessageById(int id) {
        try {
            Connection conn = ConnectionUtil.getConnection();
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
