package org.usergrid.rest.applications.users;

import com.sun.jersey.api.client.UniformInterfaceException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.usergrid.management.ApplicationInfo;
import org.usergrid.management.OrganizationOwnerInfo;
import org.usergrid.rest.RestContextTest;

import javax.ws.rs.core.MediaType;
import java.util.*;

import org.junit.Test;
import org.junit.Ignore;
import com.sun.jersey.api.client.ClientResponse;
import org.usergrid.rest.test.resource.app.CustomEntity;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;
import static org.usergrid.utils.MapUtils.hashMap;

import org.usergrid.rest.test.resource.CustomCollection;
import org.usergrid.utils.UUIDUtils;

/**
 * // TODO: Document this
 *
 * @author ApigeeCorporation
 * @since 4.0
 */
public class ConnectionResourceTest extends RestContextTest {

  @Test
     public void connectionsQueryTest() {

    CustomEntity items = new CustomEntity("item", null);

    CustomCollection activities = collection("peeps");

    Map stuff = hashMap("type", "chicken");

    activities.create(stuff);


    Map<String, Object> payload = new LinkedHashMap<String, Object>();
    payload.put("username", "Bob");

    Map<String, Object> objectOfDesire = new LinkedHashMap<String, Object>();
    objectOfDesire.put("codingmunchies", "doritoes");

    JsonNode node = resource().path("/test-organization/test-app/users")
        .queryParam("access_token", access_token)
        .accept(MediaType.APPLICATION_JSON)
        .type(MediaType.APPLICATION_JSON_TYPE)
        .post(JsonNode.class, payload);

    payload.put("username", "moe");


    node = resource().path("/test-organization/test-app/users")
        .queryParam("access_token", access_token)
        .accept(MediaType.APPLICATION_JSON)
        .type(MediaType.APPLICATION_JSON_TYPE)
        .post(JsonNode.class, payload);
    /*finish setting up the two users */


    ClientResponse toddWant = resource().path("/test-organization/test-app/users/bob/likes/peeps")
        .queryParam("access_token", access_token)
        .accept(MediaType.TEXT_HTML)
        .type(MediaType.APPLICATION_JSON_TYPE)
        .post(ClientResponse.class, objectOfDesire);

    assertEquals(200, toddWant.getStatus());

    node = resource().path("/test-organization/test-app/peeps")
        .queryParam("access_token", access_token)
        .accept(MediaType.APPLICATION_JSON)
        .type(MediaType.APPLICATION_JSON_TYPE)
        .get(JsonNode.class);

    String uuid = node.get("entities").get(0).get("uuid").getTextValue();




    try {
      node = resource().path("/test-organization/test-app/users/moe/likes/" + uuid)
          .queryParam("access_token", access_token)
          .accept(MediaType.APPLICATION_JSON)
          .type(MediaType.APPLICATION_JSON_TYPE)
          .get(JsonNode.class);
      assert (false);
    } catch (UniformInterfaceException uie) {
      assertEquals(404, uie.getResponse().getClientResponseStatus().getStatusCode());
      return;
    }

  }

