package com.example.simon.instantmessengerapp.core.rest.services;


import com.example.simon.instantmessengerapp.core.rest.AndroidFriendlyFeature;
import com.example.simon.instantmessengerapp.core.rest.classes.JSONGroup;
import com.example.simon.instantmessengerapp.core.rest.classes.JSONMessage;
import com.example.simon.instantmessengerapp.core.rest.exceptions.GroupDoesNotExistException;
import com.example.simon.instantmessengerapp.core.rest.exceptions.UserOrGroupDoesNotExistException;
import com.example.simon.instantmessengerapp.model.classes.*;
import com.example.simon.instantmessengerapp.model.interfaces.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.example.simon.instantmessengerapp.core.rest.constants.GeneralRestConstants.ERR_INTERNAL_SERVER_ERROR;
import static com.example.simon.instantmessengerapp.core.rest.constants.GroupRestConstants.*;

/**
 * class representing the client rest interface to send server requests for groups
 */                                //implements com.example.simon.instantmessengerapp.core.rest.interfaces.GroupRestClient
public class GroupRestClientImpl  {

    private static final String URL = "http://192.168.178.28:4434";
    private static final String WEB_CONTEXT_PATH = "/im";
    private static final String GROUPS_PATH = "groups";
    private static final String USERS_PATH = "users";
    private static final String MESSAGES_PATH = "messages";

    private Client client;
    private Response response;
    private Gson gSon;

    /**
     * default constructor
     */
    public GroupRestClientImpl() {
        client = ClientBuilder.newClient().register(AndroidFriendlyFeature.class);
        response = null;
    }

    /**
     * convert a json string to Group object
     *
     * @param json String to convert
     * @return Group object
     */
    private Group makeGroupFromJSON(String json) {
        GsonBuilder b = new GsonBuilder();
        Gson gson = b.create();
        return gson.fromJson(json, GroupImpl.class);
    }

    /**
     * convert a json string to a Message object
     *
     * @param json String to convert
     * @return Group object
     */
    private ArrayList<Message> makeMessageListFromJSON(String json) {
        Type listType = new TypeToken<ArrayList<MessageImpl>>() {
        }.getType();
        GsonBuilder b = new GsonBuilder();
        Gson gson = b.create();
        return gson.fromJson(json, listType);
    }

