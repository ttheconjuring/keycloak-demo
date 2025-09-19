Description
 - 
The idea of this project is to demonstrate how to implement authentication/authorization adhering to the the OAuth2 standard.
* End User: You (through the browser)
* Client: Postman
* Authorization Server: Keycloak
* Resource Server: Spring Boot Application

*Always remember to update the URLs you provide to Postman and Spring Boot Application according to the chosen realm in Keycloak. All the URLs needed can be found here: <code>Realm settings -> General -> Endpoints -> OpenID Endpoint Configuration</code>

*In case you want to use opaque tokens instead of jwt, then you need to register the Spring Boot Application as a client in Keycloak too. The process remains the same.

Keycloak Configuration
 - 

<b>1. Authorization Code Grant with Proof Key for Code Exchange (PKCE)</b>

- Create new realm and enable it. (You can name if after the flow.)
- Navigate to Clients and click on Create client (This will be the Postman client.)
	- General settings
		- Set Client type to OpenID Connect.
  		- Set Clinet ID.
  		- Set Name and Description (optional).
  		- Disable Always display in UI.
	- Capability config
   		- Disable Cleint authentication.
   		- Disable Authorization.
  		- Set Authentication flow to Standard flow only.
  		- Set PKCE Method to S256.
	- Login settings
 		- Set Valid redirect URIs to *. Click Save.
- Navigate to Users and click on Create new user (This will be the end user.)
	- Enable Email verified.
 	- Set Username, Email, First name and Last name. Click Create.
  - Navigate to Credentials and click Set password.
  	- Set Password and Password confirmation.
   	- You can add password requirements by going to Authentication -> Policies -> Password policy. (optional)
    - Disable Temporary. Click Save.
- Navigate to Realm roles and click on Create role.
  - Set Role name to USER. Add description (optional). CLick Save.
  - Set Role name to ADMIN. Add description (optional). Click Save.
- Navigate back to Users and click on your user.
	- Navigate to Role mapping.
 	- Click on Assign Role -> Realm roles.
  - Select USER and ADMIN and click Assign.

     
<b>2. Authorization Code Grant</b>

- Create new realm and enable it. (You can name if after the flow.)
- Navigate to Clients and click on Create client (This will be the Postman client.)
	- General settings
		- Set Client type to OpenID Connect.
  		- Set Clinet ID.
  		- Set Name and Description (optional).
  		- Disable Always display in UI.
	- Capability config
   		- Enable Cleint authentication.
   		- Disable Authorization.
  		- Set Authentication flow to Standard flow only.
	- Login settings
 		- Set Valid redirect URIs to *. Click Save.
- Navigate to Users and click on Create new user (This will be the end user.)
	- Enable Email verified.
 	- Set Username, Email, First name and Last name. Click Create.
  - Navigate to Credentials and click Set password.
  	- Set Password and Password confirmation.
   	- You can add password requirements by going to Authentication -> Policies -> Password policy. (optional)
    - Disable Temporary. Click Save.
- Navigate to Realm roles and click on Create role.
  - Set Role name to USER. Add description (optional). CLick Save.
  - Set Role name to ADMIN. Add description (optional). Click Save.
- Navigate back to Users and click on your user.
	- Navigate to Role mapping.
 	- Click on Assign Role -> Realm roles.
  - Select USER and ADMIN and click Assign.

<b>3. Client Credentials Grant Type</b>

- Create new realm and enable it. (You can name if after the flow.)
- Navigate to Clients and click on Create client (This will be the Postman client.)
	- General settings
		- Set Client type to OpenID Connect.
  		- Set Clinet ID.
  		- Set Name and Description (optional).
  		- Disable Always display in UI.
	- Capability config
   		- Enable Cleint authentication.
   		- Disable Authorization.
  		- Set Authentication flow to Service accounts roles only.
	- Login settings (skip). Click Save.
- Navigate to Realm roles and click on Create role.
  - Set Role name to USER. Add description (optional). Click Save.
  - Set Role name to ADMIN. Add description (optional). Click Save.
- Navigate back to Clients and click on your client.
	- Navigate to Service accounts role.
 	- Click on Assign Role -> Realm roles.
  - Select USER and ADMIN and click Assign.
 
Postman Configuration
 - 

1) Authorization Code Grant with Proof Key for Code Exchange (PKCE)
- Open Postman.
- Put down the endpoint URL.
- Navigate to Authorization.
	- Set Type to OAuth 2.0
 	- Set Add authorization data to Reqest Headers.
  - Scroll down to Configure New Token.
  - Set Token name.
  - Set Grant Type to Authorization Code (With PKCE).
  - Click on Authorize using browser
  - Set Auth URL by looking at the OpenID Endpoint Configuration for the realm.
  - Set Access token URL by looking at the OpenID Endpoint Configuration for the realm.
  - Set Client ID as the one configured in Keycloak.
  - You can leave the Client Secret field empty, since it is not mandatory.
  - Set Code Challenge Method to SHA-256.
  - You can leave the Code Verifier empty (it will be generated for you).
  - You can leave the Scope field empty (the default values are: profile email).
  - You can leave the State filed empty or set it as a random string.
  - Set Client Authentication to Send client credentials in body.
  - Click on Get New Access Token and then Use Token.
   
3) Authorization Code Grant
- Open Postman.
- Put down the endpoint URL.
- Navigate to Authorization.
	- Set Type to OAuth 2.0
 	- Set Add authorization data to Reqest Headers.
  - Scroll down to Configure New Token.
  - Set Token name.
  - Set Grant Type to Authorization Code.
  - Click on Authorize using browser
  - Set Auth URL by looking at the OpenID Endpoint Configuration for the realm.
  - Set Access token URL by looking at the OpenID Endpoint Configuration for the realm.
  - Set Client ID and Client Secret the same as the ones configured in Keycloak.
  - You can leave the Scope field empty (the default values are: profile email).
  - You can leave the State filed empty or set it as a random string.
  - Set Client Authentication to Send client credentials in body.
  - Click on Get New Access Token and then Use Token.
  
5) Client Credentials Grant Type
- Open Postman.
- Put down the endpoint URL.
- Navigate to Authorization.
	- Set Type to OAuth 2.0.
 	- Set Add authorization data to Reqest Headers.
  - Scroll down to Configure New Token.
  - Set Token name.
  - Set Grant Type to Client Credentials.
  - You can obtain the Access Token URL by navigating to Realm Settings in Keycloak, then scroll down to Endpoints and click on the Configuration link. Find the jwks_uri key and copy the value, then paste it in the corresponding Postman field.
  - Set Client ID and Client Secret the same as the ones configured in Keycloak.
  - You can leave the Scope field empty.
  - Set Client Authentication to Send client credentials in body.
  - Click on Get New Access Token and then Use Token.