  /*change things so that they say item instead of peeps*/
  @Test
  public void realDeleteQueryTest() {

   // CustomEntity items = new CustomEntity("item", null);

    CustomCollection activities = collection("peeps");

    Map stuff = hashMap("type", "chicken");

    activities.create(stuff);


    Map<String, Object> payload = new LinkedHashMap<String, Object>();
    payload.put("username", "todd");

    Map<String, Object> objectOfDesire = new LinkedHashMap<String, Object>();
    objectOfDesire.put("codingmunchies", "doritoes");

    JsonNode node = resource().path("/test-organization/test-app/users")
        .queryParam("access_token", access_token)
        .accept(MediaType.APPLICATION_JSON)
        .type(MediaType.APPLICATION_JSON_TYPE)
        .post(JsonNode.class, payload);

    payload.put("username", "scott");


    node = resource().path("/test-organization/test-app/users")
        .queryParam("access_token", access_token)
        .accept(MediaType.APPLICATION_JSON)
        .type(MediaType.APPLICATION_JSON_TYPE)
        .post(JsonNode.class, payload);
    /*finish setting up the two users */


    ClientResponse toddWant = resource().path("/test-organization/test-app/users/me/owns/peeps")
        .queryParam("access_token", access_token)
        .accept(MediaType.TEXT_HTML)
        .type(MediaType.APPLICATION_JSON_TYPE)
        .post(ClientResponse.class, objectOfDesire);

    assertEquals(200, toddWant.getStatus());

    node = resource().path("/test-organization/test-app/peeps")
        .queryParam("access_token", access_token)
        .accept(MediaType.APPLICATION_JSON)
        .type(MediaType.APPLICATION_JSON_TYPE)
        .get(JsonNode.class);

    String uuid = node.get("entities").get(0).get("uuid").getTextValue();




    try {
      node = resource().path("/test-organization/test-app/users/me/owns/" + uuid)
          .queryParam("access_token", access_token)
          .accept(MediaType.APPLICATION_JSON)
          .type(MediaType.APPLICATION_JSON_TYPE)
          .get(JsonNode.class);
      //assert (false);
    } catch (UniformInterfaceException uie) {
      assertEquals(404, uie.getResponse().getClientResponseStatus().getStatusCode());
      return;
    }



    node = resource().path("/test-organization/test-app/users/me/owns/peeps/" + uuid)
        .queryParam("access_token", access_token)
        .accept(MediaType.APPLICATION_JSON)
        .type(MediaType.APPLICATION_JSON_TYPE)
        .delete(JsonNode.class);

    //assert(false);


  }

  /*change things so that they say item instead of peeps*/
  @Test//("still doesn't represent dino's unauth access issue ")   //USERGRID-1713
  public void dinoDeleteQueryTest() {

    // CustomEntity items = new CustomEntity("item", null);

    CustomCollection activities = collection("items");

    //Map stuff = hashMap("type", "chicken");

    //activities.create(stuff);


    Map<String, Object> payload = new LinkedHashMap<String, Object>();
    payload.put("username", "todd");

    Map<String, Object> permissionLoad = new LinkedHashMap<String, Object>();


    Map<String, Object> objectOfDesire = new LinkedHashMap<String, Object>();
    objectOfDesire.put("codingmunchies", "doritoes");

    JsonNode node = resource().path("/test-organization/test-app/users")
        .queryParam("access_token", access_token)
        .accept(MediaType.APPLICATION_JSON)
        .type(MediaType.APPLICATION_JSON_TYPE)
        .post(JsonNode.class, payload);

    payload.put("username", "scott");



    node = resource().path("/test-organization/test-app/users")
        .queryParam("access_token", access_token)
        .accept(MediaType.APPLICATION_JSON)
        .type(MediaType.APPLICATION_JSON_TYPE)
        .post(JsonNode.class, payload);

    /*finish setting up the two users */


    ClientResponse toddWant = resource().path("/test-organization/test-app/users/me/owns/items")
        .queryParam("access_token", access_token)
        .accept(MediaType.TEXT_HTML)
        .type(MediaType.APPLICATION_JSON_TYPE)
        .post(ClientResponse.class, objectOfDesire);

    assertEquals(200, toddWant.getStatus());

    permissionLoad.put("permission","get,put,post,delete:/users/me/owns/**");

    node = resource().path("/test-organization/test-app/roles/default")
        .queryParam("access_token", access_token)
        .accept(MediaType.APPLICATION_JSON)
        .type(MediaType.APPLICATION_JSON_TYPE)
        .delete(JsonNode.class);

    node = resource().path("/test-organization/test-app/roles/default")
        .queryParam("access_token", access_token)
        .accept(MediaType.APPLICATION_JSON)
        .type(MediaType.APPLICATION_JSON_TYPE)
        .post(JsonNode.class, permissionLoad);

    node = resource().path("/test-organization/test-app/items")
        .queryParam("access_token", access_token)
        .accept(MediaType.APPLICATION_JSON)
        .type(MediaType.APPLICATION_JSON_TYPE)
        .get(JsonNode.class);

    String uuid = node.get("entities").get(0).get("uuid").getTextValue();

    try {
      node = resource().path("/test-organization/test-app/users/me/owns/" + uuid)
          .queryParam("access_token", access_token)
          .accept(MediaType.APPLICATION_JSON)
          .type(MediaType.APPLICATION_JSON_TYPE)
          .get(JsonNode.class);
      //assert (false);
    } catch (UniformInterfaceException uie) {
      assertEquals(200, uie.getResponse().getClientResponseStatus().getStatusCode());
      return;
    }

    node = resource().path("/test-organization/test-app/users/me/owns/items/" + uuid)
        .queryParam("access_token", access_token)
        .accept(MediaType.APPLICATION_JSON)
        .type(MediaType.APPLICATION_JSON_TYPE)
        .delete(JsonNode.class);

    try {
      node = resource().path("/test-organization/test-app/users/me/owns/" + uuid)
          .queryParam("access_token", access_token)
          .accept(MediaType.APPLICATION_JSON)
          .type(MediaType.APPLICATION_JSON_TYPE)
          .get(JsonNode.class);
      //assert (false);
    } catch (UniformInterfaceException uie) {
      assertEquals(404, uie.getResponse().getClientResponseStatus().getStatusCode());
      return;
    }

    try {
      node = resource().path("/test-organization/test-app/items/" + uuid)
          .queryParam("access_token", access_token)
          .accept(MediaType.APPLICATION_JSON)
          .type(MediaType.APPLICATION_JSON_TYPE)
          .get(JsonNode.class);
      //assert (false);
    } catch (UniformInterfaceException uie) {
      assertEquals(200, uie.getResponse().getClientResponseStatus().getStatusCode());
      return;
    }

    //assert(false);


  }