    /**
     * send a requst to add a new group
     *
     * @param gro new Group object to add
     * @return added Group object
     */
    public Group addGroup(Group gro) {
        gSon = new GsonBuilder().create();
        JSONGroup g = new JSONGroup();
        g.name = gro.getGroupName();
        String json = gSon.toJson(g);

        try {
            response = client
                    .target(URL + WEB_CONTEXT_PATH)
                    .path(GROUPS_PATH)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(json));

            if (response.getStatus() == 500) {
                throw new RuntimeException(ERR_INTERNAL_SERVER_ERROR + ": " + response.getStatus());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        String respJson = response.readEntity(String.class);

        return makeGroupFromJSON(respJson);
    }

    /**
     * send a delete request for group with given id
     *
     * @param groupId groupId to indentify group
     */
    public void removeGroup(final String groupId) {

        try {
            response = client
                    .target(URL + WEB_CONTEXT_PATH)
                    .path(GROUPS_PATH)
                    .path(groupId)
                    .request(MediaType.APPLICATION_JSON)
                    .delete();

            if (response.getStatus() == 404) {
                throw new GroupDoesNotExistException(ERR_GROUP_DOES_NOT_EXIST);

            } else if (response.getStatus() == 500) {
                throw new RuntimeException(ERR_INTERNAL_SERVER_ERROR + ": " + response.getStatus());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * get a list of all users in a group
     *
     * @param groupId
     * @return ArrayList with userIds
     */
    public ArrayList<Integer> getUsersOfGroup(final String groupId) {

        try {
            response = client
                    .target(URL + WEB_CONTEXT_PATH)
                    .path(GROUPS_PATH)
                    .path(groupId)
                    .path(USERS_PATH)
                    .request(MediaType.APPLICATION_JSON)
                    .get(Response.class);


            if (response.getStatus() == 404) {
                throw new GroupDoesNotExistException(ERR_GROUP_DOES_NOT_EXIST);

            } else if (response.getStatus() == 500) {
                throw new RuntimeException(ERR_INTERNAL_SERVER_ERROR + ": " + response.getStatus());

            }

        } catch (Exception e) {
            e.printStackTrace();
            // group does not exist, so return null
            return null;
        }

        String json = (String) response.readEntity(String.class);
        gSon = new GsonBuilder().create();

        return gSon.fromJson(json, new TypeToken<ArrayList<Integer>>() {
        }.getType());
    }

    /**
     * send request to add new user to group
     *
     * @param groupId group to add user to
     * @param userId  user to add
     */
    public void addUserToGroup(final String groupId, final String userId) {

        try {
            response = client
                    .target(URL + WEB_CONTEXT_PATH)
                    .path(GROUPS_PATH)
                    .path(groupId)
                    .path(USERS_PATH)
                    .path(userId)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(null));

            if (response.getStatus() == 404) {
                throw new UserOrGroupDoesNotExistException(ERR_USER_GROUP_DOES_NOT_EXIST);

            } else if (response.getStatus() == 500) {
                throw new RuntimeException(ERR_INTERNAL_SERVER_ERROR + ": " + response.getStatus());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * send request to remove user from group
     *
     * @param groupId group to remove user from
     * @param userId  user to remove
     */
    public void removeUserFromGroup(final String groupId, final String userId) {

        try {
            response = client
                    .target(URL + WEB_CONTEXT_PATH)
                    .path(GROUPS_PATH)
                    .path(groupId)
                    .path(USERS_PATH)
                    .path(userId)
                    .request(MediaType.APPLICATION_JSON)
                    .delete();

            if (response.getStatus() == 404) {
                throw new UserOrGroupDoesNotExistException(ERR_USER_GROUP_DOES_NOT_EXIST);

            } else if (response.getStatus() == 500) {
                throw new RuntimeException(ERR_INTERNAL_SERVER_ERROR + ": " + response.getStatus());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * send request to get a group by id
     *
     * @param groupId id of group to get
     * @return Group object
     */
    public Group getGroupById(final String groupId) {

        try {
            response = client
                    .target(URL + WEB_CONTEXT_PATH)
                    .path(GROUPS_PATH)
                    .path(groupId)
                    .request(MediaType.APPLICATION_JSON)
                    .get(Response.class);


            if (response.getStatus() == 404) {
                throw new GroupDoesNotExistException(ERR_GROUP_DOES_NOT_EXIST);

            } else if (response.getStatus() == 500) {
                throw new RuntimeException(ERR_INTERNAL_SERVER_ERROR + ": " + response.getStatus());

            }

        } catch (Exception e) {
            e.printStackTrace();
            // group does not exist, so return null
            return null;
        }

        String json = (String) response.readEntity(String.class);
        gSon = new GsonBuilder().create();

        return gSon.fromJson(json, GroupImpl.class);
    }

    /**
     * send request to change a group
     *
     * @param groupId group to change
     * @param group   new changes Group object
     */
    public void changeGroup(final String groupId, Group group) {
        gSon = new GsonBuilder().create();
        JSONGroup g = new JSONGroup();
        g.name = group.getGroupName();
        String json = gSon.toJson(g);

        try {
            response = client
                    .target(URL + WEB_CONTEXT_PATH)
                    .path(GROUPS_PATH)
                    .path(groupId)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(json));

            if (response.getStatus() == 404) {
                throw new GroupDoesNotExistException(ERR_GROUP_DOES_NOT_EXIST);

            } else if (response.getStatus() == 500) {
                throw new RuntimeException(ERR_INTERNAL_SERVER_ERROR + ": " + response.getStatus());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * send request to get all messages within a group
     *
     * @param groupId group to get messages from
     * @param page    select which page of messages to get
     * @return ArrayList of Messages in group
     */
    public ArrayList<Message> getMessagesOfGroup(final String groupId, final String page) {
        try {
            response = client
                    .target(URL + WEB_CONTEXT_PATH)
                    .path(GROUPS_PATH)
                    .path(groupId)
                    .path(MESSAGES_PATH)
                    //.queryParam("page", page)
                    .request(MediaType.APPLICATION_JSON)
                    .get(Response.class);


            if (response.getStatus() == 404) {
                throw new GroupDoesNotExistException(ERR_GROUP_DOES_NOT_EXIST);

            } else if (response.getStatus() == 500) {
                throw new RuntimeException(ERR_INTERNAL_SERVER_ERROR + ": " + response.getStatus());

            }

        } catch (Exception e) {
            e.printStackTrace();
            // group does not exist, so return null
            return null;
        }

        String respJson = (String) response.readEntity(String.class);

        //return gSon.fromJson(json, new TypeToken<ArrayList<Message>>(){}.getType()); //TODO: Funktioniert so nicht! wirft eine Exception

        return makeMessageListFromJSON(respJson);
    }

    /**
     * send request to post a message
     *
     * @param msg     message to post
     */
    public void postMessage(Message msg) {
        gSon = new GsonBuilder().create();
        JSONMessage m = new JSONMessage();
        m.content = msg.getContent();
        m.userid = msg.getUser();
        String json = gSon.toJson(m);

        try {
            response = client
                    .target(URL + WEB_CONTEXT_PATH)
                    .path(GROUPS_PATH)
                    .path(Integer.toString(msg.getGroup()))
                    .path(MESSAGES_PATH)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(json)); // TODO geht so nicht

            if (response.getStatus() == 404) {
                throw new GroupDoesNotExistException(ERR_GROUP_DOES_NOT_EXIST);

            } else if (response.getStatus() == 500) {
                throw new RuntimeException(ERR_INTERNAL_SERVER_ERROR + ": " + response.getStatus());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}






