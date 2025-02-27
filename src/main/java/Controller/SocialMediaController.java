package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Service.*;
import Model.*;
import java.util.Optional;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private AccountService accountService = new AccountService();
    private MessageService messageService = new MessageService();
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        //app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::registerAccount);
        app.post("/login", this::loginUser);
        app.post("/messages", this::createMessage);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{id}", this::getMessageById);
        app.delete("/messages/{id}", this::deleteMessageById);
        app.patch("/messages/{id}", this::updateMessage);
        app.get("/accounts/{accountId}/messages", this::getMessagesByUser);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerAccount(Context ctx) {
        Account account = ctx.bodyAsClass(Account.class);
        Account createdAccount = accountService.registerAccount(account);
        if (createdAccount != null) {
            ctx.json(createdAccount);
        } else {
            ctx.status(400);
        }
    }
    private void loginUser(Context ctx) {
        Account loginRequest = ctx.bodyAsClass(Account.class);
    Account account = accountService.login(loginRequest.getUsername(), loginRequest.getPassword());

    if (account == null) {
        ctx.status(401).result(""); // Return empty body for invalid login
    } else {
        ctx.status(200).json(account);
    }
    }

    private void createMessage(Context ctx) {
        Message message = ctx.bodyAsClass(Message.class);
        Message createdMessage = messageService.postMessage(message);
        if (createdMessage != null) {
            ctx.json(createdMessage);
        } else {
            ctx.status(400).result("Invalid message data.");
        }
    }

    private void getAllMessages(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void getMessageById(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("id"));
        Message message = messageService.getMessageById(messageId);
        if (message != null) {
            ctx.json(message);
        } else {
            ctx.status(404).result("Message not found.");
        }
    }

    private void deleteMessageById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Boolean deletedMessage = messageService.deleteMessageById(id);
        if (deletedMessage==true) {
            ctx.status(200).json(deletedMessage);
        } else {
           // ctx.status(200);
        }
    }

    private void updateMessage(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("id"));
        Message updatedMessage = ctx.bodyAsClass(Message.class);
        Message result = messageService.updateMessageById(messageId, updatedMessage);
        if (result != null) {
            ctx.json(result);
        } else {
            ctx.status(400).result("Update failed.");
        }
    }
   
    private void getMessagesByUser(Context ctx) {
        int accountId = Integer.parseInt(ctx.pathParam("accountId"));
        List<Message> messages = messageService.getMessagesByUserId(accountId);
        if (!messages.isEmpty()) {
            ctx.json(messages);
        } else {
            ctx.status(404).result("No messages found for this user.");
        }
    }
}