  /*test stolen and refactored from PermissionsResourceTest */
  @Test
  public void applicationPermissions() throws Exception {
    UUID id = UUIDUtils.newTimeUUID();

    String applicationName = "test";
    String orgname = "applicationpermissions";
    String username = "permissionadmin" + id;
    String password = "password";
    String email = String.format("email%s@usergrid.com", id);

    OrganizationOwnerInfo orgs = managementService.createOwnerAndOrganization(orgname, username, "noname", email,
        password, true, false);

    // create the app
    ApplicationInfo appInfo = managementService.createApplication(orgs.getOrganization().getUuid(), applicationName);

    // now create the new role
    Map<String, String> data = hashMap("name", "reviewer");

    String adminToken = managementService.getAccessTokenForAdminUser(orgs.getOwner().getUuid(), 0);

    JsonNode node = resource().path(String.format("/%s/%s/roles", orgname, applicationName))
        .queryParam("access_token", adminToken).accept(MediaType.APPLICATION_JSON)
        .type(MediaType.APPLICATION_JSON_TYPE).post(JsonNode.class, data);

    assertNull(getError(node));

    // delete the default role to test permissions later
    node = resource().path(String.format("/%s/%s/roles/default", orgname, applicationName))
        .queryParam("access_token", adminToken).accept(MediaType.APPLICATION_JSON)
        .type(MediaType.APPLICATION_JSON_TYPE).delete(JsonNode.class);

    assertNull(getError(node));

    // grant the perms to reviewer
    addPermission(orgname, applicationName, adminToken, "reviewer", "get,put,post,delete:/users/me/items/**");
    addPermission(orgname, applicationName, adminToken, "reviewer", "get,put,post,delete:/users/me/owns/**");
    addPermission(orgname, applicationName, adminToken, "reviewer", "get,put,post:/users/me/*");
    addPermission(orgname, applicationName, adminToken, "reviewer", "get,post:/groups/*");
    addPermission(orgname, applicationName, adminToken, "reviewer", "get:/roles/**");
    addPermission(orgname, applicationName, adminToken, "reviewer", "post:/items");
    addPermission(orgname, applicationName, adminToken, "reviewer", "post:/things");

    // grant get to guest
    //addPermission(orgname, applicationName, adminToken, "guest", "get:/reviews/**");

    UUID userId = createRoleUser(orgs.getOrganization().getUuid(), appInfo.getId(), adminToken, "reviewer1",
        "reviewer1@usergrid.com");

    node = resource().path(String.format("/%s/%s/roles/reviewer/permissions", orgname, applicationName))
        .queryParam("access_token", adminToken)
        .accept(MediaType.APPLICATION_JSON)
        .type(MediaType.APPLICATION_JSON_TYPE)
        .get(JsonNode.class);

    // grant this user the "reviewer" role
    node = resource().path(String.format("/%s/%s/users/reviewer1/roles/reviewer", orgname, applicationName))
        .queryParam("access_token", adminToken).accept(MediaType.APPLICATION_JSON)
        .type(MediaType.APPLICATION_JSON_TYPE).post(JsonNode.class);

    assertNull(getError(node));

    String reviewer1Token = managementService.getAccessTokenForAppUser(appInfo.getId(), userId, 0);


    Map<String, Object> objectOfDesire = new LinkedHashMap<String, Object>();
    objectOfDesire.put("codingmunchies", "doritoes");

    ClientResponse toddWant = resource().path(String.format("/%s/%s/users/reviewer1/owns/items", orgname,applicationName))
        .queryParam("access_token", adminToken)
        .accept(MediaType.TEXT_HTML)
        .type(MediaType.APPLICATION_JSON_TYPE)
        .post(ClientResponse.class, objectOfDesire);


    node = resource().path(String.format("/%s/%s/items",orgname,applicationName))
        .queryParam("access_token", adminToken)
        .accept(MediaType.APPLICATION_JSON)
        .type(MediaType.APPLICATION_JSON_TYPE)
        .get(JsonNode.class);

    String uuid = node.get("entities").get(0).get("uuid").getTextValue();

    node = resource().path(String.format("/%s/%s/users/reviewer1/owns",orgname,applicationName))
        .queryParam("access_token", adminToken)
        .accept(MediaType.APPLICATION_JSON)
        .type(MediaType.APPLICATION_JSON_TYPE)
        .get(JsonNode.class);




    try {
      node = resource().path(String.format("/%s/%s/users/reviewer1/owns/items/" + uuid,orgname,applicationName))
          .queryParam("access_token", adminToken)
          .accept(MediaType.APPLICATION_JSON)
          .type(MediaType.APPLICATION_JSON_TYPE)
          .get(JsonNode.class);
      //assert (false);
    } catch (UniformInterfaceException uie) {
      assertEquals(200, uie.getResponse().getClientResponseStatus().getStatusCode());
      return;
    }

    node = resource().path(String.format("/%s/%s/users/reviewer1/owns/items/" + uuid,orgname,applicationName))
        .queryParam("access_token", adminToken)
        .accept(MediaType.APPLICATION_JSON)
        .type(MediaType.APPLICATION_JSON_TYPE)
        .delete(JsonNode.class);

    try {
    node = resource().path(String.format("/%s/%s/users/reviewer1/owns/items/" + uuid,orgname,applicationName))
        .queryParam("access_token", adminToken)
        .accept(MediaType.APPLICATION_JSON)
        .type(MediaType.APPLICATION_JSON_TYPE)
        .get(JsonNode.class);
  } catch (UniformInterfaceException uie) {
    assertEquals(404, uie.getResponse().getClientResponseStatus().getStatusCode());
    return;
  }

  }

