package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in
     * the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * 
     * @return a Javalin app object which defines the behavior of the Javalin
     *         controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.post("register", this::registerHandler);
        app.post("login", this::loginHandler);
        app.post("messages", this::createMessageHandler);
        app.get("messages", this::getAllMessageHandler);
        app.get("/messages/{message_id}", this::getMessageHandler);
        app.delete("/messages/{message_id}", this::deleteHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesFromUserHandler);

        return app;
    }

    /**
     * Handler for registering a new account.
     * 
     * @param ctx The Javalin Context object manages information about both the HTTP
     *            request and response.
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    private void registerHandler(Context ctx) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if (addedAccount != null) {
            ctx.json(mapper.writeValueAsString(addedAccount));
        } else {
            ctx.status(400);
        }
    }

    /**
     * Handler for verifying login information.
     * 
     * @param ctx The Javalin Context object manages information about both the HTTP
     *            request and response.
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    private void loginHandler(Context ctx) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account verifiedAccount = accountService.verifyAccount(account);
        if (verifiedAccount != null) {
            ctx.json(mapper.writeValueAsString(verifiedAccount));
        } else {
            ctx.status(401);
        }
    }

    /**
     * Handler for creating a new message.
     * 
     * @param ctx The Javalin Context object manages information about both the HTTP
     *            request and response.
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    private void createMessageHandler(Context ctx) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message createdMessage = messageService.createMessage(message);
        Account postedBy = accountService.getAccountById(message.posted_by);
        if (createdMessage != null && postedBy != null) {
            ctx.json(mapper.writeValueAsString(createdMessage));
        } else {
            ctx.status(400);
        }
    }

    /**
     * Handler for retrieving all messages.
     * 
     * @param ctx The Javalin Context object manages information about both the HTTP
     *            request and response.
     */
    private void getAllMessageHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    /**
     * Handler for retrieving a message given a message_id.
     * 
     * @param ctx The Javalin Context object manages information about both the HTTP
     *            request and response.
     */
    private void getMessageHandler(Context ctx) {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessage(message_id);
        if (message != null) {
            ctx.json(message);
        } else {
            ctx.status(200);
        }
    }

    /**
     * Handler for deleting a message.
     * 
     * @param ctx The Javalin Context object manages information about both the HTTP
     *            request and response.
     */
    private void deleteHandler(Context ctx) {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.deleteMessage(message_id);
        if (message != null) {
            ctx.json(message);
        } else {
            ctx.status(200);
        }
    }

    /**
     * Handler for updating the text of a message.
     * 
     * @param ctx The Javalin Context object manages information about both the HTTP
     *            request and response.
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    private void updateMessageHandler(Context ctx) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(ctx.body());
        String message_text = jsonNode.get("message_text").asText();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message updatedMessage = messageService.updateMessage(message_text, message_id);
        Message messageAlreadyPresent = messageService.getMessage(message_id);
        if (updatedMessage != null && messageAlreadyPresent != null) {
            ctx.json(updatedMessage);
        } else {
            ctx.status(400);
        }
    }

    /**
     * Handler for getting all messages from a particular user.
     * 
     * @param ctx The Javalin Context object manages information about both the HTTP
     *            request and response.
     */
    private void getAllMessagesFromUserHandler(Context ctx) {
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesFromUser(account_id);
        ctx.json(messages);
    }

}