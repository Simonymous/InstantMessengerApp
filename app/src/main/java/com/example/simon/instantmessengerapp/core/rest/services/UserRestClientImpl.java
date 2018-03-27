package com.example.simon.instantmessengerapp.core.rest.services;

import com.example.simon.instantmessengerapp.core.rest.AndroidFriendlyFeature;
import com.example.simon.instantmessengerapp.core.rest.classes.UserQueryResponseImpl;
import com.example.simon.instantmessengerapp.core.rest.exceptions.UserAlreadyExistsException;
import com.example.simon.instantmessengerapp.core.rest.exceptions.UserDoesNotExistException;
import com.example.simon.instantmessengerapp.model.interfaces.User;
import com.example.simon.instantmessengerapp.model.classes.UserImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.Entity;

import java.util.ArrayList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.example.simon.instantmessengerapp.core.rest.constants.GeneralRestConstants.ERR_INTERNAL_SERVER_ERROR;
import static com.example.simon.instantmessengerapp.core.rest.constants.UserRestConstants.*;

/**
 * class representing the client rest interface to send server requests for users
 */                             //implements com.example.simon.instantmessengerapp.core.rest.interfaces.UserRestClient
public class UserRestClientImpl  {

    private static final String URL = "http://192.168.178.28:4434";
    private static final String WEB_CONTEXT_PATH = "/im";
    private static final String USERS_PATH = "users";
    private static final String GROUPS_PATH = "groups";
    private static final String INIT_PATH = "init";
    private static final String STOP_PATH = "stop";

    private Client client;
    private Response response;
    private Gson gSon;

    /**
     * default constructor
     */
    public UserRestClientImpl() {
        client = ClientBuilder.newClient().register(AndroidFriendlyFeature.class);
        response = null;
    }