  /**
   * Test adding the permission to the role
   *
   * @param orgname
   * @param appname
   * @param adminToken
   * @param rolename
   * @param grant
   */
  private void addPermission(String orgname, String appname, String adminToken, String rolename, String grant) {
    Map<String, String> props = hashMap("permission", grant);

    String rolePath = String.format("/%s/%s/roles/%s/permissions", orgname, appname, rolename);

    JsonNode node = resource().path(rolePath).queryParam("access_token", adminToken).accept(MediaType.APPLICATION_JSON)
        .type(MediaType.APPLICATION_JSON_TYPE).put(JsonNode.class, props);

    assertNull(getError(node));

    node = resource().path(rolePath).queryParam("access_token", adminToken).accept(MediaType.APPLICATION_JSON)
        .type(MediaType.APPLICATION_JSON_TYPE).get(JsonNode.class);

    ArrayNode data = (ArrayNode) node.get("data");

    Iterator<JsonNode> iterator = data.getElements();

    while (iterator.hasNext()) {
      if (grant.equals(iterator.next().asText())) {
        return;
      }
    }

    fail(String.format("didn't find grant %s in the results", grant));
  }

  /**
   * Test adding the permission to the role
   *
   * @param orgname
   * @param appname
   * @param rolename
   * @param grant
   */
  private void addPermission(String orgname, String appname, String rolename, String grant) {
    Map<String, String> props = hashMap("permission", grant);

    String rolePath = String.format("/%s/%s/roles/%s/permissions", orgname, appname, rolename);

    JsonNode node = resource().path(rolePath).accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON_TYPE)
        .put(JsonNode.class, props);

