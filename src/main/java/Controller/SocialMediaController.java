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
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin //app object which defines the behavior of the Javalin controller.
     */

     MessageService messageService;
     AccountService accountService;
     private ObjectMapper mapper = new ObjectMapper();

     public SocialMediaController(){
        this.messageService = new MessageService();
        this.accountService = new AccountService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::registerUserHandler);
        app.post("/login", this::verifyLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIDHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIDHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIDHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByAccountIDHandler);
       
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerUserHandler(Context ctx) throws JsonMappingException, JsonProcessingException {

        
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account registeredUser = accountService.addAccount(account);

        if(registeredUser!=null){
            ctx.json(mapper.writeValueAsString(registeredUser));
        }else{
            ctx.status(400);
        }
         
    
    }

    private void verifyLoginHandler(Context ctx) throws JsonMappingException, JsonProcessingException {

        
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account signedInUser = accountService.loginUser(account);

        if(signedInUser!=null){
            ctx.json(mapper.writeValueAsString(signedInUser));
        }else{
            ctx.status(401);
        }
         
    
    }

    private void postMessageHandler(Context ctx) throws JsonMappingException, JsonProcessingException{
        
        ObjectMapper mapper = new ObjectMapper();
        Message msg = mapper.readValue(ctx.body(), Message.class);
        Message newMsg = messageService.addMessage(msg);
        if(newMsg != null){
            ctx.json(mapper.writeValueAsString(newMsg));
        }else{
            ctx.status(400);
        }}
   
    private void getAllMessagesHandler(Context ctx) throws JsonMappingException, JsonProcessingException {

            ctx.json(messageService.getAllMessages());
        }
    

        private void getMessageByIDHandler(Context ctx) throws JsonMappingException, JsonProcessingException{
            String id = ctx.pathParam("message_id");
            Message msg = messageService.getMessageByID(Integer.parseInt(id));
        if(msg != null){
            ctx.json(msg);
        }else{
            ctx.status(200);
        }
            
        }


        private void deleteMessageByIDHandler(Context ctx) throws JsonMappingException, JsonProcessingException {

            String id = ctx.pathParam("message_id");
            Message msg = messageService.deleteMessageById(Integer.parseInt(id));
            if(msg != null){
                ctx.json(mapper.writeValueAsString(msg));
            }else{
                ctx.status(200);
            }
            }

            private void updateMessageByIDHandler(Context ctx) throws JsonMappingException, JsonProcessingException{

                ObjectMapper mapper = new ObjectMapper();
                JsonNode json = mapper.readTree(ctx.body());

                int message_id = Integer.parseInt(ctx.pathParam("message_id"));
                String newMsgText = json.get("message_text").asText();
                Message updatedMessage = messageService.updateMsgById(message_id, newMsgText);
        
                if(updatedMessage == null){
                    ctx.status(400);
                }else{
                    ctx.json(mapper.writeValueAsString(updatedMessage));
        }
            }

            private void getAllMessagesByAccountIDHandler(Context ctx) throws JsonMappingException, JsonProcessingException{
                
                int id = Integer.parseInt(ctx.pathParam("account_id"));
                List<Message> msgs = messageService.getAllMessagesByUser(id);
                
                if(msgs != null){
                    ctx.json(mapper.writeValueAsString(msgs));
                }else{
                    ctx.status(200);
                }
                
                }


        }


    