    /**
     * convert a json string to OwnUser object
     *
     * @param json String to convert
     * @return OwnUser object
     */
    private User makeUserFromJSON(String json) {
        GsonBuilder b = new GsonBuilder();
        Gson gson = b.create();
        return gson.fromJson(json, UserImpl.class);
    }

//    /**
//     * send request to initate updater notify
//     *
//     * @return Response
//     */
//    public void initiateUpdate() {
//        gSon = new GsonBuilder().create();
//        //JSONClientAddress address = new JSONClientAddress(OwnAddress.getInstance().getPort(), OwnAddress.getInstance().getAddress());
//        //System.out.println("Initiate update-Listener on : "+ OwnAddress.getInstance().getAddress() + ":"+OwnAddress.getInstance().getPort());
////
//        String json = gSon.toJson(address);
//
//        try {
//            response = client
//                    .target(URL + WEB_CONTEXT_PATH)
//                    .path(USERS_PATH)
//                    .path(INIT_PATH)
//                    .request(MediaType.APPLICATION_JSON)
//                    .post(Entity.json(json));
//
//            if (response.getStatus() == 500) {
//                throw new RuntimeException(ERR_INTERNAL_SERVER_ERROR + ": " + response.getStatus());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    /**
//     * send request to stop updater notify
//     *
//     * @return Response
//     */
//    public void stopUpdate() {
//        gSon = new GsonBuilder().create();
//       //JSONClientAddress address = new JSONClientAddress(OwnAddress.getInstance().getPort(), OwnAddress.getInstance().getAddress());
//       // System.out.println("Shutdown update-Listener on : "+ OwnAddress.getInstance().getAddress() + ":"+OwnAddress.getInstance().getPort());
//
//       // String json = gSon.toJson(address);
//
//        try {
//            response = client
//                    .target(URL + WEB_CONTEXT_PATH)
//                    .path(USERS_PATH)
//                    .path(STOP_PATH)
//                    .request(MediaType.APPLICATION_JSON)
//                    .post(Entity.json(json));
//
//            if (response.getStatus() == 500) {
//                throw new RuntimeException(ERR_INTERNAL_SERVER_ERROR + ": " + response.getStatus());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    /**
     * send requst to change user
     *
     * @param id   user to change
     * @param user new user
     */
    public void updateUser(final String id, User user) {
        gSon = new GsonBuilder().create();
        String json = gSon.toJson(user);

        try {
            response = client
                    .target(URL + WEB_CONTEXT_PATH)
                    .path(USERS_PATH)
                    .path(id)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(json));

            if (response.getStatus() == 404) {
                throw new UserDoesNotExistException(ERR_USER_DOES_NOT_EXIST);

            } else if (response.getStatus() == 409) {
                throw new UserAlreadyExistsException(ERR_USER_ALREADY_EXISTS);

            } else if (response.getStatus() == 500) {
                throw new RuntimeException(ERR_INTERNAL_SERVER_ERROR + ": " + response.getStatus());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * send request to get a user object by name
     *
     * @param name - name of user to get
     * @return OwnUser
     */
    public UserQueryResponseImpl getUserByName(final String name) {

        try {
            response = client
                    .target(URL + WEB_CONTEXT_PATH)
                    .path(USERS_PATH)
                    .queryParam("byname", name)
                    .request(MediaType.APPLICATION_JSON)
                    .get(Response.class);


            if (response.getStatus() == 404) {
                throw new UserDoesNotExistException(ERR_USER_DOES_NOT_EXIST);

            } else if (response.getStatus() == 500) {
                throw new RuntimeException(ERR_INTERNAL_SERVER_ERROR + ": " + response.getStatus());

            }

        } catch (Exception e) {
            e.printStackTrace();
            // user does not exist, so return null TODO: BUG: DOESNT RETURN NULL (workaround wurde erstellt)
            return null;
        }

        String json = (String) response.readEntity(String.class);
        gSon = new GsonBuilder().create();

        return gSon.fromJson(json, UserQueryResponseImpl.class);
    }

    /**
     * send request to get a user object by name
     *
     * @return Userlist
     */
    public UserQueryResponseImpl getAllUser() {

        try {
            response = client
                    .target(URL + WEB_CONTEXT_PATH)
                    .path(USERS_PATH)
                    .request(MediaType.APPLICATION_JSON)
                    .get(Response.class);

            if (response.getStatus() == 404) {
                throw new UserDoesNotExistException(ERR_USER_DOES_NOT_EXIST);

            } else if (response.getStatus() == 500) {
                throw new RuntimeException(ERR_INTERNAL_SERVER_ERROR + ": " + response.getStatus());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        String json = (String) response.readEntity(String.class);
        gSon = new GsonBuilder().create();

        return gSon.fromJson(json, UserQueryResponseImpl.class);
    }

    /**
     * send request to add new user
     *
     * @param name   - name of new user
     * @param passwd - password of new user
     * @return new User Object
     */
    public User addUser(final String name, final String passwd) {
        gSon = new GsonBuilder().create();
        // create new User object from username and password
        UserImpl newUser = new UserImpl(name, passwd);
        // create json string out of User object
        String json = gSon.toJson(newUser);

        try {
            response = client
                    .target(URL + WEB_CONTEXT_PATH)
                    .path(USERS_PATH)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(json));

            if (response.getStatus() == 409) {
                throw new UserAlreadyExistsException(ERR_USER_ALREADY_EXISTS);

            } else if (response.getStatus() == 500) {
                throw new RuntimeException(ERR_INTERNAL_SERVER_ERROR + ": " + response.getStatus());

            }

        } catch (Exception e) {
            e.printStackTrace();
            // user already exists, so return null
            return null;
        }

        String respJson = (String) response.readEntity(String.class);
        return makeUserFromJSON(respJson);
    }

    /**
     * send request to remove user by id
     *
     * @param userId user to remove
     */
    public void removeUser(final String userId) {

        try {
            response = client
                    .target(URL + WEB_CONTEXT_PATH)
                    .path(USERS_PATH)
                    .path(userId)
                    .request(MediaType.APPLICATION_JSON)
                    .delete();

            if (response.getStatus() == 404) {
                throw new UserDoesNotExistException(ERR_USER_DOES_NOT_EXIST);

            } else if (response.getStatus() == 500) {
                throw new RuntimeException(ERR_INTERNAL_SERVER_ERROR + ": " + response.getStatus());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * send request to get user by id
     *
     * @param userId - user id to look for
     * @return OwnUser object
     */
    public User getUserById(final String userId) {
        try {
            response = client
                    .target(URL + WEB_CONTEXT_PATH)
                    .path(USERS_PATH)
                    .path(userId)
                    //.queryParam("byid", id)
                    .request(MediaType.APPLICATION_JSON)
                    .get(Response.class);

            if (response.getStatus() == 404) {
                throw new UserDoesNotExistException(ERR_USER_DOES_NOT_EXIST);

            } else if (response.getStatus() == 500) {
                throw new RuntimeException(ERR_INTERNAL_SERVER_ERROR + ": " + response.getStatus());

            }

        } catch (Exception e) {
            e.printStackTrace();
            // user does not exist, so return null
            return null;
        }

        String respJson = (String) response.readEntity(String.class);
        return makeUserFromJSON(respJson);
    }

    /**
     * send request to get a user with given name
     *
     * @param name - username to look for
     * @return User - found user
     */
    public User getTheUserByName(String name) {
        try {
            response = client
                    .target(URL + WEB_CONTEXT_PATH)
                    .path(USERS_PATH)
                    .path("name")
                    .queryParam("byname", name)
                    .request(MediaType.APPLICATION_JSON)
                    .get(Response.class);

            if (response.getStatus() == 404) {
                System.err.println("User does not exist");
                return null;

            } else if (response.getStatus() == 500) {
                throw new RuntimeException(ERR_INTERNAL_SERVER_ERROR + ": " + response.getStatus());

            }

        } catch (Exception e) {
            e.printStackTrace();
            // user does not exist, so return null
            return null;
        }

        String respJson = (String) response.readEntity(String.class);

        return makeUserFromJSON(respJson);
    }

    /**
     * send request to get all groups of user
     *
     * @param userId user to get groups from
     * @return ArrayList of group ids
     */
    public ArrayList<Integer> getGroupsOfUser(final String userId) {
        try {
            response = client
                    .target(URL + WEB_CONTEXT_PATH)
                    .path(USERS_PATH)
                    .path(userId)
                    .path(GROUPS_PATH)
                    .request(MediaType.APPLICATION_JSON)
                    .get(Response.class);

            if (response.getStatus() == 404) {
                throw new UserDoesNotExistException(ERR_USER_DOES_NOT_EXIST);

            } else if (response.getStatus() == 500) {
                throw new RuntimeException(ERR_INTERNAL_SERVER_ERROR + ": " + response.getStatus());

            }

        } catch (Exception e) {
            e.printStackTrace();
            // user does not exist, so return null
            return null; // TODO not good
        }

        String json = (String) response.readEntity(String.class);
        gSon = new GsonBuilder().create();

        return gSon.fromJson(json, new TypeToken<ArrayList<Integer>>() {
        }.getType());
    }

    /**
     * send request to authenticate a user with user id and password
     *
     * @param userId
     * @param passwd
     * @return boolean do or do not authenticate
     */
    public boolean authenticateUser(final String userId, final String passwd) {
        try {
            response = client
                    .target(URL + WEB_CONTEXT_PATH)
                    .path(USERS_PATH)
                    .path(userId)
                    .path(passwd)
                    .request(MediaType.APPLICATION_JSON)
                    .get(Response.class);

            if (response.getStatus() == 404) {
                throw new UserDoesNotExistException(ERR_USER_DOES_NOT_EXIST);

            } else if (response.getStatus() == 500) {
                throw new RuntimeException(ERR_INTERNAL_SERVER_ERROR + ": " + response.getStatus());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        String json = (String) response.readEntity(String.class);
        gSon = new GsonBuilder().create();

        return gSon.fromJson(json, Boolean.class);
    }

}