    assertNull(getError(node));

    node = resource().path(rolePath).accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON_TYPE)
        .get(JsonNode.class);

    ArrayNode data = (ArrayNode) node.get("data");

    Iterator<JsonNode> iterator = data.getElements();

    while (iterator.hasNext()) {
      if (grant.equals(iterator.next().asText())) {
        return;
      }
    }

    fail(String.format("didn't find grant %s in the results", grant));
  }


//  @Test
//  public void deleteConnectionsTest () {
//
//    CustomEntity items = new CustomEntity("item",null);
//
//    Map<String, Object> payload = new LinkedHashMap<String, Object>();
//    payload.put("uuid", "59129360-fa30-11e2-bbf5-bbb7a60289dc");
//
//
//    JsonNode node = resource().path("/test-organization/test-app/users/me/owns/items")
//        .queryParam("access_token", access_token)
//        .accept(MediaType.APPLICATION_JSON)
//        .type(MediaType.APPLICATION_JSON_TYPE)
//        .post(JsonNode.class,payload);
//
//    String uuid = node.get("entities").get(0).get("uuid").getTextValue();
//    node = resource().path("/test-organization/test-app/users/me/owns/items")
//        .queryParam("access_token", access_token)
//        .accept(MediaType.APPLICATION_JSON)
//        .type(MediaType.APPLICATION_JSON_TYPE)
//        .get(JsonNode.class);
//
//    assertNotNull(node.get("entities").get(0));
//
//    ClientResponse deleteResponse = resource().path("/test-organization/test-app/users/me/owns/items/"+uuid)
//        .queryParam("access_token", access_token)
//        .accept(MediaType.TEXT_HTML)
//        .type(MediaType.APPLICATION_JSON_TYPE)
//        .delete(ClientResponse.class);
//
//    assertEquals(200,deleteResponse.getStatus());
//
//  }

  /**
   * Create the user, check there are no errors
   *
   * @param orgId
   * @param appId
   * @param adminToken
   * @param username
   * @param email
   *
   * @return the userid
   * @throws Exception
   */
  private UUID createRoleUser(UUID orgId, UUID appId, String adminToken, String username, String email)
      throws Exception {

    Map<String, String> props = hashMap("email", email).map("username", username).map("name", username)
        .map("password", "password");

    JsonNode node = resource().path(String.format("/%s/%s/users", orgId, appId)).queryParam("access_token", adminToken)
        .accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON_TYPE).put(JsonNode.class, props);

    assertNull(getError(node));

    UUID userId = UUID.fromString(getEntity(node, 0).get("uuid").asText());

    // manually activate user
    managementService.activateAppUser(appId, userId);

    return userId;

  }

}